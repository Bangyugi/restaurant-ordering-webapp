package com.group2.restaurantorderingwebapp.repository;

import com.group2.restaurantorderingwebapp.entity.Dish;
import com.group2.restaurantorderingwebapp.entity.Ranking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RankingRepository extends JpaRepository<Ranking,Long> {

    @Query("from Ranking r where r.user.id=:id")
    Page<Ranking> findAllByUserId(Long id,Pageable pageable);


    @Query("from Ranking r where r.dish.id=:id")
    Page<Ranking> findAllByDishId(Long id, Pageable pageable);

    Page<Ranking> findAllByStar(int star,Pageable pageable);
}
