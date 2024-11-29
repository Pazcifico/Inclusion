package com.projeto.sistema.repository;

import com.projeto.sistema.model.Evento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByUsuarioCriadorId(Long usuarioCriadorId);
}

