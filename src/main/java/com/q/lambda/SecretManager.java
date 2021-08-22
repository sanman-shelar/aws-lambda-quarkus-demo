package com.q.lambda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;
import software.amazon.awssdk.services.secretsmanager.model.SecretsManagerException;

public class SecretManager {

    private Secret secret;
    private String secretId;

    public SecretManager(String secretId) {
        this.secretId = secretId;
        get(secretId);
    }

    public String getSecretId() {
        return secretId;
    }

    public Secret getSecret() {
        return secret;
    }

    private void get(String secretName) {
        ObjectMapper objectMapper = new ObjectMapper();
        SecretsManagerClient secretsClient = SecretsManagerClient.builder().region(Region.AP_SOUTH_1).build();
        try {
            GetSecretValueRequest valueRequest = GetSecretValueRequest.builder().secretId(secretName).build();

            GetSecretValueResponse valueResponse = secretsClient.getSecretValue(valueRequest);
            secret = objectMapper.readValue(valueResponse.secretString(), Secret.class);
            secretsClient.close();
        } catch (SecretsManagerException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
