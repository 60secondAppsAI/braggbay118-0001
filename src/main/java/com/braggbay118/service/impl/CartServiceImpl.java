package com.braggbay118.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.braggbay118.dao.GenericDAO;
import com.braggbay118.service.GenericService;
import com.braggbay118.service.impl.GenericServiceImpl;
import com.braggbay118.dao.CartDAO;
import com.braggbay118.domain.Cart;
import com.braggbay118.dto.CartDTO;
import com.braggbay118.dto.CartSearchDTO;
import com.braggbay118.dto.CartPageDTO;
import com.braggbay118.dto.CartConvertCriteriaDTO;
import com.braggbay118.dto.common.RequestDTO;
import com.braggbay118.dto.common.ResultDTO;
import com.braggbay118.service.CartService;
import com.braggbay118.util.ControllerUtils;





@Service
public class CartServiceImpl extends GenericServiceImpl<Cart, Integer> implements CartService {

    private final static Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

	@Autowired
	CartDAO cartDao;

	


	@Override
	public GenericDAO<Cart, Integer> getDAO() {
		return (GenericDAO<Cart, Integer>) cartDao;
	}
	
	public List<Cart> findAll () {
		List<Cart> carts = cartDao.findAll();
		
		return carts;	
		
	}

	public ResultDTO addCart(CartDTO cartDTO, RequestDTO requestDTO) {

		Cart cart = new Cart();

		cart.setCartId(cartDTO.getCartId());


		cart.setCreatedDate(cartDTO.getCreatedDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		cart = cartDao.save(cart);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Cart> getAllCarts(Pageable pageable) {
		return cartDao.findAll(pageable);
	}

	public Page<Cart> getAllCarts(Specification<Cart> spec, Pageable pageable) {
		return cartDao.findAll(spec, pageable);
	}

	public ResponseEntity<CartPageDTO> getCarts(CartSearchDTO cartSearchDTO) {
	
			Integer cartId = cartSearchDTO.getCartId(); 
   			String sortBy = cartSearchDTO.getSortBy();
			String sortOrder = cartSearchDTO.getSortOrder();
			String searchQuery = cartSearchDTO.getSearchQuery();
			Integer page = cartSearchDTO.getPage();
			Integer size = cartSearchDTO.getSize();

	        Specification<Cart> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, cartId, "cartId"); 
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Cart> carts = this.getAllCarts(spec, pageable);
		
		//System.out.println(String.valueOf(carts.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(carts.getTotalPages()));
		
		List<Cart> cartsList = carts.getContent();
		
		CartConvertCriteriaDTO convertCriteria = new CartConvertCriteriaDTO();
		List<CartDTO> cartDTOs = this.convertCartsToCartDTOs(cartsList,convertCriteria);
		
		CartPageDTO cartPageDTO = new CartPageDTO();
		cartPageDTO.setCarts(cartDTOs);
		cartPageDTO.setTotalElements(carts.getTotalElements());
		return ResponseEntity.ok(cartPageDTO);
	}

	public List<CartDTO> convertCartsToCartDTOs(List<Cart> carts, CartConvertCriteriaDTO convertCriteria) {
		
		List<CartDTO> cartDTOs = new ArrayList<CartDTO>();
		
		for (Cart cart : carts) {
			cartDTOs.add(convertCartToCartDTO(cart,convertCriteria));
		}
		
		return cartDTOs;

	}
	
	public CartDTO convertCartToCartDTO(Cart cart, CartConvertCriteriaDTO convertCriteria) {
		
		CartDTO cartDTO = new CartDTO();
		
		cartDTO.setCartId(cart.getCartId());

	
		cartDTO.setCreatedDate(cart.getCreatedDate());

	

		
		return cartDTO;
	}

	public ResultDTO updateCart(CartDTO cartDTO, RequestDTO requestDTO) {
		
		Cart cart = cartDao.getById(cartDTO.getCartId());

		cart.setCartId(ControllerUtils.setValue(cart.getCartId(), cartDTO.getCartId()));

		cart.setCreatedDate(ControllerUtils.setValue(cart.getCreatedDate(), cartDTO.getCreatedDate()));



        cart = cartDao.save(cart);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public CartDTO getCartDTOById(Integer cartId) {
	
		Cart cart = cartDao.getById(cartId);
			
		
		CartConvertCriteriaDTO convertCriteria = new CartConvertCriteriaDTO();
		return(this.convertCartToCartDTO(cart,convertCriteria));
	}







}