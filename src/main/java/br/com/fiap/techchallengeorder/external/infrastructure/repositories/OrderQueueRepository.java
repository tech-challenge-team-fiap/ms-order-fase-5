package br.com.fiap.techchallengeorder.external.infrastructure.repositories;

import br.com.fiap.techchallengeorder.domain.enums.StatusOrder;
import br.com.fiap.techchallengeorder.external.infrastructure.entities.OrderQueueDB;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderQueueRepository extends CrudRepository<OrderQueueDB, UUID> {

    List<OrderQueueDB> findAllByStatusOrder(Sort dateRegister, StatusOrder statusOrder);
}