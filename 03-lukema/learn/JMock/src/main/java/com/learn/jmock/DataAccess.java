package com.learn.jmock;

import java.math.BigDecimal;

public interface DataAccess {
	BigDecimal getPriceBySku(String sku);
}
