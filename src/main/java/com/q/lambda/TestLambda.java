package com.q.lambda;

import java.sql.SQLException;

import javax.inject.Inject;
import javax.inject.Named;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;

@Named("test")
public class TestLambda implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject
    ProcessingService service;

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, final Context context) {
        try {
            return service.process(request.getBody());
        } catch (JsonProcessingException | SQLException e) {            
            e.printStackTrace();
        }
        return new APIGatewayProxyResponseEvent().withStatusCode(500);
    }
}
