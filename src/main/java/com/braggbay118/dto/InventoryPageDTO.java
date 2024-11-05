package com.braggbay118.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InventoryPageDTO {

	private Integer page = 0;
	private Long totalElements = 0L;

	private List<InventoryDTO> inventorys;
}





