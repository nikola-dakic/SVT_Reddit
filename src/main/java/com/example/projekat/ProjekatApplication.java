package com.example.projekat;

import com.example.projekat.repository.CommunityRepository;
import com.example.projekat.repository.PostRepository;
import com.example.projekat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjekatApplication {


    public static void main(String[] args) {
        SpringApplication.run(ProjekatApplication.class, args);
    }
}
