package com.braggbay118.dao;

import java.util.List;

import com.braggbay118.dao.GenericDAO;
import com.braggbay118.domain.Inventory;





public interface InventoryDAO extends GenericDAO<Inventory, Integer> {
  
	List<Inventory> findAll();
	






}


