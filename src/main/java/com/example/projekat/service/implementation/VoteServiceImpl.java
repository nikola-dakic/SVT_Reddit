package com.example.projekat.service.implementation;

import com.example.projekat.model.dto.NewVoteDto;
import com.example.projekat.model.dto.PostDto;
import com.example.projekat.model.entity.Post;
import com.example.projekat.model.entity.Vote;
import com.example.projekat.model.entity.VoteType;
import com.example.projekat.model.entity.User;
import com.example.projekat.repository.PostRepository;
import com.example.projekat.repository.VoteRepository;
import com.example.projekat.repository.UserRepository;
import com.example.projekat.service.PostService;
import com.example.projekat.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class VoteServiceImpl implements VoteService {
    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Override
    public int getCarmaForPostId(Long id) {
        int sum = 0;
        List<Vote> votes = voteRepository.findAllByPostId(id);
        for (Vote vote : votes) {
            sum += vote.getVoteType().getScore();
        }
        return sum;
    }

    @Override
    public VoteType getVoteTypeForPostIdAndUserId(Long postId, Long userId) {
        VoteType userVote = null;
        List<Vote> votes = voteRepository.findAllByPostId(postId);
        for (Vote vote : votes) {
            if (vote.getUser().getId().equals(userId)) {
                userVote = vote.getVoteType();
            }
        }
        return userVote;
    }


    @Override
    public void upvote(PostDto newPost) {
        Optional<User> userOpt = userRepository.findFirstByUsername(newPost.getUsername());
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("No user with username: " + newPost.getUsername());
        }
        User user = userOpt.get();
        Optional<Post> postOpt = postRepository.findById(newPost.getId());
        if (postOpt.isEmpty()) {
            throw new IllegalArgumentException("No post with id: " + newPost.getId());
        }
        Post post = postOpt.get();
        Vote vote = new Vote(null, VoteType.UPVOTE, LocalDate.now(), user, post);
        voteRepository.save(vote);
    }

    @Override
    public Vote savePostVote(NewVoteDto newVoteDTO, String username, Long postId) {

        Optional<User> userOpt = userRepository.findFirstByUsername(username);
        if (userOpt.isEmpty()) {
            throw new IllegalArgumentException("No user with username: " + username);
        }
        User user = userOpt.get();
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isEmpty()) {
            throw new IllegalArgumentException("No post with id: " + postId);
        }
        Post post = postOpt.get();

        List<Vote> allVotes = voteRepository.findAllByPostId(postId);
        for (Vote vote : allVotes) {
            boolean alreadyExist = vote.getUser().getUsername().equals(username) && vote.getPost().getId().equals(postId);
            if (alreadyExist && vote.getVoteType().equals(newVoteDTO.getVoteType())) {
                return vote;
            } else if (alreadyExist) {
                voteRepository.deleteById(vote.getId());
            }
        }
        Vote vote = new Vote(null, newVoteDTO.getVoteType(), LocalDate.now(), user, post);
        return voteRepository.save(vote);
    }
}
