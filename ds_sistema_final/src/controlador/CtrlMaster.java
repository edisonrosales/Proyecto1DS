package controlador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import Bodega.Jefe;
import Local.Gerente;
import Local.Usuario;
import Local.Vendedor;
import BD.Conexion;

/**
 *
 */
public class CtrlMaster {
    private static Usuario user=null; 
    
    private static Connection validarLogin(String usuario, String password) throws SQLException{
        Conexion bd = Conexion.getInstance();
        bd.setLogIn(usuario, password);
        Connection conn;
        try{
        conn = bd.conectarMySQL();
        }catch(SQLException ex){
            throw new SQLException("Sus credenciales son incorrectas.\nIntente nuevamente.");
        }
        return conn;
    }
    
    public static void buscarUsuario(String username,String password) throws SQLException{
        
        if(validarLogin(username,password)==null){
           throw new SQLException("El usuario o contraseña es incorrecto.");
        }
        ResultSet rs=buscarTipoUsuario();
        int tipo = rs.getInt("TipoUsuario");
        switch(tipo){
            case 1:
                user = new Vendedor(rs.getString("usuario"),rs.getString("clave"),
                rs.getBoolean("isAdmin"),rs.getString("nombre"),rs.getString("apellido"),rs.getString("cedula"));
                break;
            case 2:
                user = new Jefe(rs.getString("usuario"),rs.getString("clave"),
                rs.getBoolean("isAdmin"),rs.getString("nombre"),rs.getString("apellido"),rs.getString("cedula"));
                break;
            case 3:
                user = new Gerente(rs.getString("usuario"),rs.getString("clave"),
                rs.getBoolean("isAdmin"),rs.getString("nombre"),rs.getString("apellido"),rs.getString("cedula"));
                break;
        }
    }
    
    public static ResultSet buscarTipoUsuario() throws SQLException {
        Conexion bd = Conexion.getInstance();
        Connection conn = bd.conectarMySQL();
        String query = "SELECT * FROM Usuario JOIN Persona ON Usuario.cedula= Persona.cedula Where usuario =\""+bd.getUser()+"\";";

        ResultSet rs = bd.seleccionarDatos(query, conn);

        if (rs == null || rs.isClosed() || !rs.next()) {
            throw new SQLException("Usuario no encontrado.\nInténtelo más tarde. ");
        }
        return rs;
    }
    
    public static Usuario getUser(){
        return user;
    }

    
    public static ResultSet llenarTablaProductos() throws SQLException {
        Conexion bd = Conexion.getInstance();
        Connection conn = bd.conectarMySQL();
        String query = "select * from producto";
        ResultSet rs = bd.seleccionarDatos(query, conn);
        return rs;}
    
    


    public static String cambiarPantalla(){
        return null;
    }
}
