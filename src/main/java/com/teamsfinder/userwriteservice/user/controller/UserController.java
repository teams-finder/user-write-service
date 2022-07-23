package com.teamsfinder.userwriteservice.user.controller;

import com.teamsfinder.userwriteservice.user.dto.EditUserDto;
import com.teamsfinder.userwriteservice.user.dto.UserResponseDto;
import com.teamsfinder.userwriteservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @PatchMapping
    UserResponseDto editUser(@Valid @RequestBody EditUserDto editUserDto){
        return userService.editUser(editUserDto);
    }

    @PatchMapping("/{id}/block")
    UserResponseDto blockUser(@PathVariable("id") Long id){
        return userService.blockUser(id);
    }
}
