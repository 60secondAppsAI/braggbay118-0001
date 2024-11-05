package com.braggbay118.dao;

import java.util.List;

import com.braggbay118.dao.GenericDAO;
import com.braggbay118.domain.Auction;





public interface AuctionDAO extends GenericDAO<Auction, Integer> {
  
	List<Auction> findAll();
	






}


