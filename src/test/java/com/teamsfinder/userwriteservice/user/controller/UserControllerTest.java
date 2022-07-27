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
    private static final String ID_JSON_PATH = "$.id";
    private static final String GITHUB_JSON_PATH = "$.githubProfileUrl";
    private static final String PICTURE_JSON_PATH = "$.profilePictureUrl";
    private static final String TAGS_JSON_PATH = "$.tags";
    private static final String BLOCK_END_POINT = "/users/1/block";

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
                .andExpect(jsonPath(ID_JSON_PATH).value(user.getId()))
                .andExpect(jsonPath(GITHUB_JSON_PATH).value(EDIT_STRING))
                .andExpect(jsonPath(PICTURE_JSON_PATH).value(EDIT_STRING))
                .andExpect(jsonPath(TAGS_JSON_PATH).exists());
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
        ResultActions performRequest = mockMvc.perform(patch(BLOCK_END_POINT));

        //then
        performRequest.andExpect(status().isBadRequest());
    }
}