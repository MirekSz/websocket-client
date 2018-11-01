
package com.nibado.example.websocket.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.nibado.example.websocket.service.Request;

public class WebSocketClient {

	public static void main(final String... argv) throws Exception, ExecutionException {
		StandardWebSocketClient webSocketClient = new StandardWebSocketClient();

		List<Transport> transports = new ArrayList();
		transports.add(new WebSocketTransport(webSocketClient));
		transports.add(new RestTemplateXhrTransport());

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
