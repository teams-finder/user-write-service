package com.teamsfinder.userwriteservice.user.broker;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
class KeycloakListener {

//    @RabbitListener(queues = "KK.EVENT.#")
//    public void handleUserCreateAccount(String s){
//        System.out.println(s);
//    }
}
