package com.project.backend.service.impl;

import com.project.backend.constain.RoleName;
import com.project.backend.dto.RoleDTO;
import com.project.backend.dto.UserDTO;
import com.project.backend.entity.Role;
import com.project.backend.entity.User;
import com.project.backend.mapper.UserMapper;
import com.project.backend.repository.RoleRepository;
import com.project.backend.repository.UserRepository;
import com.project.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO saveUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        if (Objects.isNull(user.getRoles())) {
            log.info("roles is null, create new roles");
            List<Role> roles = new ArrayList<>();
            user.setRoles(roles);
        }
        Role role = roleRepository.findByName(RoleName.USER);
        user.getRoles().add(role);
        userRepository.save(user);
        log.info("save success");
        return userMapper.toDTO(user);
    }

    @Override
    public RoleDTO saveRole(RoleDTO roleDTO) {
        Role role = Role.builder()
                .name(roleDTO.getName())
                .build();
        roleRepository.save(role);
        roleDTO = RoleDTO.builder()
                .id(role.getId())
                .name(roleDTO.getName())
                .build();
        return roleDTO;
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        User user = userRepository.findByUserName(userName);
        Role role = roleRepository.findByName(roleName);
    }

    @Override
    public UserDTO findByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        return userMapper.toDTO(user);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        users.forEach(user -> {
            userDTOList.add(userMapper.toDTO(user));
        });
        return userDTOList;
    }
}