package com.coverself.flipdeal;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.coverself.flipdeal.common.CommonURL;
import com.coverself.flipdeal.controller.ProductController;
import com.coverself.flipdeal.entity.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class FlipdealApplication {

	public static void main(String[] args) throws IOException {
		ConfigurableApplicationContext context = SpringApplication.run(FlipdealApplication.class, args);

		ProductController controller = context.getBean(ProductController.class);
		if (args[0] != null) {
			runApp(controller, args[0]);
		}
	}

	public static void runApp(ProductController controller, String promotionType) {
		String path = CommonURL.OUTPUT_JSON_PATH;
		ObjectMapper mapper = new ObjectMapper();

		Function<Product, String> objectToJson = p -> {
			try {
				return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(p);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		};

		// Object to JSON in String
		try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
			String jsonInString = controller.getProductsWithDiscount(promotionType).stream()//
					.map(p -> objectToJson.apply(p)).collect(Collectors.joining(", "));
			out.write(jsonInString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
