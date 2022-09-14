package com.example.projekat.service.implementation;

import com.example.projekat.model.dto.NewPasswordDto;
import com.example.projekat.model.dto.PostDto;
import com.example.projekat.model.dto.UpdateUserProfileDto;
import com.example.projekat.model.dto.UserDto;
import com.example.projekat.model.entity.Community;
import com.example.projekat.model.entity.Moderator;
import com.example.projekat.model.entity.User;
import com.example.projekat.repository.CommunityRepository;
import com.example.projekat.repository.PostRepository;
import com.example.projekat.repository.UserRepository;
import com.example.projekat.service.PostService;
import com.example.projekat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    CommunityRepository communityRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        Optional<User> user = userRepository.findFirstByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

    @Override
    public User createUser(UserDto userDTO) {

        Optional<User> user = userRepository.findFirstByUsername(userDTO.getUsername());

        if (user.isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        newUser.setEmail(userDTO.getEmail());
        newUser.setDescription("");
        newUser.setDisplayName(userDTO.getUsername());
        newUser.setRegistrationDate(LocalDate.now());


        newUser = userRepository.save(newUser);
        return newUser;
    }

    @Transactional
    @Override
    public NewPasswordDto changePassword(UserDetails userDetails, NewPasswordDto newPasswordDTO) {

        if (!passwordEncoder.matches(newPasswordDTO.getOldPassword(), userDetails.getPassword())) {
            throw new IllegalArgumentException("Wrong old password");
        }
        if (!newPasswordDTO.getNewPassword().equals(newPasswordDTO.getNewRepeatedPassword())) {
            throw new IllegalArgumentException("New passwords should match");
        }
        String newPassword = passwordEncoder.encode(newPasswordDTO.getNewPassword());
        String oldPassword = passwordEncoder.encode(newPasswordDTO.getOldPassword());
        userRepository.setNewPasswordByUsername(newPassword, userDetails.getUsername());
        return new NewPasswordDto(oldPassword, newPassword, newPassword);
    }

    @Override
    public void removeModeratorRole(Long userId, Long communityId) {
        Optional<Community> communityOpt = communityRepository.findById(communityId);
        if (communityOpt.isEmpty()) {
            throw new IllegalArgumentException("No community with id: " + communityId);
        }
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("No user with id: " + userId);
        }
        User user = userOpt.get();
        Set<Moderator> moderatorRoles = user.getModeratorRoles();
        Moderator toDelete = null;
        for (Moderator moderatorRole : moderatorRoles) {
            Community community = moderatorRole.getCommunity();
            if (community.getId().equals(communityId)) {
                community.removeModerator(moderatorRole);
                communityRepository.save(community);
                toDelete = moderatorRole;
                break;
            }
        }
        user.removeModeratorRole(toDelete);
        userRepository.save(user);
    }

    @Override
    public UserDto updateUser(Long userId, UpdateUserProfileDto updatedUser, String username) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (!(userOpt.isPresent() && userOpt.get().getUsername().equals(username))) {
            throw new IllegalArgumentException("You can only update your data");
        }
        User user = userOpt.get();
        user.setEmail(updatedUser.getEmail());
        user.setDisplayName(updatedUser.getDisplayName());
        user.setDescription(updatedUser.getDescription());
        UserDto userDTO = new UserDto(userRepository.save(user));
        return userDTO;


    }

    @Override
    public UserDto getCurrentUser() {

        Object principalObject = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principalObject instanceof String) {
            UserDto userDTO = new UserDto();
            userDTO.setId(-1L);
            return userDTO;
        }
        UserDetails principal = (UserDetails) principalObject;
        Optional<User> user = userRepository.findFirstByUsername(principal.getUsername());
        if (user.isPresent()) {
            return new UserDto(user.get());
        }
        UserDto userDTO = new UserDto();
        userDTO.setId(-1L);
        return userDTO;
    }

    @Override
    public int totalCarma(String username) {
        User user = this.findByUsername(username);
        int sum = 0;
        List<PostDto> postDtos = postService.findAllByUserId(user.getId());
        for (PostDto postDTO : postDtos) {
            sum += postDTO.getCarma();
        }
        return sum;
    }

    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();

        List<UserDto> dtos = new ArrayList<>();
        for (User user : users) {
            dtos.add(new UserDto(user));
        }
        return dtos;
    }
}
