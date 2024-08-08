package br.com.fiap.techchallengeorder.application.service.impl;

import br.com.fiap.techchallengeorder.application.dto.product.ProductEditFormDto;
import br.com.fiap.techchallengeorder.application.service.ClientService;
import br.com.fiap.techchallengeorder.application.service.ProductService;
import br.com.fiap.techchallengeorder.domain.enums.TypeProduct;
import br.com.fiap.techchallengeorder.domain.enums.TypeStatus;
import br.com.fiap.techchallengeorder.domain.exception.client.ClientNotFoundException;
import br.com.fiap.techchallengeorder.domain.exception.products.ProductNotFoundException;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.ClientDB;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.ProductDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class ClientServiceImpl implements ClientService {

    @Override
    public ClientDB clientFindById(UUID clientId) throws ProductNotFoundException, ClientNotFoundException {

        ClientDB clientDB = new ClientDB();
        clientDB.setEmail("andreluizdasilva@gmail.com");
        clientDB.setName("Andre Luiz da Silva");
        clientDB.setPhone("81988558855");
        clientDB.setDateRegister(LocalDateTime.now());
        clientDB.setCpf("19100000000");
        clientDB.setId(UUID.fromString(UUID.randomUUID().toString()));
        return clientDB;
    }
}
