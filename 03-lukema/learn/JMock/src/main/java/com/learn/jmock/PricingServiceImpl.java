package com.learn.jmock;

import java.math.BigDecimal;

public class PricingServiceImpl implements PricingService {

	private DataAccess dataAccess;

	public void setDataAccess(DataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}

	public BigDecimal getPrice(String sku) throws SkuNotFoundException {
		final BigDecimal price = this.dataAccess.getPriceBySku(sku);
		if (price == null) {
			throw new SkuNotFoundException();
		}
		return price;
	}
}
