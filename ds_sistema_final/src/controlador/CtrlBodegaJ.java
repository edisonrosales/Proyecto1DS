/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import javafx.collections.ObservableList;
import Bodega.Jefe;
import Bodega.Repartidor;
import Bodega.Ruta;
import Inventario.Matriz;
import Local.Cliente;
import Local.Vendedor;
import Orden.Orden;
import Orden.OrdenCliente;
import Orden.OrdenMatriz;
import BD.Conexion;

/*
 */
public class CtrlBodegaJ{
    
    private final Jefe jefe;
    private Queue<Repartidor> repartidores;
    
    public CtrlBodegaJ(Jefe jefe){
        try {
            obtenerRepartidores();
        } catch (SQLException ex) {
           mensajes.notificaciones.mostrarDialogo("No fue posible conectarse al servidor.", "Error de conexión", "Error");
        }
        this.jefe = jefe;
    }

    public ResultSet obtenerRSRutas(Connection conn) throws SQLException{
            String query = 
            "Select r.id_ruta, count(p.id_pedido) as \"cantidad\" , r.Realizado, r.id_repartidor " +
            "from ruta r join repartidor re On r.id_repartidor = re.cedula " +
            "join pedido p On p.id_ruta = r.id_ruta " +
            "where r.id_jefeBodega = \""+jefe.getId()+"\"  and r.Realizado = \"F\"" +
            "group by r.id_ruta;";
            ResultSet rs = Conexion.getInstance().seleccionarDatos(query, conn);
            return rs;
    }
    
    public Ruta obtenerRuta(ResultSet rs) throws SQLException{
        int id_ruta = rs.getInt("id_ruta");
        int cantidad = rs.getInt("cantidad");
        String realizado = rs.getString("Realizado");
        String id_repartidor = rs.getString("id_repartidor");
        if(realizado.equals("F")){
            realizado = "En proceso";
        }else{
            realizado = "Finalizado";
        }
        Repartidor r = obtenerRepartidor(id_repartidor);
        return new Ruta(id_ruta, jefe, r, realizado, cantidad);
    }
    
    private Repartidor obtenerRepartidor(String cedula) throws SQLException{
        Conexion bd = Conexion.getInstance();
        Connection conn = bd.conectarMySQL();
        String query = 
            "Select r.cedula, p.nombre, p.apellido\n" +
            "From repartidor r \n" +
            "Join Persona p On r.cedula = p.cedula\n" +
            "Where r.cedula = \"" + cedula + "\" ;";
        ResultSet rs = Conexion.getInstance().seleccionarDatos(query, conn);
        Repartidor r = null;
        if(rs.next()){
            r = new Repartidor(rs.getString("nombre"), rs.getString("apellido"), rs.getString("cedula"), 0);
        }
        bd.cerrarConexion(conn);
        return r;
    }
    
    public ResultSet obtenerRSPedidos(Connection conn) throws SQLException{
        String query = 
            "SELECT p.id_pedido, p.id_cliente, p.id_matriz, p.id_vendedor  \n" +
            "FROM pedido p  \n" +
            "Where p.id_ruta is NULL and p.id_jefeBodega = \""+jefe.getId()+"\";";
        ResultSet rs = Conexion.getInstance().seleccionarDatos(query, conn);
        return rs; 
    }
    
    public ResultSet obtenerRSPedidos(Connection conn, int id_ruta) throws SQLException{
        String query = 
            "SELECT p.id_pedido, p.id_cliente, p.id_matriz, p.id_vendedor  \n" +
            "FROM pedido p  \n" +
            "Where p.id_ruta= "+id_ruta+" and p.id_jefeBodega = \""+jefe.getId()+"\";";
        ResultSet rs = Conexion.getInstance().seleccionarDatos(query, conn);
        return rs; 
    }
    
    public Orden obtenerPedido(ResultSet rs) throws SQLException{
        int id_pedido = rs.getInt("id_pedido");
        String id_matriz = rs.getString("id_matriz");
        String id_cliente = rs.getString("id_cliente");
        String id_vendedor = rs.getString("id_vendedor");
        Orden pedido = null;
        if(id_matriz != null){
            pedido = new OrdenMatriz(obtenerMatriz(id_matriz), obtenerVendedor(id_vendedor), id_pedido);
        }else{
            pedido = new OrdenCliente(id_pedido,obtenerCliente(id_cliente), obtenerVendedor(id_vendedor));
        }
        
        return pedido;
    } 
    
    private Cliente obtenerCliente(String cedula) throws SQLException{
        Conexion bd = Conexion.getInstance();
        Connection conn = bd.conectarMySQL();
        String query = 
            "SELECT *\n" +
            "FROM cliente c\n" +
            "JOIN persona p On p.cedula = c.cedula\n" +
            "WHERE p.cedula = \"" + cedula + "\";";
        ResultSet rs = Conexion.getInstance().seleccionarDatos(query, conn);
        Cliente c = null;
        if(rs.next()){
            c = new Cliente(rs.getString("nombre"), rs.getString("apellido"), 
                    rs.getString("cedula"), rs.getString("direccion"), rs.getString("telefono"));
        }
        bd.cerrarConexion(conn);
        return c;
    }
    
