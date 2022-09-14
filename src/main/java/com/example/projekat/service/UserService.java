package com.example.projekat.service;

import com.example.projekat.model.dto.NewPasswordDto;
import com.example.projekat.model.dto.UpdateUserProfileDto;
import com.example.projekat.model.dto.UserDto;
import com.example.projekat.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    User createUser(UserDto userDTO);

    NewPasswordDto changePassword(UserDetails userDetails, NewPasswordDto newPasswordDTO);

    void removeModeratorRole(Long userId, Long communityId);

    UserDto updateUser(Long userId, UpdateUserProfileDto updatedUser, String username);

    UserDto getCurrentUser();

    int totalCarma(String username);

    List<UserDto> findAll();
}
