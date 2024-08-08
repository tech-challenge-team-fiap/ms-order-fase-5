package br.com.fiap.techchallengeorder.domain.model;

import br.com.fiap.techchallengeorder.application.dto.client.ClientDto;
import br.com.fiap.techchallengeorder.application.dto.client.ClientFormDto;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.ClientDB;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {

    private String name;
    private String cpf;
    private String email;
    private String phone;
    private LocalDateTime dateRegister;

    public Client() {}

    public Client(ClientDto clientDto) {
        this.name = clientDto.getName();
        this.cpf = clientDto.getCpf();
        this.email = clientDto.getEmail();
        this.phone = clientDto.getPhone();
        this.dateRegister = clientDto.getDateRegister();
    }

    public Client(ClientFormDto clientDto) {
        this.name = clientDto.getName();
        this.cpf = clientDto.getCpf();
        this.email = clientDto.getEmail();
        this.phone = clientDto.getPhone();
        this.dateRegister = clientDto.getDateRegister();
    }

    public ClientDB build(){
        return ClientDB.builder()
                                 .name(getName())
                                 .cpf(getCpf())
                                 .email(getEmail())
                                 .phone(getPhone())
                                 .dateRegister(getDateRegister())
                                 .build();
    }
}
