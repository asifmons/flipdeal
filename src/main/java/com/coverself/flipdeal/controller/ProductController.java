package com.coverself.flipdeal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coverself.flipdeal.entity.Product;
import com.coverself.flipdeal.service.ProductService;

@Component
public class ProductController {

	@Autowired
	ProductService productService;

	public List<Product> getProductsWithDiscount(String discountType) {
		return productService.getProductsWithDiscount(discountType);
	}

}
