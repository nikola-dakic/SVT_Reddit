package com.example.projekat.model.dto;

import com.example.projekat.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDto {
    private Long id;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
    private String avatar;
    private String description;
    private LocalDate registrationDate;
    private String displayName;
    private String role;
    private boolean isDelete;
    private int userCarma;

    public UserDto(User createdUser) {
        this.id = createdUser.getId();
        this.username = createdUser.getUsername();
        this.password = createdUser.getPassword();
        this.email = createdUser.getEmail();
        this.description = createdUser.getDescription();
        this.registrationDate = createdUser.getRegistrationDate();
        this.displayName = createdUser.getDisplayName();
        this.role = createdUser.getRole();
    }

    public UserDto(User createdUser, int userCarma) {
        this.id = createdUser.getId();
        this.username = createdUser.getUsername();
        this.password = createdUser.getPassword();
        this.email = createdUser.getEmail();
        this.description = createdUser.getDescription();
        this.registrationDate = createdUser.getRegistrationDate();
        this.displayName = createdUser.getDisplayName();
        this.role = createdUser.getRole();
        this.userCarma = userCarma;
    }

}
