package com.sda.auction.service.impl;

import com.sda.auction.dto.LoginDto;
import com.sda.auction.dto.UserDto;
import com.sda.auction.mapper.UserMapper;
import com.sda.auction.model.Role;
import com.sda.auction.model.User;
import com.sda.auction.repository.RoleRepository;
import com.sda.auction.repository.UserRepository;
import com.sda.auction.service.SecurityService;
import com.sda.auction.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private SecurityService securityService;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, SecurityService securityService, RoleRepository roleRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

        this.securityService = securityService;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        //convertim  DTO in entity
        User user = userMapper.convert(userDto);
        encodePassword(user);
        addUserRoles(user);

        //persistam in baza de date
        User savedUser = userRepository.save(user);

        //convertim entitatea persistata inapoi in DTO pentru a o intoarce catre requester(ex.postman)
        return userMapper.convert(savedUser);

    }

    private void addUserRoles(User user) {
        Role role = roleRepository.findByRoleName("user");
        user.addRole(role);
        Role admin = roleRepository.findByRoleName("admin");
        user.addRole(admin);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public LoginDto login(LoginDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user == null) {
            throw new RuntimeException("user account with this email doesnt exist!");
        }
        if (securityService.passwordMatch(loginDto, user)) {
            return securityService.createDtoWithJwt(user);
        }
        throw new RuntimeException("passwords do not match!");
    }

    private void encodePassword(User user) {

        //encode user's password and put it in passwordEncoded variable
        String passwordEncoded = passwordEncoder.encode(user.getPassword());
        //set the encoded password to user entity
        user.setPassword(passwordEncoded);
    }
}
