package com.example.demo.persistencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IRepositorioUsuario extends JpaRepository<EntityUsuario, Integer> {

    @Query("SELECT c FROM EntityUsuario c WHERE c.idSede = ?1 and c.nombre= ?2")
    Optional<EntityUsuario> findByNombre(int idSede, String nombre);

}
