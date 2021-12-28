package com.project.backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}
