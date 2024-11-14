package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.request.PositionRequest;
import com.group2.restaurantorderingwebapp.dto.response.OrderResponse;
import com.group2.restaurantorderingwebapp.dto.response.PositionResponse;
import com.group2.restaurantorderingwebapp.entity.Position;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;
import com.group2.restaurantorderingwebapp.repository.PositonRepository;
import com.group2.restaurantorderingwebapp.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {
  private final PositonRepository positionRepository;
    private final ModelMapper modelMapper;

    @Override
    public PositionResponse createPosition(PositionRequest positionRequest) {
        Position position = modelMapper.map(positionRequest, Position.class);
        return  modelMapper.map(positionRepository.save(position), PositionResponse.class);
    }


    @Override
    public PositionResponse getPosition(String id) {
        Position position = positionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Position", "id", id));
        PositionResponse positionResponse = modelMapper.map(position, PositionResponse.class);
        positionResponse.setOrders(position.getOrders().stream().map(result -> {
            OrderResponse orderResponse = modelMapper.map(result, OrderResponse.class);
            if(result.getUser() != null)
              orderResponse.setUserId(result.getUser().getId());
            return orderResponse;
        }).filter(order -> !order.isStatus()).collect(Collectors.toSet()));
        return positionResponse;
    }

    @Override
    public List<PositionResponse> getAllPositions() {
        return positionRepository.findAll().stream()
                .map(position -> {
                    PositionResponse response = modelMapper.map(position, PositionResponse.class);
                    response.setOrders(position.getOrders().stream()
                            .map(order -> {
                                OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
                                if (order.getUser() != null)
                                    orderResponse.setUserId(order.getUser().getId());
                                return orderResponse;
                            }).filter(order -> !order.isStatus())
                            .collect(Collectors.toSet()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Override
    public PositionResponse updatePosition(String id, PositionRequest positionRequest) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position", "id", id));

        position.setName(positionRequest.getName());
        Position updatedPosition = positionRepository.save(position);
        PositionResponse response = modelMapper.map(updatedPosition, PositionResponse.class);
        response.setOrders(updatedPosition.getOrders().stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toSet()));
        return response;
    }

    @Override
    public String deletePosition(String id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position", "id", id));

        positionRepository.delete(position);
        return "Deleted position successfully";

    }




}
