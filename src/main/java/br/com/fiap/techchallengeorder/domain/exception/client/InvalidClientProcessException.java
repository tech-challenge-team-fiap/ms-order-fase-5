package br.com.fiap.techchallengeorder.domain.exception.client;

import br.com.fiap.techchallengeorder.domain.exception.InvalidProcessException;

public abstract class InvalidClientProcessException extends InvalidProcessException {

    public InvalidClientProcessException(String tittle, String message) {
        super(tittle, message);
    }
}
