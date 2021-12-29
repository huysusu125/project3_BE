package com.project.backend.service;

import com.project.backend.dto.RoleDTO;
import com.project.backend.dto.UserDTO;

import java.util.List;


public interface UserService {
    UserDTO saveUser(UserDTO userDTO);
    RoleDTO saveRole(RoleDTO roleDTO);
    void addRoleToUser(String userName, String roleName);
    UserDTO findById(long id);
    UserDTO findByUserName(String userName);
    List<UserDTO> findAll();
}
