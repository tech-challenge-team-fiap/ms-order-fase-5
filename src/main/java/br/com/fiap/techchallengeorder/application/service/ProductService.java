package br.com.fiap.techchallengeorder.application.service;

import br.com.fiap.techchallengeorder.application.dto.product.ProductFormDto;
import br.com.fiap.techchallengeorder.domain.exception.products.ProductNotFoundException;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.ProductDB;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    boolean productFindById(UUID productId);

    List<ProductDB> productFindAllById(List<UUID> productIds);

    void edit(ProductDB productDB) throws ProductNotFoundException;
}
