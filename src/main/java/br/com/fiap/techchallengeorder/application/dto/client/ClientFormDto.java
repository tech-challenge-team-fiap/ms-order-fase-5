package br.com.fiap.techchallengeorder.application.dto.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientFormDto {
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private LocalDateTime dateRegister;
}