package com.example.demo.servicio;

import com.example.demo.modelo.Usuario;

import java.util.List;

public interface IServicioUsuario {

    public Usuario crearUsuario(Usuario usuario);
    public Usuario buscarUsuario(Usuario usuario);
    public Usuario buscarUsuario(int id);
    public Usuario buscarUsuario(String nombre);
    public Usuario buscarUsuario(int id, String nombre);
    public Usuario actualizarUsuario(Usuario usuario);
    public Usuario eliminarUsuario(int id);
    public List<Usuario> listarUsuarios();
    public List<Usuario> listarUsuarios(String nombre);

    /**
    public Infraccion getInfraccion();
    public Infraccion setInfraccion(Infraccion infraccion);
    public Infraccion actualizarInfraccion(int numero, String tipo);
    **/

}