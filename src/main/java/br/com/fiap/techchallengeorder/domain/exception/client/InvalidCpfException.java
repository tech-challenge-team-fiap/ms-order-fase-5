package br.com.fiap.techchallengeorder.domain.exception.client;

public class InvalidCpfException extends InvalidClientProcessException {
    private static final String tittle = "Invalid CPF";
    private static final String message = "The CPF %s is a invalid number";

    public InvalidCpfException(String cpf) {
        super(tittle, String.format(message, cpf));
    }
}
