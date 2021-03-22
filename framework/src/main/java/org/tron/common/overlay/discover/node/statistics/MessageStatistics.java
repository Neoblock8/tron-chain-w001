package org.tron.common.overlay.discover.node.statistics;

import lombok.extern.slf4j.Slf4j;
import org.tron.common.net.udp.message.UdpMessageTypeEnum;
import org.tron.common.overlay.message.Message;
import org.tron.core.net.message.FetchInvDataMessage;
import org.tron.core.net.message.InventoryMessage;
import org.tron.core.net.message.MessageTypes;
import org.tron.core.net.message.TransactionsMessage;

@Slf4j
public class MessageStatistics {

  //udp discovery
  public final MessageCount discoverInPing = new MessageCount();
  public final MessageCount discoverOutPing = new MessageCount();
  public final MessageCount discoverInPong = new MessageCount();
  public final MessageCount discoverOutPong = new MessageCount();
  public final MessageCount discoverInFindNode = new MessageCount();
  public final MessageCount discoverOutFindNode = new MessageCount();
  public final MessageCount discoverInNeighbours = new MessageCount();
  public final MessageCount discoverOutNeighbours = new MessageCount();

  //tcp p2p
  public final MessageCount p2pInHello = new MessageCount();
  public final MessageCount p2pOutHello = new MessageCount();
  public final MessageCount p2pInPing = new MessageCount();
  public final MessageCount p2pOutPing = new MessageCount();
  public final MessageCount p2pInPong = new MessageCount();
  public final MessageCount p2pOutPong = new MessageCount();
  public final MessageCount p2pInDisconnect = new MessageCount();
  public final MessageCount p2pOutDisconnect = new MessageCount();

  //tcp tron
  public final MessageCount tronInMessage = new MessageCount();
  public final MessageCount tronOutMessage = new MessageCount();

  public final MessageCount tronInSyncBlockChain = new MessageCount();
  public final MessageCount tronOutSyncBlockChain = new MessageCount();
  public final MessageCount tronInBlockChainInventory = new MessageCount();
  public final MessageCount tronOutBlockChainInventory = new MessageCount();

  public final MessageCount tronInWyzInventory = new MessageCount();
  public final MessageCount tronOutWyzInventory = new MessageCount();
  public final MessageCount tronInWyzInventoryElement = new MessageCount();
  public final MessageCount tronOutWyzInventoryElement = new MessageCount();

  public final MessageCount tronInBlockInventory = new MessageCount();
  public final MessageCount tronOutBlockInventory = new MessageCount();
  public final MessageCount tronInBlockInventoryElement = new MessageCount();
  public final MessageCount tronOutBlockInventoryElement = new MessageCount();

  public final MessageCount tronInWyzFetchInvData = new MessageCount();
  public final MessageCount tronOutWyzFetchInvData = new MessageCount();
  public final MessageCount tronInWyzFetchInvDataElement = new MessageCount();
  public final MessageCount tronOutWyzFetchInvDataElement = new MessageCount();

  public final MessageCount tronInBlockFetchInvData = new MessageCount();
  public final MessageCount tronOutBlockFetchInvData = new MessageCount();
  public final MessageCount tronInBlockFetchInvDataElement = new MessageCount();
  public final MessageCount tronOutBlockFetchInvDataElement = new MessageCount();


  public final MessageCount tronInWyz = new MessageCount();
  public final MessageCount tronOutWyz = new MessageCount();
  public final MessageCount tronInWyzs = new MessageCount();
  public final MessageCount tronOutWyzs = new MessageCount();
  public final MessageCount tronInBlock = new MessageCount();
  public final MessageCount tronOutBlock = new MessageCount();
  public final MessageCount tronOutAdvBlock = new MessageCount();

  public void addUdpInMessage(UdpMessageTypeEnum type) {
    addUdpMessage(type, true);
  }

  public void addUdpOutMessage(UdpMessageTypeEnum type) {
    addUdpMessage(type, false);
  }

  public void addTcpInMessage(Message msg) {
    addTcpMessage(msg, true);
  }

  public void addTcpOutMessage(Message msg) {
    addTcpMessage(msg, false);
  }

