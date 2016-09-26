package com.parking.service.persistence.repository;

import com.parking.service.persistence.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Client: sbenner
 * Date: 5/4/16
 * Time: 6:26 AM
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, QueryDslPredicateExecutor<Client> {

    Client findClientById(Long id);

    Client findByUserNameAndPassword(String userName, String pass);
    Client findByUserName(String userName);
}
