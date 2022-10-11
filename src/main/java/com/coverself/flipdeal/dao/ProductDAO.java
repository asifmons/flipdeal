package com.coverself.flipdeal.dao;

import java.util.HashMap;
import java.util.List;

import com.coverself.flipdeal.entity.Product;

public interface ProductDAO {

	public List<Product> getProducts();

	public HashMap<String, Double> getExchangeRates();

}
