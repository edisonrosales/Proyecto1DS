/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds_sistema_final;

import controlador.CtrlBodegaJ;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import Bodega.Ruta;
import BD.Conexion;

/**
 * FXML Controller class
 *
 */
public class Canvas implements Initializable {

    @FXML
    private GridPane gridd;
    @FXML
    private ListView<String> CodRepartidor;
    @FXML
    private ListView<Integer> CodRuta;
    @FXML
    private ImageView imagenc;
    
    private CtrlBodegaJ control;
    
    private static Canvas pantalla = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pantalla = this;
        control = mainJ.getControlJefe();
        try {
            llenar();
        } catch (SQLException ex) {
            Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void llenar() throws SQLException{
        CodRepartidor.getItems().clear();
        CodRuta.getItems().clear();
        Conexion bd = Conexion.getInstance();
        Connection conn = bd.conectarMySQL();
        ResultSet rs = control.obtenerRSRutas(conn);
        while(rs.next()){
            Ruta r = control.obtenerRuta(rs);
            CodRepartidor.getItems().add(r.getRepartidor().getId());
            CodRuta.getItems().add(r.getId_ruta());
        }
    }
    
    public static Canvas getPantalla(){
        return pantalla;
    }
    
    @FXML
    private void btnVolver(MouseEvent event) {
        
    }
    
}
