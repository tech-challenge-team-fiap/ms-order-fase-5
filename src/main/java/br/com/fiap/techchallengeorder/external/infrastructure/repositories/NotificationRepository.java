package br.com.fiap.techchallengeorder.external.infrastructure.repositories;

import br.com.fiap.techchallengeorder.external.infrastructure.entities.NotificationDB;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends CrudRepository<NotificationDB, UUID> {


}