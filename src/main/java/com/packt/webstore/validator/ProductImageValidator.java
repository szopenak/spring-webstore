package com.packt.webstore.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.packt.webstore.domain.Product;

public class ProductImageValidator implements Validator {
	private long maxSize = 2000000;
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Product.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product) target;
		if(product.getProductImage().getSize()> maxSize) {
		errors.rejectValue("productImage","com.packt.webstore.validator.ProductImageValidator.message");
	}
	}
}
