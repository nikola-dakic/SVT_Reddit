package com.example.projekat.model.dto;

import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
public class LoginDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public LoginDto() {
        super();
    }

    public LoginDto(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
