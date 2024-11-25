package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.request.RankingRequest;
import com.group2.restaurantorderingwebapp.dto.response.PageCustom;
import com.group2.restaurantorderingwebapp.dto.response.RankingResponse;
import com.group2.restaurantorderingwebapp.dto.response.UserResponse;
import com.group2.restaurantorderingwebapp.entity.Dish;
import com.group2.restaurantorderingwebapp.entity.Ranking;
import com.group2.restaurantorderingwebapp.entity.User;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;
import com.group2.restaurantorderingwebapp.repository.DishRepository;
import com.group2.restaurantorderingwebapp.repository.RankingRepository;
import com.group2.restaurantorderingwebapp.repository.UserRepository;
import com.group2.restaurantorderingwebapp.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final DishRepository dishRepository;



    @Override
    public RankingResponse addRanking(RankingRequest rankingRequest){
        Ranking ranking = modelMapper.map(rankingRequest, Ranking.class);
        User user = userRepository.findById(rankingRequest.getUserId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", rankingRequest.getUserId()));
        Dish dish = dishRepository.findById(rankingRequest.getDishID()).orElseThrow(() -> new ResourceNotFoundException("Dish", "id", rankingRequest.getDishID()));
        ranking.setDish(dish);
        ranking.setUser(user);
        rankingRepository.save(ranking);
        RankingResponse response= modelMapper.map(ranking, RankingResponse.class);
        response.setUser(modelMapper.map(user, UserResponse.class));

        return response;
    }


    @Override
    public RankingResponse updateRanking(Long rankingId, RankingRequest rankingRequest){
        Ranking ranking = rankingRepository.findById(rankingId).orElseThrow(() -> new ResourceNotFoundException("Ranking", "id", rankingId));
        ranking.setComment(rankingRequest.getComment());
        ranking.setRankingStars(rankingRequest.getRankingStars());
        rankingRepository.save(ranking);
        RankingResponse response= modelMapper.map(ranking, RankingResponse.class);
        response.setUser(modelMapper.map(ranking.getUser(), UserResponse.class));
        return response;
    }

    @Override
    public String deleteRanking(Long rankingId){
        Ranking ranking = rankingRepository.findById(rankingId).orElseThrow(() -> new ResourceNotFoundException("Ranking", "id", rankingId));
        rankingRepository.delete(ranking);
        return "Ranking deleted successfully";
    }

    @Override
    public PageCustom<RankingResponse> getAllRanking(Pageable pageable) {
        Page<Ranking> page = rankingRepository.findAll(pageable);
        PageCustom<RankingResponse> pageCustom = PageCustom.<RankingResponse>builder()
                .pageNo(page.getNumber()+1)
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .pageContent(page.getContent().stream().map(result -> modelMapper.map(result, RankingResponse.class)).toList())
                .build();
        return pageCustom;
    }

    @Override
    public RankingResponse getRankingById(Long rankingId){
        Ranking ranking = rankingRepository.findById(rankingId).orElseThrow(() -> new ResourceNotFoundException("Ranking", "id", rankingId));
        RankingResponse response= modelMapper.map(ranking, RankingResponse.class);
        response.setUser(modelMapper.map(ranking.getUser(), UserResponse.class));
        return response;
    }

    @Override
    public PageCustom<RankingResponse> getRankingByDishId(Long rankingId,Pageable pageable){
        Page<Ranking> ranking = rankingRepository.findAllByDishId(rankingId,pageable);
        PageCustom<RankingResponse> pageCustom = PageCustom.<RankingResponse>builder()
                .pageNo(ranking.getNumber()+1)
                .pageSize(ranking.getSize())
                .pageContent(ranking.getContent().stream().map(result -> modelMapper.map(result, RankingResponse.class)).toList())
                .build();
        return pageCustom;
    }

    @Override
    public PageCustom<RankingResponse> getRankingByUserId(Long rankingId,Pageable pageable){
        Page<Ranking> ranking = rankingRepository.findAllByUserId(rankingId,pageable);
        PageCustom<RankingResponse> pageCustom = PageCustom.<RankingResponse>builder()
                .pageNo(ranking.getNumber()+1)
                .pageSize(ranking.getSize())
                .pageContent(ranking.getContent().stream().map(result -> modelMapper.map(result, RankingResponse.class)).toList())
                .build();

        return pageCustom;
    }


    @Override
    public PageCustom<RankingResponse> getRankingByStar(int rankingStars,Pageable pageable) {
        Page<Ranking> ranking = rankingRepository.findAllByRankingStars(rankingStars,pageable);
        PageCustom<RankingResponse> pageCustom = PageCustom.<RankingResponse>builder()
                .pageNo(ranking.getNumber()+1)
                .pageSize(ranking.getSize())
                .pageContent(ranking.getContent().stream().map(result -> modelMapper.map(result, RankingResponse.class)).toList())
                .build();
        return pageCustom;
    }


}
