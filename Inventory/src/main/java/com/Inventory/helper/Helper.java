package com.Inventory.helper;

import java.util.ArrayList;
import java.util.List;

public class Helper {

	public static List<String> validateRequest(String partNumber, String serialNumber, String quantity) {

		List<String> response = new ArrayList<>();
		if (partNumber.isBlank())
			response.add("PartNumber value is not provided");
		if (serialNumber.isBlank())
			response.add("SerialNumber value is not provided");
		if (!quantity.matches("-?\\d+"))
			response.add("Quantity is not numberic");
		else if (Integer.valueOf(quantity) < 0)
			response.add("Quantity value is Negative");

		return response;

	}
}
