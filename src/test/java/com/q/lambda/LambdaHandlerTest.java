package com.q.lambda;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.amazon.lambda.test.LambdaClient;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@QuarkusTest
public class LambdaHandlerTest {

    @InjectMock
    PersonRepository personRepository;
    
    @Test
    public void testSimpleLambdaSuccess() throws Exception {
        
        Person person = new Person(1, "Test person", 22);
        Mockito.when(personRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(person));
        
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
