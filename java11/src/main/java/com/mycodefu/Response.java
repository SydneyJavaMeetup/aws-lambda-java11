package com.mycodefu;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Response {
	private final int executionCount;
	private final String message;
	private final APIGatewayProxyRequestEvent input;

	public Response(int executionCount, String message, APIGatewayProxyRequestEvent input) {
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
	public APIGatewayProxyRequestEvent getInput() {
		return this.input;
	}


	public enum Coldness {
		Cold,
		Hot
	}
}
