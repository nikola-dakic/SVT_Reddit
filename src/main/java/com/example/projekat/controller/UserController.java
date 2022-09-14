package com.example.projekat.controller;
import com.example.projekat.model.dto.*;
import com.example.projekat.model.entity.User;
import com.example.projekat.security.TokenUtils;
import com.example.projekat.repository.UserRepository;
import com.example.projekat.service.PostService;
import com.example.projekat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")

public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenUtils tokenUtils;
    @Autowired
    PostService postService;


    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody @Validated LoginDto authenticationRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails user = (UserDetails) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user);
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));

    }

    @PostMapping("/registration")
    public ResponseEntity<UserDto> create(@RequestBody @Validated UserDto newUser) {
        User createdUser = userService.createUser(newUser);

        if (createdUser == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto userDTO = new UserDto(createdUser);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }


    @PatchMapping("/changePassword")
    public ResponseEntity<Void> changePassword(@RequestBody @Validated NewPasswordDto newPasswordDTO) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        NewPasswordDto changedPassword = userService.changePassword(userDetails, newPasswordDTO);

        if (changedPassword == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<UserDto> getCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int total = userService.totalCarma(userDetails.getUsername());
        return new ResponseEntity<>(new UserDto(userService.findByUsername(userDetails.getUsername()), total), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
                                              @RequestBody @Validated UpdateUserProfileDto updatedUser) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userDTO = userService.updateUser(userId, updatedUser, userDetails.getUsername());
        if (userDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userDTO, HttpStatus.OK);

    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userOpt = userRepository.findFirstByUsername(userDetails.getUsername());
        return new ResponseEntity<>(postService.findAllByUserId(userOpt.get().getId()), HttpStatus.OK);
    }
}
