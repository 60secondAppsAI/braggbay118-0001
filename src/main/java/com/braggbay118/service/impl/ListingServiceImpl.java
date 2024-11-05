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
import com.braggbay118.dao.ListingDAO;
import com.braggbay118.domain.Listing;
import com.braggbay118.dto.ListingDTO;
import com.braggbay118.dto.ListingSearchDTO;
import com.braggbay118.dto.ListingPageDTO;
import com.braggbay118.dto.ListingConvertCriteriaDTO;
import com.braggbay118.dto.common.RequestDTO;
import com.braggbay118.dto.common.ResultDTO;
import com.braggbay118.service.ListingService;
import com.braggbay118.util.ControllerUtils;





@Service
public class ListingServiceImpl extends GenericServiceImpl<Listing, Integer> implements ListingService {

    private final static Logger logger = LoggerFactory.getLogger(ListingServiceImpl.class);

	@Autowired
	ListingDAO listingDao;

	


	@Override
	public GenericDAO<Listing, Integer> getDAO() {
		return (GenericDAO<Listing, Integer>) listingDao;
	}
	
	public List<Listing> findAll () {
		List<Listing> listings = listingDao.findAll();
		
		return listings;	
		
	}

	public ResultDTO addListing(ListingDTO listingDTO, RequestDTO requestDTO) {

		Listing listing = new Listing();

		listing.setListingId(listingDTO.getListingId());


		listing.setStartDate(listingDTO.getStartDate());


		listing.setEndDate(listingDTO.getEndDate());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		listing = listingDao.save(listing);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Listing> getAllListings(Pageable pageable) {
		return listingDao.findAll(pageable);
	}

	public Page<Listing> getAllListings(Specification<Listing> spec, Pageable pageable) {
		return listingDao.findAll(spec, pageable);
	}

	public ResponseEntity<ListingPageDTO> getListings(ListingSearchDTO listingSearchDTO) {
	
			Integer listingId = listingSearchDTO.getListingId(); 
     			String sortBy = listingSearchDTO.getSortBy();
			String sortOrder = listingSearchDTO.getSortOrder();
			String searchQuery = listingSearchDTO.getSearchQuery();
			Integer page = listingSearchDTO.getPage();
			Integer size = listingSearchDTO.getSize();

	        Specification<Listing> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, listingId, "listingId"); 
			
 			
 			

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

		Page<Listing> listings = this.getAllListings(spec, pageable);
		
		//System.out.println(String.valueOf(listings.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(listings.getTotalPages()));
		
		List<Listing> listingsList = listings.getContent();
		
		ListingConvertCriteriaDTO convertCriteria = new ListingConvertCriteriaDTO();
		List<ListingDTO> listingDTOs = this.convertListingsToListingDTOs(listingsList,convertCriteria);
		
		ListingPageDTO listingPageDTO = new ListingPageDTO();
		listingPageDTO.setListings(listingDTOs);
		listingPageDTO.setTotalElements(listings.getTotalElements());
		return ResponseEntity.ok(listingPageDTO);
	}

	public List<ListingDTO> convertListingsToListingDTOs(List<Listing> listings, ListingConvertCriteriaDTO convertCriteria) {
		
		List<ListingDTO> listingDTOs = new ArrayList<ListingDTO>();
		
		for (Listing listing : listings) {
			listingDTOs.add(convertListingToListingDTO(listing,convertCriteria));
		}
		
		return listingDTOs;

	}
	
	public ListingDTO convertListingToListingDTO(Listing listing, ListingConvertCriteriaDTO convertCriteria) {
		
		ListingDTO listingDTO = new ListingDTO();
		
		listingDTO.setListingId(listing.getListingId());

	
		listingDTO.setStartDate(listing.getStartDate());

	
		listingDTO.setEndDate(listing.getEndDate());

	

		
		return listingDTO;
	}

	public ResultDTO updateListing(ListingDTO listingDTO, RequestDTO requestDTO) {
		
		Listing listing = listingDao.getById(listingDTO.getListingId());

		listing.setListingId(ControllerUtils.setValue(listing.getListingId(), listingDTO.getListingId()));

		listing.setStartDate(ControllerUtils.setValue(listing.getStartDate(), listingDTO.getStartDate()));

		listing.setEndDate(ControllerUtils.setValue(listing.getEndDate(), listingDTO.getEndDate()));



        listing = listingDao.save(listing);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ListingDTO getListingDTOById(Integer listingId) {
	
		Listing listing = listingDao.getById(listingId);
			
		
		ListingConvertCriteriaDTO convertCriteria = new ListingConvertCriteriaDTO();
		return(this.convertListingToListingDTO(listing,convertCriteria));
	}







}
