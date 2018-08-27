package com.packt.webstore.domain.repository;
import com.packt.webstore.domain.Product;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProductRepository {
	List <Product> getAllProducts();
	Product getProductById(String productId);
	List<Product> getProductsByCategory(String category);
	Set <Product> getProductsByManufacturer(String manufacturer);
	void addProduct(Product product);
}

