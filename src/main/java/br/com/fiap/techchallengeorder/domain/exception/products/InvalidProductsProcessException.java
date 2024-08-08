package br.com.fiap.techchallengeorder.domain.exception.products;

import br.com.fiap.techchallengeorder.domain.exception.InvalidProcessException;

public abstract class InvalidProductsProcessException extends InvalidProcessException {

    public InvalidProductsProcessException(String tittle, String message) {
        super(tittle, message);
    }
}
