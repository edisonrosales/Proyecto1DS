/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventario;

import java.util.LinkedList;


public class Matriz {
    private String id;
    private String tipo;
    private String direccion;
    private LinkedList<Stock> stocks;
    
    public Matriz(String id, String direccion, String tipo){
        this.id = id;
        this.direccion = direccion;
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }
}
