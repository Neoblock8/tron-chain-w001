package org.tron.core.net.messagehandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tron.core.config.args.Args;
import org.tron.core.exception.P2pException;
import org.tron.core.exception.P2pException.TypeEnum;
import org.tron.core.net.WyzthNetDelegate;
import org.tron.core.net.message.TransactionMessage;
import org.tron.core.net.message.TransactionsMessage;
import org.tron.core.net.message.WyzthMessage;
import org.tron.core.net.peer.Item;
import org.tron.core.net.peer.PeerConnection;
import org.tron.core.net.service.AdvService;
import org.tron.protos.Protocol.Inventory.InventoryType;
import org.tron.protos.Protocol.ReasonCode;
import org.tron.protos.Protocol.Transaction;
import org.tron.protos.Protocol.Transaction.Contract.ContractType;

@Slf4j(topic = "net")
@Component
public class TransactionsMsgHandler implements WyzthMsgHandler {

  private static int MAX_WYZ_SIZE = 50_000;
  private static int MAX_SMART_CONTRACT_SUBMIT_SIZE = 100;
  @Autowired
  private WyzthNetDelegate WyzthNetDelegate;
  @Autowired
  private AdvService advService;

  //  private static int TIME_OUT = 10 * 60 * 1000;
  private BlockingQueue<WyzEvent> smartContractQueue = new LinkedBlockingQueue(MAX_WYZ_SIZE);

  private BlockingQueue<Runnable> queue = new LinkedBlockingQueue();

  private int threadNum = Args.getInstance().getValidateSignThreadNum();
  private ExecutorService wyzHandlePool = new ThreadPoolExecutor(threadNum, threadNum, 0L,
      TimeUnit.MILLISECONDS, queue);

  private ScheduledExecutorService smartContractExecutor = Executors
      .newSingleThreadScheduledExecutor();

  public void init() {
    handleSmartContract();
  }

  public void close() {
    smartContractExecutor.shutdown();
  }

  public boolean isBusy() {
    return queue.size() + smartContractQueue.size() > MAX_WYZ_SIZE;
  }

  @Override
  public void processMessage(PeerConnection peer, WyzthMessage msg) throws P2pException {
    TransactionsMessage transactionsMessage = (TransactionsMessage) msg;
    check(peer, transactionsMessage);
    for (Transaction wyz : transactionsMessage.getTransactions().getTransactionsList()) {
      int type = wyz.getRawData().getContract(0).getType().getNumber();
      if (type == ContractType.TriggerSmartContract_VALUE
          || type == ContractType.CreateSmartContract_VALUE) {
        if (!smartContractQueue.offer(new WyzEvent(peer, new TransactionMessage(wyz)))) {
          logger.warn("Add smart contract failed, queueSize {}:{}", smartContractQueue.size(),
              queue.size());
        }
      } else {
        wyzHandlePool.submit(() -> handleTransaction(peer, new TransactionMessage(wyz)));
      }
    }
  }

  private void check(PeerConnection peer, TransactionsMessage msg) throws P2pException {
    for (Transaction wyz : msg.getTransactions().getTransactionsList()) {
      Item item = new Item(new TransactionMessage(wyz).getMessageId(), InventoryType.WYZ);
      if (!peer.getAdvInvRequest().containsKey(item)) {
        throw new P2pException(TypeEnum.BAD_MESSAGE,
            "wyz: " + msg.getMessageId() + " without request.");
      }
      peer.getAdvInvRequest().remove(item);
    }
  }

  private void handleSmartContract() {
    smartContractExecutor.scheduleWithFixedDelay(() -> {
      try {
        while (queue.size() < MAX_SMART_CONTRACT_SUBMIT_SIZE) {
          WyzEvent event = smartContractQueue.take();
          wyzHandlePool.submit(() -> handleTransaction(event.getPeer(), event.getMsg()));
        }
      } catch (Exception e) {
        logger.error("Handle smart contract exception.", e);
      }
    }, 1000, 20, TimeUnit.MILLISECONDS);
  }

  private void handleTransaction(PeerConnection peer, TransactionMessage wyz) {
    if (peer.isDisconnect()) {
      logger.warn("Drop wyz {} from {}, peer is disconnect.", wyz.getMessageId(),
          peer.getInetAddress());
      return;
    }

    if (advService.getMessage(new Item(wyz.getMessageId(), InventoryType.WYZ)) != null) {
      return;
    }

    try {
      WyzthNetDelegate.pushTransaction(wyz.getTransactionCapsule());
      advService.broadcast(wyz);
    } catch (P2pException e) {
      logger.warn("Wyz {} from peer {} process failed. type: {}, reason: {}",
          wyz.getMessageId(), peer.getInetAddress(), e.getType(), e.getMessage());
      if (e.getType().equals(TypeEnum.BAD_WYZ)) {
        peer.disconnect(ReasonCode.BAD_TX);
      }
    } catch (Exception e) {
      logger.error("Wyz {} from peer {} process failed.", wyz.getMessageId(), peer.getInetAddress(),
          e);
    }
  }

  class WyzEvent {

    @Getter
    private PeerConnection peer;
    @Getter
    private TransactionMessage msg;
    @Getter
    private long time;

    public WyzEvent(PeerConnection peer, TransactionMessage msg) {
      this.peer = peer;
      this.msg = msg;
      this.time = System.currentTimeMillis();
    }
  }
}