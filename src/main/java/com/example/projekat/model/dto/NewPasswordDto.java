package com.example.projekat.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewPasswordDto {
    private String oldPassword;
    private String newPassword;
    private String newRepeatedPassword;
}
