package com.example.projekat.controller;

import com.example.projekat.model.dto.NewPostDto;
import com.example.projekat.model.dto.NewVoteDto;
import com.example.projekat.model.dto.PostDto;
import com.example.projekat.model.dto.UpdatePostDto;
import com.example.projekat.model.entity.Vote;
import com.example.projekat.service.PostService;
import com.example.projekat.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    VoteService voteService;

    @PostMapping
    public ResponseEntity<PostDto> create(@RequestBody @Validated NewPostDto newPost) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PostDto saved = postService.save(newPost, userDetails.getUsername());

        if (saved == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        PostDto postDTO = postService.findById(saved.getId());
        voteService.upvote(postDTO);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAll() {
        List<PostDto> all = postService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable("id") Long id) {
        PostDto postDTO = postService.findById(id);

        if (postDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostDto> update(@PathVariable("id") Long id, @RequestBody @Validated UpdatePostDto updatedPost) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PostDto saved = postService.update(id, updatedPost, userDetails.getUsername());
        if (saved == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        PostDto deleted = postService.delete(id, userDetails.getUsername());
        if (deleted == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PatchMapping("/{postId}/vote")
    public ResponseEntity<PostDto> createVote(@PathVariable("postId") Long postId,
                                                  @RequestBody @Validated NewVoteDto newVote) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Vote savedVote = voteService.savePostVote(newVote, userDetails.getUsername(), postId);
        if (savedVote == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        PostDto postDTO = postService.findById(postId);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @GetMapping("/communities/{communityId}")
    public ResponseEntity<List<PostDto>> getCommunityPosts(@PathVariable("communityId") Long communityid) {
        List<PostDto> posts = postService.findByCommunityPosts(communityid);
        if (posts.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
