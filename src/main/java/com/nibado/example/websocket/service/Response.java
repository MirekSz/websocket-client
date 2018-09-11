
package com.nibado.example.websocket.service;

public class Response {

	private Long start;

	public Response(final Long start) {
		this.setStart(start);
	}

	public Response() {
	}

	public Long getStart() {
		return start;
	}

	public void setStart(final Long start) {
		this.start = start;
	}

}
