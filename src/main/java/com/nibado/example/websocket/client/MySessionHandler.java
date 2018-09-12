
package com.nibado.example.websocket.client;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import com.nibado.example.websocket.service.Response;

public class MySessionHandler extends StompSessionHandlerAdapter {

	Logger log = LoggerFactory.getLogger(MySessionHandler.class);
	public static AtomicLong SUM = new AtomicLong(0);

	@Override
	public void afterConnected(final StompSession session, final StompHeaders connectedHeaders) {
		session.subscribe("/topic/greetings", this);

		log.info("New session: {}", session.getSessionId());
	}

	@Override
	public void handleException(final StompSession session, final StompCommand command, final StompHeaders headers, final byte[] payload,
			final Throwable exception) {
		exception.printStackTrace();
	}

	@Override
	public Type getPayloadType(final StompHeaders headers) {
		return Response.class;
	}

	@Override
	public synchronized void handleFrame(final StompHeaders headers, final Object payload) {
		Long start = ((Response) payload).getStart();
		long end = System.currentTimeMillis() - start;
		// System.out.println("Response time " + end);
		SUM.addAndGet(end);
	}
}
