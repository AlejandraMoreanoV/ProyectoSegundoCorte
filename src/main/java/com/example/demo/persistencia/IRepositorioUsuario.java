package com.example.demo.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IRepositorioUsuario extends JpaRepository<EntityUsuario, Integer> {

    Optional<EntityUsuario> findByNombre(String nombre);

}
