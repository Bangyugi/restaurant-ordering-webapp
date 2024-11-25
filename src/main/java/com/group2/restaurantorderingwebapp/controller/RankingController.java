package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.RankingRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Ranking", description = "Ranking API")
public class RankingController {

    private final RankingService rankingService;

    @Operation(summary = "Get All Rankings", description = "Get All Rankings API")
    @GetMapping()
    public ResponseEntity<ApiResponse> getAllRankings(
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "rankingStars", required = false) String sortBy
    ){
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).descending() );
        ApiResponse apiResponse = ApiResponse.success(rankingService.getAllRanking(pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get Rankings By Dish Id", description = "Get Rankings By Dish Id API")
    @GetMapping("/dishes/{rankingId}")
    public ResponseEntity<ApiResponse> getRankingByDishId(@PathVariable("rankingId") Long rankingId,@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
                                                          @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
                                                          @RequestParam(value = "sortBy",defaultValue = "rankingStars", required = false) String sortBy
    ){
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).descending() );
        ApiResponse apiResponse = ApiResponse.success(rankingService.getRankingByDishId(rankingId,pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get Rankings By User Id", description = "Get Rankings By User Id API")
    @GetMapping("/users/{rankingId}")
    public ResponseEntity<ApiResponse> getRankingByUserId(@PathVariable("rankingId") Long rankingId,@RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
                                                          @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
                                                          @RequestParam(value = "sortBy",defaultValue = "rankingStars", required = false) String sortBy
    ){
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).descending() );
        ApiResponse apiResponse = ApiResponse.success(rankingService.getRankingByUserId(rankingId,pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get Rankings By Id", description = "Get Rankings By Id API")
    @GetMapping("/{rankingId}")
    public ResponseEntity<ApiResponse> getRankingById(@PathVariable("rankingId") Long rankingId){

        ApiResponse apiResponse = ApiResponse.success(rankingService.getRankingById(rankingId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Create Rankings", description = "Create Rankings API")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping()
    public ResponseEntity<ApiResponse> createRanking(@RequestBody RankingRequest rankingRequest){
        ApiResponse apiResponse = ApiResponse.success(rankingService.addRanking(rankingRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Update Rankings", description = "Update Rankings API")
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/{rankingId}")
    public ResponseEntity<ApiResponse> updateRanking(@PathVariable("rankingId") Long rankingId, @RequestBody RankingRequest rankingRequest){
        ApiResponse apiResponse = ApiResponse.success(rankingService.updateRanking(rankingId, rankingRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Delete Rankings", description = "Delete Rankings API")
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/{rankingId}")
    public ResponseEntity<ApiResponse> deleteRanking(@PathVariable("rankingId") Long rankingId){
        ApiResponse apiResponse = ApiResponse.success(rankingService.deleteRanking(rankingId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get Rankings By Star", description = "Get Rankings By Star API")
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/star/{rankingStars}")
    public ResponseEntity<ApiResponse> getRankingByStar(@PathVariable("rankingStars") int rankingStars, @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
                                                        @RequestParam(value = "pageSize",defaultValue = "10", required = false) int pageSize,
                                                        @RequestParam(value = "sortBy",defaultValue = "rankingStars", required = false) String sortBy
    ){
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).descending() );
        ApiResponse apiResponse = ApiResponse.success(rankingService.getRankingByStar(rankingStars,pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
