package com.example.projekat.service;

import com.example.projekat.model.dto.NewPostDto;
import com.example.projekat.model.dto.PostDto;
import com.example.projekat.model.dto.UpdatePostDto;
import com.example.projekat.model.entity.Community;
import com.example.projekat.model.entity.Post;

import java.util.List;

public interface PostService {
    List<PostDto> findAll();

    List<PostDto> findByCommunityPosts(Long communityId);

    List<PostDto> findAllByUserId(Long userId);

    Community getCommunityByPost(Post p);

    PostDto findById(Long id);

    PostDto save(NewPostDto newPost, String username);

    PostDto update(Long id, UpdatePostDto updatedPost, String username);

    PostDto delete(Long id, String username);
}
