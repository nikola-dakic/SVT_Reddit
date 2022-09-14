package com.example.projekat.service.implementation;

import com.example.projekat.model.dto.NewPostDto;
import com.example.projekat.model.dto.PostDto;
import com.example.projekat.model.dto.UpdatePostDto;
import com.example.projekat.model.entity.Community;
import com.example.projekat.model.entity.Post;
import com.example.projekat.model.entity.VoteType;
import com.example.projekat.model.entity.User;
import com.example.projekat.repository.CommunityRepository;
import com.example.projekat.repository.PostRepository;
import com.example.projekat.repository.UserRepository;
import com.example.projekat.service.CommunityService;
import com.example.projekat.service.PostService;
import com.example.projekat.service.VoteService;
import com.example.projekat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;

    @Autowired
    VoteService voteService;

    @Autowired
    CommunityService communityService;


    @Override
    public List<PostDto> findAll() {
        List<Post> posts = postRepository.findAll();

        List<PostDto> postovi = new ArrayList<>();
        for (Post p : posts) {
            int carma = voteService.getCarmaForPostId(p.getId());
            if (!p.isDeleted()) {

                VoteType voteType = voteService.getVoteTypeForPostIdAndUserId(p.getId(), userService.getCurrentUser().getId());

                postovi.add(new PostDto(p, getCommunityByPost(p).getId(), carma, voteType));
            }

        }
        return postovi;
    }

    @Override
    public List<PostDto> findByCommunityPosts(Long communityId) {
        Community community = communityRepository.findById(communityId).get();
        Set<Post> comPosts = community.getPosts();
        List<PostDto> posts = new ArrayList<>();
        for (Post p : comPosts) {
            int carma = voteService.getCarmaForPostId(p.getId());
            if (!p.isDeleted()) {
                VoteType voteType = voteService.getVoteTypeForPostIdAndUserId(p.getId(), userService.getCurrentUser().getId());

                posts.add(new PostDto(p, community.getId(), carma, voteType));
            }

        }
        return posts;
    }

    @Override
    public List<PostDto> findAllByUserId(Long userId) {
        List<Post> posts = postRepository.findAllByPostedById(userId);
        List<PostDto> dtos = new ArrayList<>();
        for (Post p : posts) {
            int carma = voteService.getCarmaForPostId(p.getId());
            if (!p.isDeleted()) {

                VoteType voteType = voteService.getVoteTypeForPostIdAndUserId(p.getId(), userService.getCurrentUser().getId());

                dtos.add(new PostDto(p, getCommunityByPost(p).getId(), carma, voteType));
            }

        }
        return dtos;
    }

    @Override
    public Community getCommunityByPost(Post p) {
        List<Community> communities = communityRepository.findAll();
        for (Community community : communities) {
            if (community.getPosts().contains(p)) {
                return community;
            }
        }
        return null;
    }

    @Override
    public PostDto findById(Long id) {
        Optional<Post> postOpt = postRepository.findById(id);
        if (postOpt.isEmpty() || postOpt.get().isDeleted()) {
            throw new IllegalArgumentException("No post with id " + id);
        }

        Post p = postOpt.get();
        int carma = voteService.getCarmaForPostId(p.getId());
        VoteType voteType = voteService.getVoteTypeForPostIdAndUserId(p.getId(), userService.getCurrentUser().getId());

        PostDto postDTO = new PostDto(p, getCommunityByPost(p).getId(), carma, voteType);
        return postDTO;
    }

    @Override
    public PostDto save(NewPostDto newPost, String username) {
        Optional<User> userOpt = userRepository.findFirstByUsername(username);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("No user with username: " + username);
        }
        Optional<Community> communityOpt = communityRepository.findById(newPost.getCommunityId());
        if (communityOpt.isEmpty()) {
            throw new IllegalArgumentException("No community with id: " + (newPost.getCommunityId()));
        }
        Community community = communityOpt.get();

        Post post = new Post(null, newPost.getTitle(), newPost.getText(), LocalDate.now(), false, userOpt.get());
        community.addPost(post);


        Community com = communityRepository.save(community);
        List<Post> posts = new ArrayList<>(com.getPosts());
        Comparator<Post> c = (p1, p2) -> p2.getCreationDate().compareTo(p1.getCreationDate());
        posts.sort(c);
        for (Post p : posts) {
            if (p.getPostedBy().getId().equals(userOpt.get().getId())) {
                return new PostDto(p, community.getId(), 1, VoteType.UPVOTE);
            }

        }

        return null;
    }

    @Transactional
    @Override
    public PostDto update(Long id, UpdatePostDto updatedPost, String username) {
        Optional<Post> postOpt = postRepository.findById(id);
        User byUsername = userService.findByUsername(username);
        if (postOpt.isEmpty()) {
            throw new IllegalArgumentException("No post with id: " + id);
        }

        Post p = postOpt.get();
        if (byUsername.getRole().equals("ROLE_USER") && !p.getPostedBy().getUsername().equals(username)) {
            throw new IllegalArgumentException("No post with id: " + id);
        }
        p.setTitle(updatedPost.getTitle());
        p.setText(updatedPost.getText());

        Post updated = postRepository.save(p);
        PostDto res = new PostDto(updated);
        return res;
    }

    @Transactional
    @Override
    public PostDto delete(Long id, String username) {
        Optional<Post> postOpt = postRepository.findById(id);
        User byUsername = userService.findByUsername(username);

        if (postOpt.isEmpty()) {
            throw new IllegalArgumentException("No post with id: " + id);
        }
        Post p = postOpt.get();
        if (byUsername.getRole().equals("ROLE_USER") && !p.getPostedBy().getUsername().equals(username)) {
            throw new IllegalArgumentException("No post with id: " + id);
        }
        p.setDeleted(true);
        return new PostDto(postRepository.save(p));
    }


}
