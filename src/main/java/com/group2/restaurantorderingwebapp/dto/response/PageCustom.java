package com.group2.restaurantorderingwebapp.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageCustom<T> {
    private int pageNo;
    private int pageSize;
    private int totalPages;
    List<T> pageContent;
}
