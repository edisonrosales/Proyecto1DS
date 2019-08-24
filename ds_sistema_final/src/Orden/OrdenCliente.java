package Orden;

import Local.Cliente;
import Local.Vendedor;


public class OrdenCliente extends Orden{
    protected Cliente cliente;
    protected Vendedor vendedor;
    
    @Override
    public String getDireccion() {
        return cliente.getDireccion();
    }

    public OrdenCliente(int id_pedido, Cliente cliente, Vendedor vendedor) {
        super.id_pedido = id_pedido;
        this.cliente = cliente;
        this.vendedor = vendedor;
    }

    @Override
    public int getId_pedido() {
        return id_pedido;
    }
}
