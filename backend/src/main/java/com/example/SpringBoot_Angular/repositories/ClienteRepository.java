package com.example.SpringBoot_Angular.repositories;

import com.example.SpringBoot_Angular.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
