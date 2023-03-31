package com.Inventory.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Inventory.entity.Inventory;
import com.Inventory.helper.Helper;
import com.Inventory.response.CustomResponseHandler;
import com.Inventory.service.InventoryService;

@RestController
public class InventoryController {

	@Autowired
	InventoryService inventoryService;

	@GetMapping("/getAllInventory")
	public ResponseEntity<List<Inventory>> getInventory() {
		try {
			List<Inventory> inventoryDeatils = new ArrayList<>();
			inventoryDeatils = inventoryService.findAll();

			if (inventoryDeatils.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(inventoryDeatils, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/SA-IN")
	public ResponseEntity<Object> addQuantity(@RequestParam("partNumber") String partNumber,
			@RequestParam("serialNumber") String serialNumber, @RequestParam("quantity") String quantity) {

		List<String> response = Helper.validateRequest(partNumber, serialNumber, quantity);

		if (response.size() > 0)
			return CustomResponseHandler.generateResponse(response, HttpStatus.BAD_REQUEST, "");

		Inventory inventory = inventoryService.findByIdentity(partNumber, serialNumber);

		if (inventory == null)
			return CustomResponseHandler.generateResponse(
					Arrays.asList("No Inventory available for the given combination of partnumber and serialnumber"),
					HttpStatus.BAD_REQUEST, "");

		double available = inventory.getAvailableQty();
		double totalInventory = inventory.getInventoryQty();

		inventory.setInventoryQty(totalInventory + Double.valueOf(quantity));
		inventory.setAvailableQty(available + Double.valueOf(quantity));

		inventoryService.update(inventory);
		return CustomResponseHandler.generateResponse(Arrays.asList("Added the quantity"), HttpStatus.OK, inventory);
	}

	@PostMapping("/SA-OUT")
	public ResponseEntity<Object> deductQuantity(@RequestParam("partNumber") String partNumber,
			@RequestParam("serialNumber") String serialNumber, @RequestParam("quantity") String quantity) {

		List<String> response = Helper.validateRequest(partNumber, serialNumber, quantity);

		if (response.size() > 0)
			return CustomResponseHandler.generateResponse(response, HttpStatus.BAD_REQUEST, "");

		Inventory inventory = inventoryService.findByIdentity(partNumber, serialNumber);

		if (inventory == null)
			return CustomResponseHandler.generateResponse(
					Arrays.asList("No Inventory available for the given combination of partnumber and serialnumber"),
					HttpStatus.BAD_REQUEST, "");

		List<String> eventResponse = new ArrayList<>();
		double available = inventory.getAvailableQty();
		double totalInventory = inventory.getInventoryQty();
		if (available >= Double.valueOf(quantity)) {
			inventory.setInventoryQty(totalInventory - Double.valueOf(quantity));
			inventory.setAvailableQty(available - Double.valueOf(quantity));
			inventoryService.update(inventory);
			eventResponse.add("Removed the quantity");
			if (inventory.getInventoryQty() == 0) {
				inventoryService.deleteByID(partNumber, serialNumber);
				eventResponse.add("Deleted the inventory as InventoryQuantity became 0");
			}
		}

		else {
			return CustomResponseHandler.generateResponse(Arrays.asList(
					"Operation not performed as Available Quantity/Inventory Quantity is going to be negative with this operation"),
					HttpStatus.BAD_REQUEST, "");
		}

		return CustomResponseHandler.generateResponse(eventResponse, HttpStatus.OK, inventory);

	}

}
