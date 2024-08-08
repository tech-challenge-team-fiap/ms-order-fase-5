package br.com.fiap.techchallengeorder.domain.exception.order;

public class OrderNotFoundException extends InvalidOrderProcessException {
    private static final String tittle = "Order not found";
    private static final String message = "The order %s does not found";

    public OrderNotFoundException(String orderNumber) {
        super(tittle, String.format(message, orderNumber));
    }
}
