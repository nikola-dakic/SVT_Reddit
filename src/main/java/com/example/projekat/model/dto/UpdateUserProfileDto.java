package com.example.projekat.model.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateUserProfileDto {
    private String email;
    private String description;
    private String displayName;


}
