package Inventario;


public class ad_producto {
    private String nombre,tipoLocalidad,direccion,id_matriz;
    private int Stock,id_producto;

    public ad_producto(String nombre, String tipoLocalidad, int Stock,String direccion,String id_matriz,int id_producto) {
        this.nombre = nombre;
        this.tipoLocalidad = tipoLocalidad;
        this.Stock = Stock;
        this.direccion=direccion;
        this.id_matriz=id_matriz;
        this.id_producto=id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoLocalidad() {
        return tipoLocalidad;
    }

    public void setTipoLocalidad(String tipoLocalidad) {
        this.tipoLocalidad = tipoLocalidad;
    }

    public int getStock() {
        return Stock;
    }

    public void setStock(int Stock) {
        this.Stock = Stock;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getId_matriz() {
        return id_matriz;
    }

    public void setId_matriz(String id_matriz) {
        this.id_matriz = id_matriz;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    
    
    
    
}
