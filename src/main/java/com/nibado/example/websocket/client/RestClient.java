
package com.nibado.example.websocket.client;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.nibado.example.websocket.service.Request;
import com.nibado.example.websocket.service.Response;

public class RestClient {

	public static AtomicLong SUM = new AtomicLong(0);

	public static void main(final String[] args) throws Exception {
		AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
		asyncRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		for (int i = 0; i < 1000; i++) {
			// Thread.sleep(10);
			Response response = restTemplate.postForEntity("http://localhost:8080/rock/sumSync",
					new Request(System.currentTimeMillis()), Response.class).getBody();
			long end = System.currentTimeMillis() - response.getStart();
			// System.out.println("Response time " + end);
			SUM.addAndGet(end);
		}
		System.out.println(SUM);
	}

}
