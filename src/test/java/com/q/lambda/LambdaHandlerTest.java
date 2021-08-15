package com.q.lambda;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.amazon.lambda.test.LambdaClient;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class LambdaHandlerTest {

    @Test
    public void testSimpleLambdaSuccess() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        
        APIGatewayProxyRequestEvent request = new APIGatewayProxyRequestEvent();
        InputObject in = new InputObject();
        in.setGreeting("Hello");
        in.setName("Stu");
        request.withBody(mapper.writeValueAsString(in));
        APIGatewayProxyResponseEvent out = LambdaClient.invoke(APIGatewayProxyResponseEvent.class, request);
        Assertions.assertEquals(200, out.getStatusCode());
    }

}
