/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Local.Gerente;
import Local.Persona;
import BD.Conexion;


public class CtrlAdm {
    
    private final Gerente gerente;
    
    public CtrlAdm(Gerente jefe){
        this.gerente = jefe;
    }
    public void AssignarAdministrador(String cedula,boolean asignar) throws SQLException{
        Conexion bd = Conexion.getInstance();
        Connection conn = bd.conectarMySQL();
        Statement st = conn.createStatement();
        if(asignar){
        String query="update usuario set usuario.isAdmin='1' WHERE (usuario.cedula=\""+cedula+"\") ";
        st.execute(query);
        }else{
        String query="update usuario set usuario.isAdmin='0'WHERE (usuario.cedula=\""+cedula+"\") ";
        st.execute(query);
        }
       
    }
    
    public  ResultSet UsuarioByLocalidad() throws SQLException{
        Conexion bd = Conexion.getInstance();
        Connection conn = bd.conectarMySQL();
        ResultSet resul=CtrlMaster.buscarTipoUsuario();
        String query = "SELECT Persona.cedula,Persona.nombre,Persona.apellido "
                + "FROM Usuario JOIN Persona On Persona.cedula=Usuario.cedula  Where matriz_id= \""+resul.getString("matriz_id")+"\"";
        ResultSet rs = Conexion.getInstance().seleccionarDatos(query, conn);
        if (rs == null || rs.isClosed() || !rs.next()) {
            throw new SQLException("Usuario no encontrado.\nInténtelo más tarde. ");
        }
        return rs;
    }
    
     public Persona obtenerPersona(ResultSet rs) throws SQLException{
         Persona persona=null;
        if (!rs.getString("cedula").equalsIgnoreCase("0")) {
        String nombre = rs.getString("nombre");
        String apellido = rs.getString("apellido");
        String cedula = rs.getString("cedula");
        persona=new Persona(nombre,apellido,cedula);
        }
        return persona;
    }
    
     public void peticionAbastecimiento(){
         
     }
}
