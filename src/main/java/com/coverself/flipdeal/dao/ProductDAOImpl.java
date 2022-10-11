package com.coverself.flipdeal.dao;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.coverself.flipdeal.common.Common;
import com.coverself.flipdeal.entity.ExchangeRates;
import com.coverself.flipdeal.entity.Product;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Override
	public List<Product> getProducts() {
		URI uri = null;
		try {
			uri = new URI(Common.PRODUCTS_URI);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Product[]> responseEntity = restTemplate.getForEntity(uri, Product[].class);
		List<Product> products = Arrays.asList(responseEntity.getBody());
		return products;
	}

	@Override
	public HashMap<String, Double> getExchangeRates() {
		URI uri = null;
		try {
			uri = new URI(Common.EXCHANGE_RATES_URI);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ExchangeRates> responseEntityForExchangeRates = restTemplate.getForEntity(uri,
				ExchangeRates.class);
		HashMap<String, Double> rates = responseEntityForExchangeRates.getBody().getRates();
		return rates;
	}

}
