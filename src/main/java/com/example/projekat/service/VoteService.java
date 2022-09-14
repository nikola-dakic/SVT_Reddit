package com.example.projekat.service;


import com.example.projekat.model.dto.NewVoteDto;
import com.example.projekat.model.dto.PostDto;
import com.example.projekat.model.entity.Vote;
import com.example.projekat.model.entity.VoteType;

public interface VoteService {
    int getCarmaForPostId(Long id);

    VoteType getVoteTypeForPostIdAndUserId(Long postId, Long userId);

    Vote savePostVote(NewVoteDto newVoteDTO, String username, Long postId);

    void upvote(PostDto newPost);


}
