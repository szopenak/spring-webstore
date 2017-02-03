package com.packt.webstore.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.BAD_GATEWAY, reason="Brak produktów we wskazanej kategorii")
public class NoProductsFoundUnderCategoryException extends java.lang.RuntimeException{
private static final long serialVersionUID = 3935230281455340039L;
}
