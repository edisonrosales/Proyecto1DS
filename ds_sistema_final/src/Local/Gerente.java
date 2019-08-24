package Local;

import java.util.List;
import L_Compra.Compra;
import Inventario.Producto;


public class Gerente extends Usuario {

    public Gerente(String user, String password, boolean isAdmin, String nombre, String apellido, String id) {
        super(user, password, isAdmin, nombre, apellido, id);
    }
    
    
    public List<Usuario> consultarUsuarios(){
        //todo: obtener lista de usuarios BD
        return null;
    }
    
    public List<Cliente> consultarClientes(){
        //todo: obtener lista de clientes BD
        return null;
    }
    
    public List<Producto> consultarProductos(){
        //todo: obtener lista de productos BD
        return null;
    }
    
    public List<Compra> consultarCompras(){
        //todo: obtener lista de compras BD
        return null;
    }
    
    public boolean makeViewAdmin(Usuario u){
        //llama BD y cambia estado de admin a true
        return false;
    }
}
