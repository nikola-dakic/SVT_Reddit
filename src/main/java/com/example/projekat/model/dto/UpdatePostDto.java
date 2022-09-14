package com.example.projekat.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdatePostDto {
    private String title;
    private String text;
}
