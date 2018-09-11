
package com.nibado.example.websocket.client;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;

import com.nibado.example.websocket.service.Request;
import com.nibado.example.websocket.service.Response;

public class RestClient {

	public static void main(final String[] args) {
		AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();
		asyncRestTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		Response response = restTemplate
				.postForEntity("http://127.0.0.1:8080/helloSync", new Request(System.currentTimeMillis()), Response.class).getBody();
		long end = System.currentTimeMillis() - response.getStart();
		System.out.println("Response time " + end);

		response = restTemplate.postForEntity("http://127.0.0.1:8080/helloSync", new Request(System.currentTimeMillis()), Response.class)
				.getBody();
		end = System.currentTimeMillis() - response.getStart();
		System.out.println("Response time " + end);

		response = restTemplate.postForEntity("http://127.0.0.1:8080/helloSync", new Request(System.currentTimeMillis()), Response.class)
				.getBody();
		end = System.currentTimeMillis() - response.getStart();
		System.out.println("Response time " + end);

	}

}
