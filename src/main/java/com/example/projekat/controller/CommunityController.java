package com.example.projekat.controller;

import com.example.projekat.model.dto.CommunityDto;
import com.example.projekat.model.dto.NewCommunityDto;
import com.example.projekat.model.dto.UpdateCommunityDto;
import com.example.projekat.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/communities")
public class CommunityController {

    @Autowired
    CommunityService communityService;

    @PostMapping
    public ResponseEntity<CommunityDto> createCommunity(@RequestBody @Validated NewCommunityDto newCommunity) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CommunityDto createdCommunity = communityService.createCommunity(newCommunity, userDetails.getUsername());

        if (createdCommunity == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdCommunity, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CommunityDto>> getAll() {
        List<CommunityDto> all = communityService.findAll();
        return new ResponseEntity<>(all, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<CommunityDto> getById(@PathVariable("id") Long id) {
        CommunityDto communityDTO = communityService.findById(id);
        if (communityDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(communityDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @PatchMapping("/{id}")
    public ResponseEntity<CommunityDto> update(@PathVariable("id") Long id, @RequestBody @Validated UpdateCommunityDto updateCommunity) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        CommunityDto saved = communityService.update(id, updateCommunity, userDetails.getUsername());
        if (saved == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}
