package com.example.projekat.model.dto;

import com.example.projekat.model.entity.Community;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommunityDto {

    private Long id;
    private String name;
    private String description;
    private LocalDate creationDate;
    private boolean isSuspend;
    private String suspendReason;
    private Set<PostDto> posts = new HashSet<>();
    private Set<String> moderatorUsernames = new HashSet<>();


    public CommunityDto(Community c, Set<PostDto> posts) {
        setModeratorUsernames(new HashSet<>());
        if (c.getModerators() != null && !c.getModerators().isEmpty()) {
            setModeratorUsernames(Set.of(c.getModerators().iterator().next().getUser().getUsername()));
        }

        setId(c.getId());
        setName(c.getName());
        setDescription(c.getDescription());
        setCreationDate(c.getCreationDate());
        setSuspend(c.isSuspend());
        setSuspendReason(c.getSuspendReason());
        setPosts(posts);
    }
}
