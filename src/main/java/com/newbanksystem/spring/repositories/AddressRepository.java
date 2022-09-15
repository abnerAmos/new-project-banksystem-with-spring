package com.newbanksystem.spring.repositories;

import com.newbanksystem.spring.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Annotation para identificar a classe como Repository (Não Obrigatório, Apenas Identificação)

public interface AddressRepository extends JpaRepository<Address, Integer> {
} // A Classe JpaRepository transforma a Interface em um Bean do Spring
