package br.com.fiap.techchallengeorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {"br.com.fiap.techchallengeorder"})
@EnableJpaRepositories(basePackages = "br.com.fiap.techchallengeorder.external.infrastructure.repositories")
@EntityScan(basePackages = "br.com.fiap.techchallengeorder.external.infrastructure.entities")
public class TechChallengeOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechChallengeOrderApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
