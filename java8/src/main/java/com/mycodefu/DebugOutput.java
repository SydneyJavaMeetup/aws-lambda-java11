package com.mycodefu;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;

import java.util.List;

public class DebugOutput {
    private APIGatewayProxyRequestEvent event;
    private List<String> runtimeInputArguments;

    public DebugOutput() {
    }

    public DebugOutput(APIGatewayProxyRequestEvent event, List<String> runtimeInputArguments) {
        this.event = event;
        this.runtimeInputArguments = runtimeInputArguments;
    }

    public APIGatewayProxyRequestEvent getEvent() {
        return event;
    }

    public List<String> getRuntimeInputArguments() {
        return runtimeInputArguments;
    }
}
