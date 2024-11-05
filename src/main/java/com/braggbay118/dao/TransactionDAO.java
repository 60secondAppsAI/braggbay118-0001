package com.braggbay118.dao;

import java.util.List;

import com.braggbay118.dao.GenericDAO;
import com.braggbay118.domain.Transaction;





public interface TransactionDAO extends GenericDAO<Transaction, Integer> {
  
	List<Transaction> findAll();
	






}


