package com.example.projekat.model.entity;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Administrator extends User {

    public Administrator(Long id, String username, String password, String email, String description, LocalDate registrationDate, String displayName, Set<Moderator> moderatorRoles) {
        super(id, username, password, email, description, registrationDate, displayName, moderatorRoles);
    }
}
