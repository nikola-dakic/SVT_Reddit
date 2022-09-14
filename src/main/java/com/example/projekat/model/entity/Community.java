package com.example.projekat.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "community")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "is_suspend")
    private boolean isSuspend;
    @Column(name = "suspend_reason")
    private String suspendReason;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Moderator> moderators = new HashSet<>();


    public void addModerator(Moderator moderator) {
        moderators.add(moderator);
        moderator.setCommunity(this);
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public void removeModerator(Moderator moderator) {
        moderators.remove(moderator);
        moderator.setCommunity(null);
    }

    public void removeAllModerators() {
        for (Moderator m : moderators) {
            m.setCommunity(null);
        }
        moderators.clear();
    }

}