    private Matriz obtenerMatriz(String id) throws SQLException{
        Conexion bd = Conexion.getInstance();
        Connection conn = bd.conectarMySQL();
        String query = 
            "SELECT * \n" +
            "FROM matriz m\n" +
            "WHERE m.id_matriz = \"" + id + "\";";
        ResultSet rs = Conexion.getInstance().seleccionarDatos(query, conn);
        Matriz m = null;
        if(rs.next()){
            m = new Matriz(rs.getString("id_matriz"),rs.getString("direccion"),rs.getString("tipoLocalidad"));
        }
        bd.cerrarConexion(conn);
        return m;
    }
    
    private Vendedor obtenerVendedor(String cedula) throws SQLException{
        Conexion bd = Conexion.getInstance();
        Connection conn = bd.conectarMySQL();
        String query = 
            "SELECT * \n" +
            "FROM usuario u\n" +
            "JOIN persona p ON p.cedula = u.cedula\n" +
            "WHERE p.cedula = \"" + cedula + "\";";
        ResultSet rs = Conexion.getInstance().seleccionarDatos(query, conn);
        Vendedor v = null;
        if(rs.next()){
            v = new Vendedor(rs.getString("usuario"), rs.getString("clave"),
                    rs.getBoolean("isAdmin"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("cedula"));
        }
        bd.cerrarConexion(conn);
        return v;
    }
    
    private Repartidor obtenerRepartidor(){
       return (repartidores.isEmpty())? null: repartidores.poll();
    }
    
    public void agregarRepartidor(Repartidor r){
        repartidores.add(r);
    }
    
    private Ruta crearRuta(ObservableList<Orden> pedidos) throws SQLException{
        Repartidor repart = obtenerRepartidor();
        if(repart == null){
            throw new SQLException("No hay repartidores Disponible");
        }
        Ruta r = new Ruta(0, jefe, repart, "F", 0);
        LinkedList<Orden> pedidosL = new LinkedList<>(pedidos);
        r.setPedidos(pedidosL);
        return r;
    }
    
    public void guardarRuta(ObservableList<Orden> pedidos) throws SQLException{
        Ruta r = crearRuta(pedidos);
        insertarRutaBD(r);
        r.setId_ruta(obtenerLastRuta());
        for(Orden p: pedidos){
            asignarRuta(r, p);
        }
    }
    
    private void insertarRutaBD(Ruta r) throws SQLException{
        String query = 
            "INSERT INTO Ruta(id_ruta,Realizado,id_repartidor,id_jefeBodega) VALUES\n" +
            "(default,\"F\",\""+r.getRepartidor().getId()+"\",\""+jefe.getId()+"\")";
        
        Conexion.getInstance().hacerQuery(query); 
    }
    
    private void asignarRuta(Ruta r, Orden p) throws SQLException{
        String query = 
            "UPDATE  pedido \n" +
            "SET id_ruta = " + r.getId_ruta()+"\n" +
            "Where pedido.id_pedido = " + p.getId_pedido() + ";";
        Conexion.getInstance().hacerQuery(query); 
    }
    
    private int obtenerLastRuta() throws SQLException{
        Conexion bd = Conexion.getInstance();
        Connection conn = bd.conectarMySQL();
        String query = 
            "SELECT max(r.id_ruta) as \"id\"\n" +
            "FROM ruta r\n" +
            "WHERE r.id_jefeBodega = \""+jefe.getId()+"\";";
        ResultSet rs = Conexion.getInstance().seleccionarDatos(query, conn);
        int id = 0;
        if(rs.next()){
            id =  rs.getInt("id");
        }else{
            throw new SQLException("Hubo un problema al guardar la Ruta");
        }
        bd.cerrarConexion(conn);
        return id;  
    }
    
    public void obtenerRepartidores() throws SQLException{
        Conexion bd = Conexion.getInstance();
        Connection conn = bd.conectarMySQL();
        String query = 
            "SELECT * \n" +
            "FROM repartidor r\n" +
            "JOIN persona p On r.cedula = p.cedula\n" +
            "WHERE r.cedula NOT IN\n" +
            "	(SELECT r1.id_repartidor\n" +
            "	 FROM  ruta r1  WHERE  r1.Realizado = \"F\");";
        ResultSet rs = Conexion.getInstance().seleccionarDatos(query, conn);
        repartidores = new LinkedList<>();
        while(rs.next()){
            repartidores.add(new Repartidor(rs.getString("nombre"), rs.getString("apellido"), rs.getString("cedula"), 0));
        }
        bd.cerrarConexion(conn);
    }
}
