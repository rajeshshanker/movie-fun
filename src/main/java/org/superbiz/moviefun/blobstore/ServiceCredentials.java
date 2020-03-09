package org.superbiz.moviefun.blobstore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class ServiceCredentials {

    private final String vcapServices;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ServiceCredentials(String vcapServices) {
        this.vcapServices = vcapServices;
    }

    public String getCredential(String serviceName, String serviceType, String credentialKey) {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode root;

        try {
            root = objectMapper.readTree(vcapServices);
        } catch (IOException e) {
            throw new IllegalStateException("No VCAP_SERVICES found", e);
        }

        JsonNode services = root.path(serviceType);

        for (JsonNode service : services) {
            logger.info("Service name " +service);
            logger.info("Looking for Service name " +serviceName);
            if (Objects.equals(service.get("name").asText(), serviceName)) {

                return service.get("credentials").get(credentialKey).asText();
            }
        }

        throw new IllegalStateException("No "+ serviceName + " found in VCAP_SERVICES");
    }
}

