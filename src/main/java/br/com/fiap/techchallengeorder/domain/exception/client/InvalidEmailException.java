package br.com.fiap.techchallengeorder.domain.exception.client;

public class InvalidEmailException extends InvalidClientProcessException {

    private static final String tittle = "Invalid Email";
    private static final String message = "The Email %s is invalid";

    public InvalidEmailException(String email) {
        super(tittle, String.format(message, email));
    }
}
