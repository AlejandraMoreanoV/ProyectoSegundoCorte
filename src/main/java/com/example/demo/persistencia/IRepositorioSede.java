package com.example.demo.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRepositorioSede extends JpaRepository<EntitySede, Integer> {

    Optional<EntitySede> findByCiudad(String ciudad);

}
