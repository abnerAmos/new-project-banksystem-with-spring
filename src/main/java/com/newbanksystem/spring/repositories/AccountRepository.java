package com.newbanksystem.spring.repositories;

import com.newbanksystem.spring.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    Optional<Account> findFirstByNumber(Integer number);
}
