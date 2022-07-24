package com.teamsfinder.userwriteservice.user.keycloak;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.teamsfinder.userwriteservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KeycloakListener {
    private static final String USER_ID_PATH = "userId";
    private static final String QUOTE = "\"";
    private static final String EMPTY_STRING = "";

    private final UserService userService;

    @RabbitListener(queues = "keycloak.queue")
    public void handleUserCreateAccount(String data) throws JsonProcessingException {
        ObjectNode node = new ObjectMapper().readValue(data, ObjectNode.class);
        JsonNode keyCloakIdField = node.get(USER_ID_PATH);
        String keyCloakIdString = keyCloakIdField.toString();
        String replacedQuoteKeyCloakId = keyCloakIdString.replaceAll(QUOTE, EMPTY_STRING);
        userService.createUser(replacedQuoteKeyCloakId);
    }
}
