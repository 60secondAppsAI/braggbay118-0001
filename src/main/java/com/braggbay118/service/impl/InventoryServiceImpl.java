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
import com.braggbay118.dao.InventoryDAO;
import com.braggbay118.domain.Inventory;
import com.braggbay118.dto.InventoryDTO;
import com.braggbay118.dto.InventorySearchDTO;
import com.braggbay118.dto.InventoryPageDTO;
import com.braggbay118.dto.InventoryConvertCriteriaDTO;
import com.braggbay118.dto.common.RequestDTO;
import com.braggbay118.dto.common.ResultDTO;
import com.braggbay118.service.InventoryService;
import com.braggbay118.util.ControllerUtils;





@Service
public class InventoryServiceImpl extends GenericServiceImpl<Inventory, Integer> implements InventoryService {

    private final static Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);

	@Autowired
	InventoryDAO inventoryDao;

	


	@Override
	public GenericDAO<Inventory, Integer> getDAO() {
		return (GenericDAO<Inventory, Integer>) inventoryDao;
	}
	
	public List<Inventory> findAll () {
		List<Inventory> inventorys = inventoryDao.findAll();
		
		return inventorys;	
		
	}

	public ResultDTO addInventory(InventoryDTO inventoryDTO, RequestDTO requestDTO) {

		Inventory inventory = new Inventory();

		inventory.setInventoryId(inventoryDTO.getInventoryId());


		inventory.setQuantity(inventoryDTO.getQuantity());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		inventory = inventoryDao.save(inventory);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Inventory> getAllInventorys(Pageable pageable) {
		return inventoryDao.findAll(pageable);
	}

	public Page<Inventory> getAllInventorys(Specification<Inventory> spec, Pageable pageable) {
		return inventoryDao.findAll(spec, pageable);
	}

	public ResponseEntity<InventoryPageDTO> getInventorys(InventorySearchDTO inventorySearchDTO) {
	
			Integer inventoryId = inventorySearchDTO.getInventoryId(); 
 			Integer quantity = inventorySearchDTO.getQuantity(); 
 			String sortBy = inventorySearchDTO.getSortBy();
			String sortOrder = inventorySearchDTO.getSortOrder();
			String searchQuery = inventorySearchDTO.getSearchQuery();
			Integer page = inventorySearchDTO.getPage();
			Integer size = inventorySearchDTO.getSize();

	        Specification<Inventory> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, inventoryId, "inventoryId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, quantity, "quantity"); 
			

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

		Page<Inventory> inventorys = this.getAllInventorys(spec, pageable);
		
		//System.out.println(String.valueOf(inventorys.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(inventorys.getTotalPages()));
		
		List<Inventory> inventorysList = inventorys.getContent();
		
		InventoryConvertCriteriaDTO convertCriteria = new InventoryConvertCriteriaDTO();
		List<InventoryDTO> inventoryDTOs = this.convertInventorysToInventoryDTOs(inventorysList,convertCriteria);
		
		InventoryPageDTO inventoryPageDTO = new InventoryPageDTO();
		inventoryPageDTO.setInventorys(inventoryDTOs);
		inventoryPageDTO.setTotalElements(inventorys.getTotalElements());
		return ResponseEntity.ok(inventoryPageDTO);
	}

	public List<InventoryDTO> convertInventorysToInventoryDTOs(List<Inventory> inventorys, InventoryConvertCriteriaDTO convertCriteria) {
		
		List<InventoryDTO> inventoryDTOs = new ArrayList<InventoryDTO>();
		
		for (Inventory inventory : inventorys) {
			inventoryDTOs.add(convertInventoryToInventoryDTO(inventory,convertCriteria));
		}
		
		return inventoryDTOs;

	}
	
	public InventoryDTO convertInventoryToInventoryDTO(Inventory inventory, InventoryConvertCriteriaDTO convertCriteria) {
		
		InventoryDTO inventoryDTO = new InventoryDTO();
		
		inventoryDTO.setInventoryId(inventory.getInventoryId());

	
		inventoryDTO.setQuantity(inventory.getQuantity());

	

		
		return inventoryDTO;
	}

	public ResultDTO updateInventory(InventoryDTO inventoryDTO, RequestDTO requestDTO) {
		
		Inventory inventory = inventoryDao.getById(inventoryDTO.getInventoryId());

		inventory.setInventoryId(ControllerUtils.setValue(inventory.getInventoryId(), inventoryDTO.getInventoryId()));

		inventory.setQuantity(ControllerUtils.setValue(inventory.getQuantity(), inventoryDTO.getQuantity()));



        inventory = inventoryDao.save(inventory);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public InventoryDTO getInventoryDTOById(Integer inventoryId) {
	
		Inventory inventory = inventoryDao.getById(inventoryId);
			
		
		InventoryConvertCriteriaDTO convertCriteria = new InventoryConvertCriteriaDTO();
		return(this.convertInventoryToInventoryDTO(inventory,convertCriteria));
	}







}
