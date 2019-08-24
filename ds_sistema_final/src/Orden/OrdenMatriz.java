package Orden;


import java.util.LinkedList;
import Inventario.Matriz;
import Local.Vendedor;


public class OrdenMatriz extends Orden {
    private Matriz matriz;
    private Vendedor vendedor;

    public OrdenMatriz(Matriz matriz, Vendedor vendedor, int id_pedido) {
        super.id_pedido = id_pedido;
        super.productos = new LinkedList<>();
        this.matriz = matriz;
        this.vendedor = vendedor;
    }
    
    @Override
    public int getId_pedido() {
        return id_pedido;
    }

    @Override
    public String getDireccion() {
        return matriz.getDireccion();
    }
    
}
