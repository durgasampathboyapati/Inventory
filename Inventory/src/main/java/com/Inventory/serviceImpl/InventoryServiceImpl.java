package com.Inventory.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Inventory.entity.Inventory;
import com.Inventory.repository.InventoryRepository;
import com.Inventory.service.InventoryService;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

	@Autowired
	InventoryRepository inventoryRepo;

	@Override
	public List<Inventory> findAll() {
		// TODO Auto-generated method stub
		return inventoryRepo.findAll();
	}

	@Override
	public Inventory findByIdentity(String partNumber, String serialNumber) {
		// TODO Auto-generated method stub
		Inventory inventory = inventoryRepo.findByPartNumberAndSerialNumber(partNumber, serialNumber);
		return inventory;
	}

	@Override
	public void update(Inventory inventory) {
		// TODO Auto-generated method stub
		inventoryRepo.save(inventory);

	}

	@Override
	public void deleteByID(String partNumber, String serialNumber) {
		// TODO Auto-generated method stub
		inventoryRepo.deleteByPartNumberAndSerialNumber(partNumber, serialNumber);
	}

}
