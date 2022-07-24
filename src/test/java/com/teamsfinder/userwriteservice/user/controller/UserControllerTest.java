package com.teamsfinder.userwriteservice.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamsfinder.userwriteservice.user.TestContainer;
import com.teamsfinder.userwriteservice.user.dto.EditUserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

@AutoConfigureMockMvc
class UserControllerTest extends TestContainer {
    private static final String EDIT_STRING = "EDITED";
    private static final String EDIT_END_POINT = "/users";
    private static final String ID_JSON_PATH = "$.id";
    private static final String GITHUB_JSON_PATH = "$.githubProfileUrl";
    private static final String PICTURE_JSON_PATH = "$.profilePictureUrl";
    private static final String TAGS_JSON_PATH = "$.tags";
    private static final String BLOCK_END_POINT = "/users/1/block";

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldEditUser() throws Exception {
        //given
        EditUserDto editUserDto = new EditUserDto(1L, EDIT_STRING, EDIT_STRING, new ArrayList<>());
        String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(editUserDto);
        //when
        ResultActions performRequest = mockMvc.perform(MockMvcRequestBuilders.patch(EDIT_END_POINT).contentType(MediaType.APPLICATION_JSON).content(json));
        //then
        performRequest.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(ID_JSON_PATH).value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath(GITHUB_JSON_PATH).value(EDIT_STRING))
                .andExpect(MockMvcResultMatchers.jsonPath(PICTURE_JSON_PATH).value(EDIT_STRING))
                .andExpect(MockMvcResultMatchers.jsonPath(TAGS_JSON_PATH).exists());
    }

    @Test
    void shouldThrowWhileEditingUser() throws Exception {
        //given
        EditUserDto editUserDto = new EditUserDto(1L, null, EDIT_STRING, new ArrayList<>());
        String json = objectMapper.writer().withDefaultPrettyPrinter().writeValueAsString(editUserDto);
        //when
        ResultActions performRequest = mockMvc.perform(MockMvcRequestBuilders.patch(EDIT_END_POINT).contentType(MediaType.APPLICATION_JSON).content(json));
        //then
        performRequest.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldThrowBadGatewayWhileBlockingUser() throws Exception {
        //given
        //when
        ResultActions performRequest = mockMvc.perform(MockMvcRequestBuilders.patch(BLOCK_END_POINT));
        //then
        performRequest.andExpect(MockMvcResultMatchers.status().isBadGateway());
    }
}