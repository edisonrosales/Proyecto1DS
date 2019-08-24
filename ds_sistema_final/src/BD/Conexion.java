package BD;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String DB = "tecnoimport";
    private final String HOST = "localhost";
    private final String PUERTO = "3306";

    private final String URL = "jdbc:mysql://" + HOST + ":" + PUERTO + "/" + DB
            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";;
    
    private String user;
    private String pass;
    private static Conexion conexion = new Conexion();
    
    private Conexion(){
    }
    
    public static Conexion getInstance(){
        return conexion;
    }

    public void setLogIn(String user,String pass) {
        this.user = user;
        this.pass = pass;
    }

    public static Conexion getConexion() {
        return conexion;
    }

    public static void setConexion(Conexion conexion) {
        conexion.conexion = conexion;
    }
    
    
    public Connection conectarMySQL() throws SQLException {
        Connection conn;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, user,pass);
            return conn;
        } catch (ClassNotFoundException ex) {
            throw new SQLException("Error de Base de Datos.\nNo conectada");
        }
    }

    public ResultSet seleccionarDatos(String query, Connection conn) throws SQLException {
        if (conn == null) {
            throw new SQLException("Conexión con la base de datos fallida.\n Compruebe autentificación o"
                    + "Driver de conexión.");
        }
        //create the java statement
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
        } catch (SQLException ex) {
            throw new SQLException("La base de datos se desconectó inesperadamente.");
        }

        try {
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            throw new SQLException("la Consulta no fue exitosa.\nInténtelo más tarde. " + ex.getMessage());
        }
        return rs;
    }
    
    public void cerrarConexion(Connection conn) throws SQLException {
        try {
            conn.close();
        } catch (SQLException ex) {
            throw new SQLException("Fallo al cerrar conexión a base de datos");
        }
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
    
    public void hacerQuery(String query) throws SQLException{
        Conexion bd = conexion.getInstance();
        try(Connection conn = bd.conectarMySQL()){
            try(CallableStatement cl = conn.prepareCall(query)) {
                cl.execute();
            } 
            bd.cerrarConexion(conn); 
        } catch (SQLException ex) {
            throw new SQLException("No se pudo realizar la acción");
        }
    }
}
