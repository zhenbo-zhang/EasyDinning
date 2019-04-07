package com.zhihao.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * The logic of websocket.
 */
@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

  private Session session;

  /**
   * Container that keep all open websocket.
   */
  private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

  /**
   * The logic when the websocket is open.
   *
   * @param session - session
   */
  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    webSocketSet.add(this);
    log.info("【websocket】New Connection, 总数:{}", webSocketSet.size());
  }

  /**
   * The logic when the websocket is closed.
   */
  @OnClose
  public void onClose() {
    webSocketSet.remove(this);
    log.info("【websocket】Connection is off, 总数:{}", webSocketSet.size());
  }

  /**
   * The logic when there comes a new message.
   *
   * @param message - the received message
   */
  @OnMessage
  public void onMessage(String message) {
    log.info("【websocket】New Message from Client:{}", message);
  }

  /**
   * The logic of sending message.
   *
   * @param - message to send
   */
  public void sendMessage(String message) {
    for (WebSocket webSocket : webSocketSet) {
      log.info("【websocket】New Broadcast, message={}", message);
      try {
        webSocket.session.getBasicRemote().sendText(message);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

}
