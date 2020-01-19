package com.sydneyjavameetup;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

public class Response {
	private final int executionCount;
	private final String message;
	private final Map<String, Object> input;

	public Response(int executionCount, String message, Map<String, Object> input) {
		this.executionCount = executionCount;
		this.message = message;
		this.input = input;
	}

	public String getMessage() {
		return this.message;
	}

	public Coldness getColdness() {
		return executionCount == 0 ? Coldness.Cold : Coldness.Hot;
	}

	public int getExecutionCount() {
		return executionCount;
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public Map<String, Object> getInput() {
		return this.input;
	}


	public enum Coldness {
		Cold,
		Hot
	}
}
