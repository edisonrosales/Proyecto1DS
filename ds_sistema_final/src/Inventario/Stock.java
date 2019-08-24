package Inventario;
/**
 *
 */
public class Stock {
    private Producto nombre;
    private int id_stock, id_producto;
    private String id_matriz; 
    //todo: HostBD

    public Stock(Producto nombre, int id_stock, int id_producto, String id_matriz) {
        this.nombre = nombre;
        this.id_stock = id_stock;
        this.id_producto = id_producto;
        this.id_matriz = id_matriz;
    }

    public Producto getNombre() {
        return nombre;
    }

    public void setNombre(Producto nombre) {
        this.nombre = nombre;
    }

    public int getId_stock() {
        return id_stock;
    }

    public void setId_stock(int id_stock) {
        this.id_stock = id_stock;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getId_matriz() {
        return id_matriz;
    }

    public void setId_matriz(String id_matriz) {
        this.id_matriz = id_matriz;
    }

    public void updateStock(){
        //todo: conexion a BD
    }
}

