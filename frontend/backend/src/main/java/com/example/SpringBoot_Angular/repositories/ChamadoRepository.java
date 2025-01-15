package com.example.SpringBoot_Angular.repositories;

import com.example.SpringBoot_Angular.model.entity.Chamado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChamadoRepository extends JpaRepository<Chamado, Integer> {
}
