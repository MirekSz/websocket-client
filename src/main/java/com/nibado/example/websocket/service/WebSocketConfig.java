
package com.nibado.example.websocket.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
@EnableScheduling
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
	@Autowired
	ApplicationContext context;

	@Override
	public void configureMessageBroker(final MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(final StompEndpointRegistry registry) {
		registry.addEndpoint("/hello");
		registry.addEndpoint("/hello").withSockJS();
	}

	@Scheduled(fixedDelay = 2000)
	public void sender() {
		Map<String, Object> data = new HashMap<>();
		data.put("content", "Hey " + new Date());
		context.getBean(SimpMessagingTemplate.class).convertAndSend("/topic/greetings", data);
	}
}
