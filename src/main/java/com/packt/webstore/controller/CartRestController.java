package com.packt.webstore.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.packt.webstore.domain.Cart;
import com.packt.webstore.domain.CartItem;
import com.packt.webstore.domain.Product;
import com.packt.webstore.exception.ProductNotFoundException;
import com.packt.webstore.service.CartService;
import com.packt.webstore.service.ProductService;

@RestController
@RequestMapping(value = "rest/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartRestController {
	@Autowired
	private CartService cartService;
	@Autowired
	private ProductService productService;
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Cart create(@RequestBody Cart cart) {
		return cartService.create(cart);
	}
	
	@RequestMapping(value = "/{cartId}", method = RequestMethod.GET)
	public @ResponseBody Cart read(@PathVariable(value = "cartId") String cartId) {

		Cart cart = cartService.read(cartId);
		if (cart == null) throw new IllegalArgumentException();
		return cart;
	}
	
	@RequestMapping(value = "/{cartId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void update(@PathVariable(value = "cartId") String cartId, @RequestBody Cart cart) {
		cartService.update(cartId, cart);
	}
	
	@RequestMapping(value = "/{cartId}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(value = "cartId") String cartId) {
		cartService.delete(cartId);
	}
	
	@RequestMapping(value = "/prod/{productId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addItem(@PathVariable String productId, HttpServletRequest request) {
		String sessionId = request.getSession(true).getId();
		Cart cart = cartService.read(sessionId);
		if(cart== null) {
		cart = cartService.create(new Cart(sessionId));
		}
		Product product = productService.getProductById(productId);
		if(product == null) {
		throw new ProductNotFoundException(productId);
		}
		cart.addCartItem(new CartItem(product));
		cartService.update(sessionId, cart);
	}
	
	@RequestMapping(value = "/prod/{productId}", method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void removeItem(@PathVariable String productId, HttpServletRequest request) {
		String sessionId = request.getSession(true).getId();
		Cart cart = cartService.read(sessionId);
		if(cart== null) {
			throw new IllegalArgumentException();
		}
		Product product = productService.getProductById(productId);
		if(product == null) {
			throw new ProductNotFoundException(productId);
		}
		cart.removeCartItem(new CartItem(product));
		cartService.update(sessionId, cart);
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Bad data sent.")
	public void handleClientErrors(Exception ex) { }

	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="Bad product.")
	public void handleProductErrors(Exception ex) { }
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason="Internal server error.")
	public void handleServerErrors(Exception ex) { }
}
