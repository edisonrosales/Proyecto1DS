
package Local;

import L_Compra.Compra;


public class Vendedor extends Usuario {
    
    public Vendedor(String user, String password, boolean isAdmin, String nombre, String apellido, String id) {
        super(user, password, isAdmin, nombre, apellido, id);
    }
    
    public Cliente agregarCliente(){
        return null;
    }
    
    public Cotizacion realizarCotizacion(){
        return null;
    }
    
    public Compra realizarVenta(){
        return null;
    }
}