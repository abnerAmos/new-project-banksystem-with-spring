package com.newbanksystem.spring.cache.repository;

import com.newbanksystem.spring.cache.model.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, Integer> {

}
