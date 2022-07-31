package com.teamsfinder.userwriteservice.user;

import org.testcontainers.containers.RabbitMQContainer;

class RabbitMQIntegrationBaseClass extends IntegrationBaseClass {

    private static final RabbitMQContainer rabbitMQContainer;

    static {
        rabbitMQContainer = new RabbitMQContainer();
    }

}
