package br.com.fiap.techchallengeorder.application.dto.client;

import java.time.LocalDateTime;

import br.com.fiap.techchallengeorder.external.infrastructure.entities.ClientDB;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDto {
    private String id;
    private String name;
    private String cpf;
    private String email;
    private String phone;
    private LocalDateTime dateRegister;

    public ClientDto(){
        //empty
    };

    public ClientDto(String id){
        this.id = id;
    }

    public ClientDto(String name, String cpf, String email, String phone, LocalDateTime dateRegister) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
        this.dateRegister = dateRegister;
    }

    public static ClientDto toDto(ClientDB client) {
        return new ClientDto(client.getName(),client.getCpf(), client.getEmail(),client.getPhone(),client.getDateRegister());
    }
}