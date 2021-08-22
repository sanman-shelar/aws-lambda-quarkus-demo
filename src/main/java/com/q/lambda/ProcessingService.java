package com.q.lambda;

import java.sql.SQLException;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
@Transactional
public class ProcessingService {

    @Inject
    ObjectMapper mapper;
    
    private final PersonRepository personRepository;
    public static final String CAN_ONLY_GREET_NICKNAMES = "Can only greet nicknames";
    
    public ProcessingService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public APIGatewayProxyResponseEvent process(String inputBody) throws JsonMappingException, JsonProcessingException, SQLException {
        System.out.println("Input Body: " + inputBody);
        
        Optional<Person> persons = personRepository.findById(1);
        
        System.out.println(persons.get().toString());
        
        InputObject input = mapper.readValue(inputBody, InputObject.class);
        if (input.getName().equals("Stuart")) {
            throw new IllegalArgumentException(CAN_ONLY_GREET_NICKNAMES);
        }
        String result = input.getGreeting() + " " + input.getName();
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(result);
    }
}
