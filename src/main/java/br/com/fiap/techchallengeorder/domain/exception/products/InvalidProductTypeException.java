package br.com.fiap.techchallengeorder.domain.exception.products;

public class InvalidProductTypeException extends InvalidProductsProcessException {
    private static final String tittle = "Product type invalid";
    private static final String message = "The product type %s is invalid";

    public InvalidProductTypeException(String type) {
        super(tittle, String.format(message, type));
    }
}
