package com.coverself.flipdeal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coverself.flipdeal.entity.Product;
import com.coverself.flipdeal.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	@GetMapping("/productsinr")
	public List<Product> getProductsWithDiscount(String discountType) {
		return productService.getProductsWithDiscount(discountType);
	}

}
