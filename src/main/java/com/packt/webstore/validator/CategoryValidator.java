package com.packt.webstore.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.packt.webstore.service.ProductService;

public class CategoryValidator implements ConstraintValidator<Category, String>{

	@Autowired
	private ProductService productService;
	private List <String> allowedCategories = new ArrayList<>();
	
	@Override
	public void initialize(Category arg0) {
		allowedCategories.add("Notebook");
		allowedCategories.add("Phone");
		
	}

	@Override
	public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
		// in future add reading categories from DB
		if (allowedCategories.contains(arg0)) return true;
		return false;
	}

}
