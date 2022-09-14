package com.example.projekat.model.dto;

import com.example.projekat.model.entity.VoteType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NewVoteDto {

    private VoteType voteType;
}
