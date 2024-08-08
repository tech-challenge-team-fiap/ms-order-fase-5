package br.com.fiap.techchallengeorder.domain.exception.client;

import java.util.UUID;

public class ClientNotFoundException extends InvalidClientProcessException{
    private static final String tittle = "Client not found";
    private static final String message = "The client %s not found";

    public ClientNotFoundException(String cpf) {
        super(tittle, String.format(message, cpf));
    }

    public ClientNotFoundException(UUID id) {
        super(tittle, String.format(message, id));
    }
}
