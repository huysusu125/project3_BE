package com.project.backend.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String userName;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String address;
}
