package com.coverself.flipdeal.entity;

public class Product {

	private int inventory;
	private double rating;
	private double price;
	private boolean isNew;
	private String currency;
	private String product;
	private Origin origin;
	private Discount discount;
	private Category category;

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

}
