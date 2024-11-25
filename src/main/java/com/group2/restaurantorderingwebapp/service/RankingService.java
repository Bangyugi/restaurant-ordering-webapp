package com.group2.restaurantorderingwebapp.service;

import com.group2.restaurantorderingwebapp.dto.request.RankingRequest;
import com.group2.restaurantorderingwebapp.dto.response.PageCustom;
import com.group2.restaurantorderingwebapp.dto.response.RankingResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


public interface RankingService {
    RankingResponse addRanking (RankingRequest rankingRequest);

    RankingResponse updateRanking(Long id, RankingRequest rankingRequest);

    String deleteRanking(Long id);

    PageCustom<RankingResponse> getAllRanking(Pageable pageable);


    RankingResponse getRankingById(Long id);

    PageCustom<RankingResponse> getRankingByDishId(Long id,Pageable pageable);

    PageCustom<RankingResponse> getRankingByUserId(Long id,Pageable pageable);


    PageCustom<RankingResponse> getRankingByStar(int star,Pageable pageable);
}
