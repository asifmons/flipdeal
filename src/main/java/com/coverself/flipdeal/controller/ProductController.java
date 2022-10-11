package com.coverself.flipdeal.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.coverself.flipdeal.common.Common;
import com.coverself.flipdeal.entity.Discount;
import com.coverself.flipdeal.entity.ExchangeRates;
import com.coverself.flipdeal.entity.Product;

@RestController
public class ProductController {

	@GetMapping("/products")
	public static List<Product> getProducts() {
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

	@GetMapping("/productsinr")
	public static List<Product> getProductsInr(String discountType) {
		URI uri = null;
		List<Product> products = getProducts();
		try {
			uri = new URI(Common.EXCHANGE_RATES_URI);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ExchangeRates> responseEntityForExchangeRates = restTemplate.getForEntity(uri,
				ExchangeRates.class);
		HashMap<String, Double> rates = responseEntityForExchangeRates.getBody().getRates();

		List<Product> newProducts = new ArrayList<>();
		for (Product p : products) {
			if (!p.getCurrency().equals("INR")) {
				double priceInINR = p.getPrice() / rates.get(p.getCurrency());
				p.setPrice(Math.round(priceInINR * 100) / 100);
				p.setCurrency("INR");
			}
			setDiscount(p, discountType);
			newProducts.add(p);
		}
		return newProducts;
	}

	private static void setDiscount(Product p, String discountType) {
		switch (discountType) {
		case "promotionSetA":
			setPromotionSetA(p);
			break;
		case "promotionSetB":
			setPromotionSetB(p);
			break;
		}
	}

	private static void setPromotionSetA(Product p) {
		Discount d = new Discount();
		double discount1 = 0, discount2 = 0, discount3 = 0;
		String discountTag1 = null, discountTag2 = null, discountTag3 = null;

		// rule.1
		if (p.getOrigin().name().equals("Africa")) {
			discount1 = 0.07 * p.getPrice();
			discountTag1 = "get 7% off";
		}

		// rule.2
		if (p.getRating() == 2) {
			discount2 = 0.04 * p.getPrice();
			discountTag2 = "get 4% off";
		} else if (p.getRating() < 2) {
			discount2 = 0.08 * p.getPrice();
			discountTag2 = "get 8% off";
		}

		// rule.3
		if (p.getPrice() > 500
				&& (p.getCategory().name().equals("electronics") || p.getCategory().name().equals("furnishing"))) {
			discount3 = 100;
			discountTag3 = "get Rs 100 off";
		}

		// no discount->default discount
		if (discount1 == 0 && discount2 == 0 && discount3 == 0) {
			d.setAmount(0.02 * p.getPrice());
			d.setDiscountTag("get 2% off");
		} else {
			double finalDiscount = Math.max(Math.max(discount1, discount2), discount3);
			if (finalDiscount == discount1) {
				d.setAmount(discount1);
				d.setDiscountTag(discountTag1);
			} else if (finalDiscount == discount2) {
				d.setAmount(discount2);
				d.setDiscountTag(discountTag2);
			} else {
				d.setAmount(discount3);
				d.setDiscountTag(discountTag3);
			}
		}
		p.setDiscount(d);

	}

	private static void setPromotionSetB(Product p) {
		Discount d = new Discount();
		double discount1 = 0, discount2 = 0;
		String discountTag1 = null, discountTag2 = null;

		// rule.1
		if (p.getInventory() > 20) {
			discount1 = 0.12 * p.getPrice();
			discountTag1 = "get 12% off";
		}

		// rule.2
		if (p.isNew()) {
			discount2 = 0.07 * p.getPrice();
			discountTag2 = "get 7% off";
		}

		// no discount->default discount
		if (discount1 == 0 && discount2 == 0) {
			d.setAmount(0.02 * p.getPrice());
			d.setDiscountTag("get 2% off");
		} else {
			double finalDiscount = Math.max(discount1, discount2);
			if (finalDiscount == discount1) {
				d.setAmount(discount1);
				d.setDiscountTag(discountTag1);
			} else {
				d.setAmount(discount2);
				d.setDiscountTag(discountTag2);
			}
		}
		p.setDiscount(d);
	}

}
