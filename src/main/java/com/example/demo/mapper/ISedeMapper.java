package com.example.demo.mapper;

import com.example.demo.modelo.Sede;
import com.example.demo.persistencia.EntitySede;

public interface ISedeMapper {
    Sede toDto (EntitySede entitySede);
    EntitySede toEntity (Sede sede);

}