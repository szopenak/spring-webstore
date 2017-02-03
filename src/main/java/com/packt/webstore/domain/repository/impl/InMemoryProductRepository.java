package com.packt.webstore.domain.repository.impl;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;
import com.packt.webstore.domain.Product;
import com.packt.webstore.domain.repository.ProductRepository;
import com.packt.webstore.exception.ProductNotFoundException;

@Repository
public class InMemoryProductRepository implements ProductRepository{
	private List<Product> listOfProducts = new ArrayList<Product>();
	public InMemoryProductRepository() {
		Product iphone = new Product("1","iPhone 5s", new BigDecimal(500));
		iphone.setDescription("Apple iPhone 5s, smartfon z 4-calowym ekranem o rozdzielczoœci 640x1136 i 8-megapikselowym aparatem");
		iphone.setCategory("Smartfon");
		iphone.setManufacturer("Apple");
		iphone.setUnitsInStock(1000);
		Product laptop_dell = new Product("2","Dell Inspiron", new BigDecimal(700));
		laptop_dell.setDescription("Dell Inspiron, 14-calowy laptop (czarny) z procesorem Intel Core 3. generacji");
		laptop_dell.setCategory("Laptop");
		laptop_dell.setManufacturer("Dell");
		laptop_dell.setUnitsInStock(1000);
		Product tablet_Nexus = new Product("3","Nexus 7", new BigDecimal(300));
		tablet_Nexus.setDescription("Google Nexus 7 jest najl¿ejszym 7-calowym tabletem z 4-rdzeniowym procesorem Qualcomm Snapdragon™ S4 Pro");
		tablet_Nexus.setCategory("Tablet");
		tablet_Nexus.setManufacturer("Google");
		tablet_Nexus.setUnitsInStock(1000);
		listOfProducts.add(iphone);
		listOfProducts.add(laptop_dell);
		listOfProducts.add(tablet_Nexus);
	}
	public List<Product> getAllProducts() {
	return listOfProducts;
	}
	
	public Product getProductById(String productId) {
			Product productById = null;
			for(Product product : listOfProducts) {
				if(product!=null && product.getProductId()!=null && product.getProductId().equals(productId)){
					productById = product;
					break;
				}
			}
			if(productById == null){
				throw new ProductNotFoundException(productId);
			}
			return productById;
		}
	
	public List<Product> getProductsByCategory(String category) {
		List<Product> productsByCategory = new ArrayList<Product>();
		for(Product product: listOfProducts) {
			if(category.equalsIgnoreCase(product.getCategory())){
			productsByCategory.add(product);
			}
		}
		return productsByCategory;
		}
	
	public Set<Product> getProductsByFilter(Map<String, List<String>> filterParams) {
		Set<Product> productsByBrand = new HashSet<Product>();
		Set<Product> productsByCategory = new HashSet<Product>();
		Set<String> criterias = filterParams.keySet();
		if(criterias.contains("brand")) {
			for(String brandName: filterParams.get("brand")) {
				for(Product product: listOfProducts) {
					if(brandName.equalsIgnoreCase(product.getManufacturer())){
						productsByBrand.add(product);
					}
				}
			}
		}
		if(criterias.contains("category")) {
			for(String category: filterParams.get("category")) {
			productsByCategory.addAll(this.getProductsByCategory(category));
			}
		}
		productsByCategory.retainAll(productsByBrand);
		return productsByCategory;
		}
	
	public Set <Product> getProductsByManufacturer(String manufacturer) {
		Set <Product> list = new HashSet<Product>();
		for(Product product : listOfProducts) {
			if(product!=null && product.getManufacturer()!=null && product.getManufacturer().equals(manufacturer)){
				list.add(product);
			}
		}
		if(list.isEmpty()){
			throw new IllegalArgumentException("Brak produktów od :"+ manufacturer);
		}
		return list;
	
	}
	public Set<Product> getProductsByPriceFilter(Map<String, List<String>> filterParams) {
		// TODO Auto-generated method stub
		Set <Product>products = new HashSet<Product>();
		Set<String> criterias = filterParams.keySet();
		if(criterias.contains("low")&&criterias.contains("high")) {
			BigDecimal low = BigDecimal.valueOf(Long.valueOf(filterParams.get("low").get(0)));
			BigDecimal high = BigDecimal.valueOf(Long.valueOf(filterParams.get("high").get(0)));
			for(Product product : listOfProducts) {
				if(product!=null && product.getUnitPrice()!=null && 
						(product.getUnitPrice().compareTo(low)>0 && 
						product.getUnitPrice().compareTo(high)<0)) {
					products.add(product);
				}
			}
		}
		return products;
	}
	public void addProduct(Product product) {
		listOfProducts.add(product);
		}
}