package br.com.fiap.techchallengeorder.domain.exception;

public class BaseException extends RuntimeException {
    public BaseException(String message) {
        super(message);
    }
}