package Bodega;

import java.util.LinkedList;
import Inventario.Matriz;
import Local.Persona;

/**
 *
 */
public class Repartidor extends Persona{
    protected Matriz matriz;
    protected LinkedList<Ruta> rutas;
    protected int cantRutas;

    public Repartidor(String nombre, String apellido, String id, int cant) {
        super(nombre, apellido, id);
        cantRutas = cant;
    }
    
    @Override
    public String toString(){
        return this.nombre + " " + this.apellido;
    }
}
