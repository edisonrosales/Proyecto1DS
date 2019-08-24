/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ds_sistema_final;

import controlador.CtrlBodegaJ;
import mensajes.notificaciones;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Bodega.Ruta;
import Orden.Orden;
import BD.Conexion;
import static ds_sistema_final.Establecer_ruta.accionDoubleClickTabla;


public class TerminarRuta implements Initializable {

    @FXML
    private ImageView regreso;
    @FXML
    private TableView<Orden> tablaRutasgeneral;
    @FXML
    private TableColumn<Orden,String> idped;
    @FXML
    private TableColumn<Orden,String> dir;
    @FXML
    private TableView<Orden> tablaRutasAsignadas;
    @FXML
    private TableColumn<Orden,String> idpedido;
    @FXML
    private TableColumn<Orden,String> direccion;
    @FXML
    private TextField text;
    private static CtrlBodegaJ control = mainJ.getControlJefe();
    private Ruta ruta;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ruta = Rutas.getRuta();
        agregarColumasRuta();
        try {
            llenar();
        } catch (SQLException ex) {
           notificaciones.mostrarDialogo(ex.getMessage(), "Error de conexión.", "Error");
        }
        accionDoubleClickTabla(tablaRutasAsignadas, tablaRutasgeneral);
        accionDoubleClickTabla(tablaRutasgeneral, tablaRutasAsignadas);
    }    
    
    private void llenar() throws SQLException{
        Conexion bd = Conexion.getInstance();
        Connection conn = bd.conectarMySQL();
        ResultSet rs = control.obtenerRSPedidos(conn, ruta.getId_ruta());
        celdas(conn,rs);
    }
    
    private void celdas(Connection st,ResultSet rs) throws SQLException{
        tablaRutasgeneral.setVisible(true);
        ObservableList<Orden> datos = FXCollections.observableArrayList();
        while (rs.next()) {
            Orden p = control.obtenerPedido(rs);
            datos.add(p);
        }
        tablaRutasgeneral.setItems(datos);
        tablaRutasgeneral.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        Conexion.getInstance().cerrarConexion(st);
     } 
    
    @FXML
    private void regreso(MouseEvent event) {
        
    }

    @FXML
    private void btnGuardar(MouseEvent event) {
        ObservableList<Orden> pedidosCancel =tablaRutasgeneral.getItems();
        ObservableList<Orden> pedidosConfirm =tablaRutasAsignadas.getItems();
        String confirm = "¿Desea guardar los cambios?\nLas rutas no seleccionadas regresarán a pedidos por enviar."; 
        
        if(notificaciones.comfirm(confirm)){
            try{
                String texto = text.getText();
                boolean isFinalizada = (texto.equals("") && !pedidosConfirm.isEmpty()) || (!pedidosCancel.isEmpty() && !texto.equals(""));
                if(!pedidosCancel.isEmpty() && !texto.equals("")){
                    for(Orden p : pedidosCancel){
                    cancelarPedidoBD(p);
                    }
                    actualizarObservaciones();
                }if(isFinalizada){
                    finalizarRuta();
                    control.obtenerRepartidores();
                    Rutas.setRuta(null);
                    Rutas.getController().llenar();
                    if(Canvas.getPantalla() != null)
                        Canvas.getPantalla().llenar();
                    Stage stage = (Stage) tablaRutasAsignadas.getScene().getWindow();
                    stage.close();
                }else{
                    notificaciones.mostrarDialogo("Si hubieron inconvenientes con los pedidos deben ser escritos en observaciones.",
                                "", "Faltan Campos por llenar");
                }
            } catch (SQLException ex) {
                notificaciones.mostrarDialogo("No se pudo guardar los cambios.","Error de guardado", "Error");
            }
        }
    }
    
    private void cancelarPedidoBD(Orden p) throws SQLException{
        String query = 
            "UPDATE pedido SET id_ruta = NULL\n" +
            "WHERE id_pedido = " + p.getId_pedido() + ";";
        
        Conexion.getInstance().hacerQuery(query); 
    }
    
    private void actualizarObservaciones() throws SQLException{
        String query = 
            "UPDATE ruta SET observaciones = \""+text.getText()+"\"\n" +
            "WHERE id_ruta = "+ ruta.getId_ruta()+";";
        
        Conexion.getInstance().hacerQuery(query); 
    }
    
    private void finalizarRuta() throws SQLException{
        String query = 
            "UPDATE ruta SET Realizado = \"V\"\n" +
            "WHERE id_ruta = "+ ruta.getId_ruta()+";";
        
        Conexion.getInstance().hacerQuery(query); 
    }
    
    @FXML
    private void btnCancelar(MouseEvent event) {
        Rutas.setRuta(null);
        Stage stage = (Stage) tablaRutasAsignadas.getScene().getWindow();
        stage.close();
    }
    
    private void agregarColumasRuta(){
        idped.setCellValueFactory(new PropertyValueFactory<>("id_pedido"));
        dir.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        idpedido.setCellValueFactory(new PropertyValueFactory<>("id_pedido"));
        direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
    }
    
}
