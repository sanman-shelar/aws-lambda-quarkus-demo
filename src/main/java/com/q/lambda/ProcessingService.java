package com.q.lambda;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class ProcessingService {

    @Inject
    ObjectMapper mapper;
    
    @Inject
    Logger log;
    
    public static final String CAN_ONLY_GREET_NICKNAMES = "Can only greet nicknames";

    public APIGatewayProxyResponseEvent process(String inputBody) throws JsonMappingException, JsonProcessingException {
        log.info("Input Body: " + inputBody);
        InputObject input = mapper.readValue(inputBody, InputObject.class);
        if (input.getName().equals("Stuart")) {
            throw new IllegalArgumentException(CAN_ONLY_GREET_NICKNAMES);
        }
        String result = input.getGreeting() + " " + input.getName();
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(result);
        
        
    }
}
