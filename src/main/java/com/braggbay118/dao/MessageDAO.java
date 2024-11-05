package com.braggbay118.dao;

import java.util.List;

import com.braggbay118.dao.GenericDAO;
import com.braggbay118.domain.Message;





public interface MessageDAO extends GenericDAO<Message, Integer> {
  
	List<Message> findAll();
	






}


