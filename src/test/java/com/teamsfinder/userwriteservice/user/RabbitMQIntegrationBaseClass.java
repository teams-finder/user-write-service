package com.teamsfinder.userwriteservice.user;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;

public class RabbitMQIntegrationBaseClass extends IntegrationBaseClass {

    private static final RabbitMQContainer rabbitMQContainer;

    static {
        rabbitMQContainer = new RabbitMQContainer("rabbitmq:3-management" +
                "-alpine")
                .withQueue("keycloak.queue");
        rabbitMQContainer.start();
    }

    @DynamicPropertySource
    public static void setDatasourceProperites(DynamicPropertyRegistry registry) {
        IntegrationBaseClass.setDatasourceProperties(registry);
        registry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);
        registry.add("spring.rabbitmq.username",
                rabbitMQContainer::getAdminUsername);
        registry.add("spring.rabbitmq.password",
                rabbitMQContainer::getAdminPassword);
    }
}
