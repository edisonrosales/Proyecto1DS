package Bodega;

import java.util.LinkedList;
import Orden.Orden;

/**
 *
 */
public class Ruta {
    private int id_ruta;
    private Jefe jefe;
    private Repartidor repartidor;
    private String status;
    private int cantidad;
    private LinkedList<Orden> pedidos;
    private LinkedList<String> direcciones;

    public Ruta(int id_ruta, Jefe jefe, Repartidor repartidor, String status, int cantidad) {
        this.id_ruta = id_ruta;
        this.jefe = jefe;
        this.repartidor = repartidor;
        this.status = status;
        this.cantidad = cantidad;
        pedidos = new LinkedList<>();
        direcciones = new LinkedList<>();
    }

    public void setPedidos(LinkedList<Orden> pedidos) {
        this.pedidos = pedidos;
        direcciones.clear();
        pedidos.forEach((p) -> {
            direcciones.add(p.getDireccion());
        });
        cantidad = pedidos.size();
    }  
    
    public void agregarPedido(Orden pedido){
        pedidos.add(pedido);
        direcciones.add(pedido.getDireccion());
        cantidad++;
    }

    public int getId_ruta() {
        return id_ruta;
    }

    public Repartidor getRepartidor() {
        return repartidor;
    }

    public String getStatus() {
        return status;
    }

    public int getCantidad() {
        return cantidad;
    }

    public Jefe getJefe() {
        return jefe;
    }

    public LinkedList<Orden> getPedidos() {
        return pedidos;
    }

    public LinkedList<String> getDirecciones() {
        return direcciones;
    }

    public void setId_ruta(int id_ruta) {
        this.id_ruta = id_ruta;
    }
}

