package com.example.projekat.repository;

import com.example.projekat.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByUsername(String username);

    @Modifying
    @Query("update User u set u.password =:newpassword where u.username =:username")
    void setNewPasswordByUsername(@Param("newpassword") String password, @Param("username") String username);

}
