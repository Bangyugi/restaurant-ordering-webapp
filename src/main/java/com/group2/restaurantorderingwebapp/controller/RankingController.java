package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.RankingRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rankings")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllRankings(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "star", required = false) String sortBy
    ){
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).descending() );
        ApiResponse apiResponse = ApiResponse.success(rankingService.getAllRanking(pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/dishes/{id}")
    public ResponseEntity<ApiResponse> getRankingByDishId(@PathVariable("id") Long id,@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
                                                          @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
                                                          @RequestParam(value = "sortBy",defaultValue = "star", required = false) String sortBy
    ){
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).descending() );
        ApiResponse apiResponse = ApiResponse.success(rankingService.getRankingByDishId(id,pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse> getRankingByUserId(@PathVariable("id") Long id,@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
                                                          @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
                                                          @RequestParam(value = "sortBy",defaultValue = "star", required = false) String sortBy
    ){
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).descending() );
        ApiResponse apiResponse = ApiResponse.success(rankingService.getRankingByUserId(id,pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getRankingById(@PathVariable("id") Long id){

        ApiResponse apiResponse = ApiResponse.success(rankingService.getRankingById(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createRanking(@RequestBody RankingRequest rankingRequest){
        ApiResponse apiResponse = ApiResponse.success(rankingService.addRanking(rankingRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateRanking(@PathVariable("id") Long id, @RequestBody RankingRequest rankingRequest){
        ApiResponse apiResponse = ApiResponse.success(rankingService.updateRanking(id, rankingRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRanking(@PathVariable("id") Long id){
        ApiResponse apiResponse = ApiResponse.success(rankingService.deleteRanking(id));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("/star/{star}")
    public ResponseEntity<ApiResponse> getRankingByStar(@PathVariable("star") int star, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
                                                        @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
                                                        @RequestParam(value = "sortBy",defaultValue = "star", required = false) String sortBy
    ){
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).descending() );
        ApiResponse apiResponse = ApiResponse.success(rankingService.getRankingByStar(star,pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
