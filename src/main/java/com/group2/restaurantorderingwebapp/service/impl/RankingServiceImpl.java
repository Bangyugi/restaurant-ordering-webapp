package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.request.RankingRequest;
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
    public List<RankingResponse> getAllRanking(Pageable pageable){
        List<RankingResponse> ranking = rankingRepository.findAll(pageable).stream().map(result ->
        {
            RankingResponse response = modelMapper.map(result, RankingResponse.class);
            response.setUser(modelMapper.map(result.getUser(), UserResponse.class));
            return response;
        }).toList();
        return ranking;
    }

    @Override
    public RankingResponse getRankingById(Long rankingId){
        Ranking ranking = rankingRepository.findById(rankingId).orElseThrow(() -> new ResourceNotFoundException("Ranking", "id", rankingId));
        RankingResponse response= modelMapper.map(ranking, RankingResponse.class);
        response.setUser(modelMapper.map(ranking.getUser(), UserResponse.class));
        return response;
    }

    @Override
    public List<RankingResponse> getRankingByDishId(Long rankingId,Pageable pageable){


        List<RankingResponse> ranking = rankingRepository.findAllByDishId(rankingId,pageable).getContent().stream().map(result ->
        {
            RankingResponse response = modelMapper.map(result, RankingResponse.class);
            response.setUser((modelMapper.map(result.getUser(), UserResponse.class)));
            return response;
        }).toList();
        return ranking;
    }

    @Override
    public List<RankingResponse> getRankingByUserId(Long rankingId,Pageable pageable){
        List<RankingResponse> ranking = rankingRepository.findAllByUserId(rankingId,pageable).getContent().stream().map(result ->
        {
            RankingResponse response = modelMapper.map(result, RankingResponse.class);
            response.setUser((modelMapper.map(result.getUser(), UserResponse.class)));
            return response;
        }).toList();
        return ranking;
    }


    @Override
    public List<RankingResponse> getRankingByStar(int rankingStars,Pageable pageable) {
        List<RankingResponse> ranking = rankingRepository.findAllByRankingStars(rankingStars,pageable).getContent().stream().map(result ->
        {
            RankingResponse response = modelMapper.map(result, RankingResponse.class);
            response.setUser((modelMapper.map(result.getUser(), UserResponse.class)));
            return response;
        }).toList();
        return ranking;
    }


}
