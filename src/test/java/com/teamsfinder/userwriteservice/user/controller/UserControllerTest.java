package com.teamsfinder.userwriteservice.user.controller;

import com.teamsfinder.userwriteservice.user.IntegrationBaseClass;
import com.teamsfinder.userwriteservice.user.creator.UserCreator;
import com.teamsfinder.userwriteservice.user.dto.EditUserRequestDto;
import com.teamsfinder.userwriteservice.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends IntegrationBaseClass {

    private static final String EDIT_STRING = "EDITED";
    private static final String EDIT_END_POINT = "/users";

    @Autowired
    private UserCreator userCreator;

    @Test
    void shouldEditUser() throws Exception {
        //given
        User user = userCreator.create();
        EditUserRequestDto editUserDto = new EditUserRequestDto(user.getId(),
                EDIT_STRING, EDIT_STRING, new ArrayList<>());
        String json =
                objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(editUserDto);

        //when
        ResultActions performRequest =
                mockMvc.perform(patch(EDIT_END_POINT).contentType(MediaType.APPLICATION_JSON).content(json));

        //then
        performRequest.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.githubProfileUrl").value(EDIT_STRING))
                .andExpect(jsonPath("$.profilePictureUrl").value(EDIT_STRING))
                .andExpect(jsonPath("$.tags").exists());
    }

    @Test
    void shouldThrowWhileEditingUser() throws Exception {
        //given
        EditUserRequestDto editUserDto = new EditUserRequestDto(1L, null,
                EDIT_STRING, new ArrayList<>());
        String json =
                objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(editUserDto);

        //when
        ResultActions performRequest =
                mockMvc.perform(patch(EDIT_END_POINT).contentType(MediaType.APPLICATION_JSON).content(json));

        //then
        performRequest.andExpect(status().isBadRequest());
    }

    @Test
    void shouldThrowBadGatewayWhileBlockingUser() throws Exception {
        //given

        //when
        ResultActions performRequest = mockMvc.perform(patch("/users/1/block"));

        //then
        performRequest.andExpect(status().isBadRequest());
    }
}