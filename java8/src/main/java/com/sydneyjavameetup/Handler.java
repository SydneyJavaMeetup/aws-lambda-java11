package com.sydneyjavameetup;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sydneyjavameetup.ApiGatewayResponse.prettyObjectWriter;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
	private static final AtomicInteger executionCount = new AtomicInteger(0);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		boolean DEBUG =
				input.containsKey("queryStringParameters")
				&& input.get("queryStringParameters") instanceof Map
				&& "true".equalsIgnoreCase((String)((Map)input.get("queryStringParameters")).get("debug"));
		try {
			if (DEBUG) {
				context.getLogger().log(String.format("received: \n%s", prettyObjectWriter.writeValueAsString(input)));
			}
		} catch (JsonProcessingException ignored) {}

		Response responseBody = new Response(executionCount.getAndIncrement(), "Go Serverless v1.x! Your function executed successfully!", DEBUG ? input : null);
		return ApiGatewayResponse.builder(context.getLogger())
				.setStatusCode(200)
				.setObjectBody(responseBody)
				.setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
				.build();
	}
}
