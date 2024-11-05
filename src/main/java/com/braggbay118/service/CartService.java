package com.braggbay118.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import com.braggbay118.domain.Cart;
import com.braggbay118.dto.CartDTO;
import com.braggbay118.dto.CartSearchDTO;
import com.braggbay118.dto.CartPageDTO;
import com.braggbay118.dto.CartConvertCriteriaDTO;
import com.braggbay118.service.GenericService;
import com.braggbay118.dto.common.RequestDTO;
import com.braggbay118.dto.common.ResultDTO;
import java.util.List;
import java.util.Optional;





public interface CartService extends GenericService<Cart, Integer> {

	List<Cart> findAll();

	ResultDTO addCart(CartDTO cartDTO, RequestDTO requestDTO);

	ResultDTO updateCart(CartDTO cartDTO, RequestDTO requestDTO);

    Page<Cart> getAllCarts(Pageable pageable);

    Page<Cart> getAllCarts(Specification<Cart> spec, Pageable pageable);

	ResponseEntity<CartPageDTO> getCarts(CartSearchDTO cartSearchDTO);
	
	List<CartDTO> convertCartsToCartDTOs(List<Cart> carts, CartConvertCriteriaDTO convertCriteria);

	CartDTO getCartDTOById(Integer cartId);







}





