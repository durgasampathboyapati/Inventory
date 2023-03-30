package com.Inventory.service;

import java.util.List;

import com.Inventory.entity.Inventory;

public interface InventoryService {

	List<Inventory> findAll();

	Inventory findByIdentity(String partNumber, String serialNumber);

	void update(Inventory inventory);

	void deleteByID(String partNumber, String serialNumber);

}
