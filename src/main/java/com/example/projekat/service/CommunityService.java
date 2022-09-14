package com.example.projekat.service;


import com.example.projekat.model.dto.CommunityDto;
import com.example.projekat.model.dto.NewCommunityDto;
import com.example.projekat.model.dto.UpdateCommunityDto;

import java.util.List;


public interface CommunityService {
    List<CommunityDto> findAll();

    CommunityDto findById(Long id);

    CommunityDto createCommunity(NewCommunityDto newCommunityDTO, String username);

    CommunityDto update(Long id, UpdateCommunityDto updateCommunity, String username);
}
