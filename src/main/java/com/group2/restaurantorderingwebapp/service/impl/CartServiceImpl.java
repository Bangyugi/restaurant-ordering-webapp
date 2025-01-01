package com.group2.restaurantorderingwebapp.service.impl;

import com.group2.restaurantorderingwebapp.dto.request.CartRequest;
import com.group2.restaurantorderingwebapp.dto.response.CartResponse;
import com.group2.restaurantorderingwebapp.entity.Cart;
import com.group2.restaurantorderingwebapp.entity.Order;
import com.group2.restaurantorderingwebapp.exception.ResourceNotFoundException;
import com.group2.restaurantorderingwebapp.repository.CartRepository;
import com.group2.restaurantorderingwebapp.repository.OrderRepository;
import com.group2.restaurantorderingwebapp.service.CartService;
import com.group2.restaurantorderingwebapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
   private final CartRepository cartRepository;
   private final ModelMapper modelMapper;
   private final OrderRepository orderRepository;

   @Override
   public CartResponse getById(Long id){
      Cart cart =  cartRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("cart","id",id));
      List<Order> orders = orderRepository.findAllByCartAndStatus(cart,false);
      CartResponse cartResponse= modelMapper.map(cart,CartResponse.class);
      cartResponse.setElementAmount(orders.size());
      return cartResponse;
   }

   @Override
   public CartResponse updateById(Long id, CartRequest cartRequest){
      Cart cart = cartRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("cart","id",id));
      modelMapper.map(cartRequest,cart);
      cartRepository.save(cart);
      return modelMapper.map(cart,CartResponse.class);
   }

   @Override
   public CartResponse getCartByUser(Long userId){
      return modelMapper.map(cartRepository.findByUserId(userId).orElseThrow(()-> new ResourceNotFoundException("cart","user id",userId)),CartResponse.class);
   }

}
