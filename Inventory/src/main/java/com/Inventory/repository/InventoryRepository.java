package com.Inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Inventory.entity.Inventory;
import com.Inventory.entity.InventoryID;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, InventoryID> {

	public Inventory findByPartNumberAndSerialNumber(String partNumber, String serialNumber);
	public Inventory save(Inventory inventory);
	public List<Inventory> findAll();
	public void deleteByPartNumberAndSerialNumber(String partNumber, String serialNumber);

}
