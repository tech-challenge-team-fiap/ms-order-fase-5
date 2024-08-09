package br.com.fiap.techchallengeorder.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ProductMessage {
    private UUID id;
    private int quantity;

    public ProductMessage(UUID id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}