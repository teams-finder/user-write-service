package com.teamsfinder.userwriteservice.user.controller;

import com.teamsfinder.userwriteservice.user.dto.EditUserDto;
import com.teamsfinder.userwriteservice.user.dto.UserDto;
import com.teamsfinder.userwriteservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @PatchMapping
    ResponseEntity<UserDto> editUser(@RequestBody EditUserDto editUserDto){
        return ResponseEntity.ok(userService.editUser(editUserDto));
    }

    @PatchMapping("/{id}/block")
    ResponseEntity<UserDto> blockUser(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.blockUser(id));
    }
}
