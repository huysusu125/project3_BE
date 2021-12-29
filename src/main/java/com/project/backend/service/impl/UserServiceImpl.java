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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("user: {}", username);
        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error("User not found: {}1" + username);
            throw new UsernameNotFoundException("User not found: " + username);
        }
        log.info("User found");
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
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
        User user = userRepository.findByUsername(userName);
        Role role = roleRepository.findByName(roleName);
    }

    @Override
    public UserDTO findById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO findByUserName(String userName) {
        User user = userRepository.findByUsername(userName);
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
