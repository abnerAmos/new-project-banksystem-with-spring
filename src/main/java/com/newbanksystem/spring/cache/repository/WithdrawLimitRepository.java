package com.newbanksystem.spring.cache.repository;

import com.newbanksystem.spring.cache.model.WithdrawLimit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawLimitRepository extends CrudRepository<WithdrawLimit, Integer> {
}
