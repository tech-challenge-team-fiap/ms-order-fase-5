package br.com.fiap.techchallengeorder.application.service.impl;

import br.com.fiap.techchallengeorder.application.dto.product.ProductEditFormDto;
import br.com.fiap.techchallengeorder.application.dto.product.ProductFormDto;
import br.com.fiap.techchallengeorder.application.service.ProductService;
import br.com.fiap.techchallengeorder.domain.enums.TypeProduct;
import br.com.fiap.techchallengeorder.domain.enums.TypeStatus;
import br.com.fiap.techchallengeorder.domain.exception.products.ProductNotFoundException;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.ClientDB;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.ProductDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService {

    private final RestTemplate restTemplate;

    @Value("${external.service-product.url}")
    private String productMicroService;

    @Autowired
    public ProductServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean productFindById(UUID productId) {
        try {
            ResponseEntity<?> responseEntity = restTemplate.getForEntity(productMicroService + "/id/" + productId, Object.class);
            return  true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<ProductDB> productFindAllById(List<UUID> productIds) {

        ProductDB productDB = new ProductDB();
        productDB.setTypeProduct(TypeProduct.DRINK);
        productDB.setQuantity(2);
        productDB.setId(UUID.fromString(UUID.randomUUID().toString()));
        productDB.setName("Coca-cola");
        productDB.setPrice(new BigDecimal("3.5"));
        productDB.setTypeStatus(TypeStatus.ACTIVE);
        productDB.setDescription("Soda");
        productDB.setDateRegister(LocalDateTime.now());


        ArrayList<ProductDB> productDBS = new ArrayList<>();
        productDBS.add(productDB);

        return  productDBS;
    }

    @Override
    public void edit(ProductDB productDB) throws ProductNotFoundException {
        HttpEntity<ProductEditFormDto> requestEntity = new HttpEntity<>(this.mapperProductEditFormDto(productDB));

        try {
            ResponseEntity<?> responseEntity = restTemplate.exchange(
                    productMicroService,
                    HttpMethod.PUT,
                    requestEntity,
                    Object.class
            );
        }catch (Exception e){
            throw new ProductNotFoundException(productDB.getId());
        }

    }

    private ProductEditFormDto mapperProductEditFormDto(ProductDB productDB){
        Calendar c = Calendar.getInstance();


        ProductEditFormDto productEditFormDto = new ProductEditFormDto(
                productDB.getId(), productDB.getName(), productDB.getDescription(), productDB.getQuantity(),
                productDB.getTypeProduct(), productDB.getPrice(), productDB.getTypeStatus(), c.getTime());

        return productEditFormDto;

    }

}
