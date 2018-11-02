
package com.nibado.example.websocket.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpHeaders;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.TransportRequest;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.client.XhrClientSockJsSession;

import com.nibado.example.websocket.service.Request;

public class WebSocketClient {

	public static void main(final String... argv) throws Exception, ExecutionException {
		StandardWebSocketClient webSocketClient = new StandardWebSocketClient();

		List<Transport> transports = new ArrayList();
		transports.add(new WebSocketTransport(webSocketClient));
		transports.add(new RestTemplateXhrTransport() {
			@Override
			protected void connectInternal(TransportRequest transportRequest, WebSocketHandler handler, URI receiveUrl,
					HttpHeaders handshakeHeaders, XhrClientSockJsSession session,
					SettableListenableFuture<WebSocketSession> connectFuture) {
				try {
					Thread.sleep(5000); // to spawie ze dizala
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.connectInternal(transportRequest, handler, receiveUrl, handshakeHeaders, session, connectFuture);
			}
		});

		SockJsClient sockJsClient = new SockJsClient(transports);
		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		String url = "http://localhost/rock/hello";
		StompSessionHandler sessionHandler = new MySessionHandler();
		ListenableFuture<StompSession> connect = stompClient.connect(url, sessionHandler);
		for (int i = 0; i < 1000; i++) {
			connect.get().send("/app/sum", new Request(System.currentTimeMillis()));
		}
		Thread.sleep(10000); // wait for all
		System.out.println(MySessionHandler.SUM);
		new Scanner(System.in).nextLine();
	}
}
