package com.q.lambda;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.agroal.api.AgroalDataSource;

@ApplicationScoped
public class ProcessingService {

    @Inject
    ObjectMapper mapper;
    
    @Inject
    Logger log;
    
    @Inject
    AgroalDataSource dataSource;
    
    public static final String CAN_ONLY_GREET_NICKNAMES = "Can only greet nicknames";

    public APIGatewayProxyResponseEvent process(String inputBody) throws JsonMappingException, JsonProcessingException, SQLException {
        log.info("Input Body: " + inputBody);
        
        Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs=stmt.executeQuery("select * from person");
        while(rs.next()){
            System.out.println(rs.getInt(1)+"  "+rs.getString(2));
        }
        stmt.close();
        conn.close();
        
        InputObject input = mapper.readValue(inputBody, InputObject.class);
        if (input.getName().equals("Stuart")) {
            throw new IllegalArgumentException(CAN_ONLY_GREET_NICKNAMES);
        }
        String result = input.getGreeting() + " " + input.getName();
        return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(result);
        
        
    }
}
