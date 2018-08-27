package com.packt.webstore.domain;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import com.packt.webstore.validator.Category;
import com.packt.webstore.validator.ProductId;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.constraints.*;

@Data
@XmlRootElement
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Product {
	
	@Pattern(regexp="P[0-9]+", message="{Pattern.Product.productId.validation}")
	@ProductId
	private String productId;
	
	@Size(min=4, max=50, message="{Size.Product.name.validation}")
	private String name;
	
	@Min(value=0, message="Min.Product.unitPrice.validation}")
	@Digits(integer=8, fraction=2, message="{Digits.Product.unitPrice.validation}")
	@NotNull(message= "{NotNull.Product.unitPrice.validation}")
	private BigDecimal unitPrice;
	private String description;
	private String manufacturer;
	@Pattern(regexp="[a-z]+", message= "{NotNull.Product.category.validation}")
	@Category
	private String category;
	@Min(value=1, message="{Min.Product.unitsInStock.validation}")
	private long unitsInStock;
	private long unitsInOrder;
	private boolean discontinued;
	private String condition;
	@JsonIgnore
	private MultipartFile productImage;

	public Product(String productId, String name, BigDecimal unitPrice) {
		this.productId = productId;
		this.name = name;
		this.unitPrice = unitPrice;
	}
	@XmlTransient
	public MultipartFile getProductImage() {
		return productImage;
	}
}