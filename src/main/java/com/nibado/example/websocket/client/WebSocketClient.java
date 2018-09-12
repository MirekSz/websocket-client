
package com.nibado.example.websocket.client;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.nibado.example.websocket.service.Request;

public class WebSocketClient {

	public static void main(final String... argv) throws Exception, ExecutionException {
		StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
		WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());
		ConcurrentTaskScheduler taskScheduler = new ConcurrentTaskScheduler();
		ThreadPoolTaskScheduler sche = new ThreadPoolTaskScheduler();
		sche.setPoolSize(10);
		stompClient.setTaskScheduler(sche);

		String url = "ws://127.0.0.1:8080/hello";
		StompSessionHandler sessionHandler = new MySessionHandler();
		ListenableFuture<StompSession> connect = stompClient.connect(url, sessionHandler);
		for (int i = 0; i < 10000; i++) {
			connect.get().send("/app/hello", new Request(System.currentTimeMillis()));
			Thread.sleep(10);
		}
		System.out.println(MySessionHandler.SUM);
		new Scanner(System.in).nextLine();
	}
}
