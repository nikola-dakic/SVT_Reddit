package com.example.projekat.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewPostDto {
    @NotBlank
    private String title;
    @NotBlank
    private String text;
    private Long communityId;
}
