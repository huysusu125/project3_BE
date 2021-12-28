package com.project.backend.controller;

import com.project.backend.dto.UserDTO;
import com.project.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public-api")
@Slf4j
@RequiredArgsConstructor
public class PublicAPI {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {
        log.info("create user");
        return ResponseEntity.ok(userService.saveUser(user));
    }
}
