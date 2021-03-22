package org.tron.core.net.messagehandler;

import org.tron.core.exception.P2pException;
import org.tron.core.net.message.WyzthMessage;
import org.tron.core.net.peer.PeerConnection;

public interface WyzthMsgHandler {

  void processMessage(PeerConnection peer, WyzthMessage msg) throws P2pException;

}
