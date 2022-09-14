package com.example.projekat.model.dto;

import com.example.projekat.model.entity.Post;
import com.example.projekat.model.entity.VoteType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostDto {

    private Long id;
    private String title;
    private String text;
    private String imagePath;
    private LocalDate creationDate;
    private String username;
    private Long communityId;
    private int carma;
    private VoteType userVote;


    public PostDto(Post p, Long communityId, int carma, VoteType voteType) {
        setId(p.getId());
        setTitle(p.getTitle());
        setText(p.getText());
        setCreationDate(p.getCreationDate());
        setUsername(p.getPostedBy().getUsername());
        setCommunityId(communityId);
        setCarma(carma);
        setUserVote(voteType);
    }

    public PostDto(Post p) {
        setId(p.getId());
        setTitle(p.getTitle());
        setText(p.getText());
        setCreationDate(p.getCreationDate());
        setUsername(p.getPostedBy().getUsername());
        setCommunityId(null);
        setCarma(-1);
    }

}
