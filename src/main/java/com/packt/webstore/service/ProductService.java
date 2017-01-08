package com.packt.webstore.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.packt.webstore.domain.Product;

public interface ProductService {
	public List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
	
	Set<Product> getProductsByFilter(Map<String, List<String>> filterParams);
	public Product getProductById(String productId);
	public Set<Product> getProductsByManufacturer(String manufacturer);
	public Set<Product> getProductsByPriceFilter(Map<String, List<String>> filterParams);
	void addProduct(Product product);
}
