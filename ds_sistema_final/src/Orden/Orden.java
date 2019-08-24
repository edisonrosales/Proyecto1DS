package Orden;

import L_Compra.Detalle;
import java.util.List;


public abstract class Orden {
    protected int id_pedido;
    protected String direccion;
    protected List<Detalle> productos;
    
    public abstract String getDireccion();
    
    public abstract int getId_pedido();
}