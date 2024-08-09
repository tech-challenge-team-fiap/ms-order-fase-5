package br.com.fiap.techchallengeorder.application.dto.payment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMessage {

    private String id;
    private String productId;
    private String order;
    private LocalDate createdAt;

}