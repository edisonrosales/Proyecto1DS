/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Local;


public class Cliente extends Persona {
    protected String direccion;
    protected String telefono;
    
    //todo:  buscar lib para telefono

    public Cliente(String nombre, String apellido, String id, String direccion, String telefono) {
        super(nombre, apellido, id);
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }
}
