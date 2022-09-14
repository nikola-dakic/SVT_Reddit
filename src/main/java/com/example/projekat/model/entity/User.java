package com.example.projekat.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@NoArgsConstructor
@AllArgsConstructor()
@Getter
@Setter
@Inheritance(strategy = SINGLE_TABLE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private String email;
    private String description;
    @Column(name = "registration_date")
    private LocalDate registrationDate;
    @Column(name = "display_name")
    private String displayName;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Moderator> moderatorRoles = new HashSet<>();

    public void addModeratorRole(Moderator moderator) {
        moderatorRoles.add(moderator);
        moderator.setUser(this);
    }

    public void removeModeratorRole(Moderator moderator) {
        moderatorRoles.remove(moderator);
        moderator.setUser(null);
    }

    public String getRole() {
        if (this instanceof Administrator) {
            return "ROLE_ADMIN";
        }

        if (!getModeratorRoles().isEmpty()) {
            return "ROLE_MODERATOR";

        }
        return "ROLE_USER";
    }

}
