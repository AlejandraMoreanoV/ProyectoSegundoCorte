package com.example.demo.servicio;

import com.example.demo.modelo.Sede;
import com.example.demo.modelo.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ServicioSede implements IServicioSede {

    private static List<Sede> listaSedes = new ArrayList<>();

    @Override
    public Sede crearSede(Sede sede) {
        // Verificar los atributos.
        if (buscarSede(sede.getId()) == null
                && sede.getId() >= 0
                && sede.getDireccion() != null
                && !sede.getDireccion().isEmpty()
                && sede.getCiudad() != null
                && !sede.getCiudad().isEmpty()
                && sede.getFechaRegistro() != null
                && sede.getM2() > 0) {
            sede.setListaUsuarios(new ArrayList<>());
            listaSedes.add(sede);
            return sede;
        }
        return null;
    }

    @Override
    public Sede buscarSede(int id) {
        for (Sede sede : listaSedes) {
            if (sede.getId() == id) {
                return sede;
            }
        }
        return null;
    }

    @Override
    public Sede buscarSede(String ciudad) {
        // Considerar mayúsculas, minúsculas y vacíos.
        for (Sede sede : listaSedes) {
            if (sede.getCiudad().equalsIgnoreCase(ciudad.trim())) {
                return sede;
            }
        }
        return null;
    }

    @Override
    public Sede buscarSede(int id, String ciudad) {
        // Considerar mayúsculas, minúsculas y vacíos.
        for (Sede sede : listaSedes) {
            if (sede.getId() == id && sede.getCiudad().equalsIgnoreCase(ciudad.trim())) {
                return sede;
            }
        }
        return null;
    }

    @Override
    public Sede actualizarSede(Sede s) {
        Sede sede = buscarSede(s.getId());
        if (sede == null) {
            return null;
        } else if (sede.getId()>= 0
                    && s.getDireccion() != null
                    && !s.getDireccion().isEmpty()
                    && s.getCiudad() != null
                    && !s.getCiudad().isEmpty()
                    && s.getFechaRegistro() != null
                    && s.getM2() > 0) {
                //eliminarSede(s.getId());
                //crearSede(sede);
                sede.setDireccion(s.getDireccion());
                sede.setM2(s.getM2());
                return sede;
        } else {
            return null;
        }
    }

    @Override
    public Sede eliminarSede(int id) {
        Sede sede = buscarSede(id);
        if (sede != null) {
            listaSedes.remove(sede);
            return sede;
        }
        return null;
    }

    @Override
    public List<Sede> listarSedes() {
        return listaSedes;
    }

    @Override
    public List<Sede> listarSedes(String ciudad) {
        List<Sede> listaEncontrados = new ArrayList<>();
        for (Sede sede : listaSedes) {
            if (sede.getCiudad().equalsIgnoreCase(ciudad)) {
                listaEncontrados.add(sede);
            }
        }
        return listaEncontrados;
    }
}