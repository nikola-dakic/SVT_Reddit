package com.example.projekat.service.implementation;

import com.example.projekat.model.dto.CommunityDto;
import com.example.projekat.model.dto.NewCommunityDto;
import com.example.projekat.model.dto.PostDto;
import com.example.projekat.model.dto.UpdateCommunityDto;
import com.example.projekat.model.entity.*;
import com.example.projekat.repository.CommunityRepository;
import com.example.projekat.repository.UserRepository;
import com.example.projekat.service.CommunityService;
import com.example.projekat.service.PostService;
import com.example.projekat.service.VoteService;
import com.example.projekat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    VoteService voteService;

    @Autowired
    PostService postService;

    @Override
    public List<CommunityDto> findAll() {
        List<Community> all = this.communityRepository.findAll();
        List<CommunityDto> communityDtos = new ArrayList<>();
        for (Community c : all) {
            if (!c.isSuspend()) {
                communityDtos.add(new CommunityDto(c, getPostsWithCarma(c)));
            }

        }
        return communityDtos;
    }

    @Override
    public CommunityDto findById(Long id) {
        Optional<Community> communityOpt = communityRepository.findById(id);
        if (communityOpt.isEmpty()) {
            throw new IllegalArgumentException("No community with id: " + id);
        }
        Community community = communityOpt.get();
        Set<PostDto> posts = getPostsWithCarma(community);
        return new CommunityDto(community, posts);
    }

    private Set<PostDto> getPostsWithCarma(Community community) {
        Set<PostDto> posts = new HashSet<>();
        for (Post post : community.getPosts()) {
            int carma = voteService.getCarmaForPostId(post.getId());
            VoteType voteType = voteService.getVoteTypeForPostIdAndUserId(post.getId(), userService.getCurrentUser().getId());
            posts.add(new PostDto(post, community.getId(), carma, voteType));
        }
        return posts;
    }

    @Override
    public CommunityDto createCommunity(NewCommunityDto newCommunityDTO, String username) {

        Optional<User> userOpt = userRepository.findFirstByUsername(username);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("No user with username: " + username);
        }

        Community community = new Community(null, newCommunityDTO.getName(), newCommunityDTO.getDescription(), LocalDate.now(), false,
                null, new HashSet<>(), new HashSet<>());
        User user = userOpt.get();
        Moderator moderator = new Moderator(null, user, null);
        user.addModeratorRole(moderator);
        community.addModerator(moderator);
        Community saved = communityRepository.save(community);
        return new CommunityDto(saved, new HashSet<>());
    }

    @Override
    public CommunityDto update(Long id, UpdateCommunityDto updateCommunity, String username) {
        Optional<User> userOpt = userRepository.findFirstByUsername(username);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("No user with username: " + username);
        }

        Optional<Community> communityOpt = communityRepository.findById(id);
        if (communityOpt.isEmpty()) {
            throw new IllegalArgumentException("No community with id: " + id);
        }
        Community community = communityOpt.get();
        community.setName(updateCommunity.getName());
        community.setDescription(updateCommunity.getDescription());
        return new CommunityDto(communityRepository.save(community), getPostsWithCarma(community));
    }
}
