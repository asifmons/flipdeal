package com.coverself.flipdeal.entity;

import lombok.Data;

@Data
public class Discount {

	private double amount;
	private String discountTag;

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDiscountTag() {
		return discountTag;
	}

	public void setDiscountTag(String discountTag) {
		this.discountTag = discountTag;
	}

}
