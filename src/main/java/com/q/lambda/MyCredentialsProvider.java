package com.q.lambda;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.arc.Unremovable;
import io.quarkus.credentials.CredentialsProvider;

@ApplicationScoped
@Unremovable
public class MyCredentialsProvider implements CredentialsProvider {

    @Override
    public Map<String, String> getCredentials(String credentialsProviderName) {
        SecretManager secretManager = new SecretManager("postgres-testdb-secret");
        Map<String, String> properties = new HashMap<>();
        properties.put(USER_PROPERTY_NAME, secretManager.getSecret().getUsername());
        properties.put(PASSWORD_PROPERTY_NAME, secretManager.getSecret().getPassword());
        return properties;
    }

}
