package com.coverself.flipdeal.service;

import java.util.List;

import com.coverself.flipdeal.entity.Product;

public interface ProductServiceInterface {

	public List<Product> getProductsWithDiscount(String discountType);

}
