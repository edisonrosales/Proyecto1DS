package L_Compra;

import Inventario.Producto;

/**
 *
 */
public class Detalle {
    private Producto producto;
    private Compra compra;
    private int cantidad;
    
    public double calcularTotal(){
        return producto.getPrecio() * cantidad;
    }
}