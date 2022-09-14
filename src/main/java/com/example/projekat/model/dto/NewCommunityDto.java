package com.example.projekat.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewCommunityDto {
    @NotBlank
    private String name;
    private String description;
}
