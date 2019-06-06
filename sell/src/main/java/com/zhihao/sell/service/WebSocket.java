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
 * The logic of websocket. Used to send message from server to client.
 */
@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

  private Session session;

  /**
   * Container that stores all open websocket.
   */
  private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

  /**
   * The logic when the websocket is open.
   *
   * @param session - new created session
   */
  @OnOpen
  public void onOpen(Session session) {
    this.session = session;
    webSocketSet.add(this);
    log.info("【websocket】New Connection, number:{}", webSocketSet.size());
  }

  /**
   * The logic when the websocket is closed.
   */
  @OnClose
  public void onClose() {
    webSocketSet.remove(this);
    log.info("【websocket】Connection is off, number:{}", webSocketSet.size());
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
   * The logic of push notifications.
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
