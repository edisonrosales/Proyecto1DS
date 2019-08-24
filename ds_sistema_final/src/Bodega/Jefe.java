package Bodega;

import java.util.LinkedList;
import java.util.List;
import Orden.Orden;
import Local.Usuario;


public class Jefe extends Usuario {
    protected List<Orden> pedidos;

    public Jefe(String user, String password, boolean isAdmin, String nombre, String apellido, String id) {
        super(user, password, isAdmin, nombre, apellido, id);
        pedidos = new LinkedList<>();
    }
    
}