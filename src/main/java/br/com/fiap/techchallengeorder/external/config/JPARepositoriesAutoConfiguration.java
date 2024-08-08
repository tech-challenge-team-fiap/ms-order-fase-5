package br.com.fiap.techchallengeorder.external.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("br.com.fiap.techchallengeorder.external.infrastructure.repository")
public class JPARepositoriesAutoConfiguration {

}
