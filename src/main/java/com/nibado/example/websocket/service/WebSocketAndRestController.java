
package com.nibado.example.websocket.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebSocketAndRestController {

	Logger log = LoggerFactory.getLogger(WebSocketAndRestController.class);

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Response greeting(final Request message) throws Exception {
		// log.info("Received hello: {}", message.getStart());
		return new Response(message.getStart());
	}

	@PostMapping("/helloSync")
	@ResponseBody
	public Response helloSync(@RequestBody final Request message) throws Exception {
		// log.info("Received hello: {}", message.getStart());
		return new Response(message.getStart());
	}
}
