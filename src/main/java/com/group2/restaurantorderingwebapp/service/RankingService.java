package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.RankingRequest;
import com.group2.restaurantorderingwebapp.dto.response.RankingResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RankingService {
    RankingResponse addRanking (RankingRequest rankingRequest);

    RankingResponse updateRanking(Long id, RankingRequest rankingRequest);

    String deleteRanking(Long id);

    List<RankingResponse> getAllRanking(Pageable pageable);


    RankingResponse getRankingById(Long id);

    List<RankingResponse> getRankingByDishId(Long id,Pageable pageable);

    List<RankingResponse> getRankingByUserId(Long id,Pageable pageable);


    List<RankingResponse> getRankingByStar(int star,Pageable pageable);
}
