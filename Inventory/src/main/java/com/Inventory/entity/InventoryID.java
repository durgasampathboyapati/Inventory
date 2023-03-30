package com.Inventory.entity;

import java.io.Serializable;
import java.util.Objects;

public class InventoryID implements Serializable {

	private String partNumber;
	private String serialNumber;

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	public int hashCode() {
		return Objects.hash(partNumber, serialNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryID other = (InventoryID) obj;
		return Objects.equals(partNumber, other.partNumber) && Objects.equals(serialNumber, other.serialNumber);
	}

}
