package com.coverself.flipdeal.service;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coverself.flipdeal.common.PromotionType;
import com.coverself.flipdeal.dao.ProductDAO;
import com.coverself.flipdeal.entity.Discount;
import com.coverself.flipdeal.entity.Product;

@Service
public class ProductService implements ProductServiceInterface {

	@Autowired
	ProductDAO productDao;

	@Override
	public List<Product> getProductsWithDiscount(String discountType) {

		List<Product> products = productDao.getProducts();
		HashMap<String, Double> rates = productDao.getExchangeRates();

		Function<Product, Product> priceToINR = p -> {
			p.setPrice(p.getPrice() / rates.get(p.getCurrency()));
			return p;
		};
		Function<Product, Product> roundOff = p -> {
			p.setPrice(Math.round(p.getPrice() * 100) / 100);
			return p;
		};

		products.stream().filter(p -> !p.getCurrency().equals("INR")).map(p -> priceToINR.andThen(roundOff).apply(p))
				.map(p -> {
					p.setCurrency("INR");
					return p;
				}).collect(Collectors.toList());

		List<Product> productsWithDiscount = products.stream().map(p -> {
			setDiscount(p, discountType);
			return p;
		}).collect(Collectors.toList());

		return productsWithDiscount;
	}

	private static void setDiscount(Product product, String discountType) {
		switch (discountType) {
		case PromotionType.PROMOTION_SET_A:
			setPromotionSetA(product);
			break;
		case PromotionType.PROMOTION_SET_B:
			setPromotionSetB(product);
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
