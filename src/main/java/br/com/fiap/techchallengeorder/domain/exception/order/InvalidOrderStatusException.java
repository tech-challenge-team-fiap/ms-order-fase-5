package br.com.fiap.techchallengeorder.domain.exception.order;

import br.com.fiap.techchallengeorder.domain.enums.StatusOrder;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

public class InvalidOrderStatusException extends InvalidOrderProcessException {
    private static final String tittle = "Invalid status order";
    private static final String message = "The status order %s is invalid, acceptable values is %s";

    public InvalidOrderStatusException(String status) {
        super(tittle, String.format(message, status, acceptableValues()));
    }

    private static List<String> acceptableValues() {
        List<StatusOrder> result = new ArrayList<>(EnumSet.allOf(StatusOrder.class));

        return result.stream().map(StatusOrder::name)
            .collect(Collectors.toList());
    }
}
