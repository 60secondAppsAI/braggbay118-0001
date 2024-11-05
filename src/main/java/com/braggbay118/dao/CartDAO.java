package com.braggbay118.dao;

import java.util.List;

import com.braggbay118.dao.GenericDAO;
import com.braggbay118.domain.Cart;





public interface CartDAO extends GenericDAO<Cart, Integer> {
  
	List<Cart> findAll();
	






}


