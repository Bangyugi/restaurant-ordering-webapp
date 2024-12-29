package com.group2.restaurantorderingwebapp.repository;

import com.group2.restaurantorderingwebapp.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PositonRepository extends JpaRepository<Position, Long> {

    @Query(nativeQuery = true, value = """
        SELECT
            *
        FROM
            position p
        WHERE
            (p.status = 0)
            OR
            ((p.status = 1 )
         AND p.orderTime >= DATE_ADD(:order_time, INTERVAL 5 HOUR))
        """)
    List<Position> getAvailablePositions(@Param("order_time") LocalDateTime orderTime);

    List<Position> findByPositionIdIn(List<Long> id);
}
