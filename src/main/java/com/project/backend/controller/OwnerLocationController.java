package com.project.backend.controller;

import com.project.backend.dto.HistoryDTO;
import com.project.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1.0.0/location")
@Slf4j
@RequiredArgsConstructor
public class OwnerLocationController {
    private final UserService userService;

    @PostMapping("/history")
    public void addHistory(HistoryDTO history) {
        userService.addHistory(history);
    }
}
