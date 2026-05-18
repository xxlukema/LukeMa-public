package com.broadsoft.cpbx.e911.domain;


public enum OrderStatus {
	ACTIVE(5), PENDING(6), CANCELLED(7), COMPLETE(8), ERROR(9), PENDING_INVESTIGATION(10);

	private int vzId;

	OrderStatus(int vzId) {
		this.vzId = vzId;
	}

	public int getVzId() {
		return vzId;
	}

	/**
	 * This is a helper that will look for the status that has the integer
	 * value of the param orderStatus. The parameter string order status
	 * needs to be a valid integer.
	 * 
	 * @param orderStatus
	 * @return
	 */
	public static OrderStatus getOrderStatusByVzId(int vzStatus) {
		for (OrderStatus stat : values()) {
			if (stat.getVzId() == vzStatus) {
				return stat;
			}
		}
		return null;
	}
}