  private void addUdpMessage(UdpMessageTypeEnum type, boolean flag) {
    switch (type) {
      case DISCOVER_PING:
        if (flag) {
          discoverInPing.add();
        } else {
          discoverOutPing.add();
        }
        break;
      case DISCOVER_PONG:
        if (flag) {
          discoverInPong.add();
        } else {
          discoverOutPong.add();
        }
        break;
      case DISCOVER_FIND_NODE:
        if (flag) {
          discoverInFindNode.add();
        } else {
          discoverOutFindNode.add();
        }
        break;
      case DISCOVER_NEIGHBORS:
        if (flag) {
          discoverInNeighbours.add();
        } else {
          discoverOutNeighbours.add();
        }
        break;
      default:
        break;
    }
  }

  private void addTcpMessage(Message msg, boolean flag) {

    if (flag) {
      tronInMessage.add();
    } else {
      tronOutMessage.add();
    }

    switch (msg.getType()) {
      case P2P_HELLO:
        if (flag) {
          p2pInHello.add();
        } else {
          p2pOutHello.add();
        }
        break;
      case P2P_PING:
        if (flag) {
          p2pInPing.add();
        } else {
          p2pOutPing.add();
        }
        break;
      case P2P_PONG:
        if (flag) {
          p2pInPong.add();
        } else {
          p2pOutPong.add();
        }
        break;
      case P2P_DISCONNECT:
        if (flag) {
          p2pInDisconnect.add();
        } else {
          p2pOutDisconnect.add();
        }
        break;
      case SYNC_BLOCK_CHAIN:
        if (flag) {
          tronInSyncBlockChain.add();
        } else {
          tronOutSyncBlockChain.add();
        }
        break;
      case BLOCK_CHAIN_INVENTORY:
        if (flag) {
          tronInBlockChainInventory.add();
        } else {
          tronOutBlockChainInventory.add();
        }
        break;
      case INVENTORY:
        InventoryMessage inventoryMessage = (InventoryMessage) msg;
        int inventorySize = inventoryMessage.getInventory().getIdsCount();
        messageProcess(inventoryMessage.getInvMessageType(),
                tronInWyzInventory,tronInWyzInventoryElement,tronInBlockInventory,
                tronInBlockInventoryElement,tronOutWyzInventory,tronOutWyzInventoryElement,
                tronOutBlockInventory,tronOutBlockInventoryElement,
                flag, inventorySize);
        break;
      case FETCH_INV_DATA:
        FetchInvDataMessage fetchInvDataMessage = (FetchInvDataMessage) msg;
        int fetchSize = fetchInvDataMessage.getInventory().getIdsCount();
        messageProcess(fetchInvDataMessage.getInvMessageType(),
                tronInWyzFetchInvData,tronInWyzFetchInvDataElement,tronInBlockFetchInvData,
                tronInBlockFetchInvDataElement,tronOutWyzFetchInvData,tronOutWyzFetchInvDataElement,
                tronOutBlockFetchInvData,tronOutBlockFetchInvDataElement,
                flag, fetchSize);
        break;
      case WYZS:
        TransactionsMessage transactionsMessage = (TransactionsMessage) msg;
        if (flag) {
          tronInWyzs.add();
          tronInWyz.add(transactionsMessage.getTransactions().getTransactionsCount());
        } else {
          tronOutWyzs.add();
          tronOutWyz.add(transactionsMessage.getTransactions().getTransactionsCount());
        }
        break;
      case WYZ:
        if (flag) {
          tronInMessage.add();
        } else {
          tronOutMessage.add();
        }
        break;
      case BLOCK:
        if (flag) {
          tronInBlock.add();
        }
        tronOutBlock.add();
        break;
      default:
        break;
    }
  }
  
  
  private void messageProcess(MessageTypes messageType,
                              MessageCount inWyz,
                              MessageCount inWyzEle,
                              MessageCount inBlock,
                              MessageCount inBlockEle,
                              MessageCount outWyz,
                              MessageCount outWyzEle,
                              MessageCount outBlock,
                              MessageCount outBlockEle,
                              boolean flag, int size) {
    if (flag) {
      if (messageType == MessageTypes.WYZ) {
        inWyz.add();
        inWyzEle.add(size);
      } else {
        inBlock.add();
        inBlockEle.add(size);
      }
    } else {
      if (messageType == MessageTypes.WYZ) {
        outWyz.add();
        outWyzEle.add(size);
      } else {
        outBlock.add();
        outBlockEle.add(size);
      }
    }
  }

}
