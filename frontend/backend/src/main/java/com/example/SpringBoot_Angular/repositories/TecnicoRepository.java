package com.example.SpringBoot_Angular.repositories;

import com.example.SpringBoot_Angular.model.entity.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {
}
