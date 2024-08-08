package br.com.fiap.techchallengeorder.application.service;

import br.com.fiap.techchallengeorder.domain.exception.client.ClientNotFoundException;
import br.com.fiap.techchallengeorder.domain.exception.products.ProductNotFoundException;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.ClientDB;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface ClientService {

    ClientDB clientFindById(UUID clientId) throws ProductNotFoundException, ClientNotFoundException;
}
