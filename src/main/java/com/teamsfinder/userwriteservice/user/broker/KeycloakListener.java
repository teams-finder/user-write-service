package com.teamsfinder.userwriteservice.user.broker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.teamsfinder.userwriteservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KeycloakListener {
    private static final String USER_ID_PATH = "userId";
    private final UserService userService;

    @RabbitListener(queues = "KK.EVENT.CLIENT.TeamsFinder.SUCCESS.teamsfinder.REGISTER")
    public void handleUserCreateAccount(String data) throws JsonProcessingException {
        ObjectNode node = new ObjectMapper().readValue(data, ObjectNode.class);
        JsonNode userIdField = node.get(USER_ID_PATH);
        userService.createUser(userIdField.toString());
    }
}
