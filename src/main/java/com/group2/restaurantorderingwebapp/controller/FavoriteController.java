package com.group2.restaurantorderingwebapp.controller;

import com.group2.restaurantorderingwebapp.dto.request.FavoriteRequest;
import com.group2.restaurantorderingwebapp.dto.response.ApiResponse;
import com.group2.restaurantorderingwebapp.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;

    @PostMapping()
    public ResponseEntity<ApiResponse> addFavorite(@RequestBody FavoriteRequest favoriteRequest){
        ApiResponse apiResponse = ApiResponse.success(favoriteService.createFavorite(favoriteRequest));
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<ApiResponse> deleteFavorite(@PathVariable Long favoriteId){
        ApiResponse apiResponse = ApiResponse.success(favoriteService.deleteFavorite(favoriteId));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<ApiResponse> getFavoritesByUser(
            @PathVariable Long userId,
            @RequestParam (value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam (value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam (value = "sortBy", defaultValue = "favoriteId", required = false) String sortBy
    ){
        Pageable pageable = PageRequest.of(pageNo -1,pageSize, Sort.by(sortBy).ascending());
        ApiResponse apiResponse = ApiResponse.success(favoriteService.getFavoriteByUser(userId,pageable));
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
