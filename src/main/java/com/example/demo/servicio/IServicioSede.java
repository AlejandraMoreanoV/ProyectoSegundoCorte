package com.example.demo.servicio;

import com.example.demo.modelo.Sede;

import java.util.List;


public interface IServicioSede {

    Sede crearSede(Sede sede);

    Sede buscarSede(int id);

    Sede buscarSede(String ciudad);

    Sede buscarSede(int id, String ciudad);

    Sede actualizarSede(Sede sede);

    Sede eliminarSede(int id);

    List<Sede> listarSedes();

    List<Sede> listarSedes(String ciudad);

}