package ds_sistema_final;

import ds_sistema_final.Canvas;
import controlador.CtrlBodegaJ;
import mensajes.notificaciones;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import BD.Conexion;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Orden.Orden;

/**
 * FXML Controller class
 *
 */
public class Establecer_ruta implements Initializable {

    @FXML
    private TableView<Orden> tablaRutasgeneral;
    
    @FXML
    private TableColumn<Orden, String> idped;
    @FXML
    private TableColumn<Orden, String> dir;
    @FXML
    private TableView<Orden> tablaRutasAsignadas;
    @FXML
    private TableColumn<Orden, String> idpedido;
    @FXML
    private TableColumn<Orden, String> direccion;
    
    private ObservableList<String> data;
    
    private static CtrlBodegaJ control = mainJ.getControlJefe();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        ResultSet rs = control.obtenerRSPedidos(conn);
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
    
    private void agregarColumasRuta(){
        idped.setCellValueFactory(new PropertyValueFactory<>("id_pedido"));
        dir.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        idpedido.setCellValueFactory(new PropertyValueFactory<>("id_pedido"));
        direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
    }
    
    public static void accionDoubleClickTabla( TableView<Orden> eliminado,  TableView<Orden> agregado){
        eliminado.setRowFactory(tv -> {
            TableRow<Orden> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
                     && event.getClickCount() == 1) {

                    Orden clickedRow = row.getItem();
                    agregado.getItems().add(clickedRow);
                    eliminado.getItems().remove(clickedRow);
                }
            });
            return row ;
        });
    }
    
    @FXML
    private void regreso(MouseEvent event) {
    }
    
     @FXML
    private void btnGuardar (MouseEvent event) {
        ObservableList<Orden> pedidos =tablaRutasAsignadas.getItems();
        if(pedidos.isEmpty()){
            notificaciones.mostrarDialogo("Debe seleccionar al menos un pedido para crear una ruta.",
                    "No hay Pedidos Asignados.", "Aviso");
        }else if(notificaciones.comfirm("¿Desea guardar los cambios?")){
            try {
                control.guardarRuta(pedidos);
                //TODO: datos del repartidor
                notificaciones.mostrarDialogo("", "Se ha guardado correctamente", "Aviso");
                Stage stage = (Stage) tablaRutasAsignadas.getScene().getWindow();
                Rutas.getController().llenar();
                stage.close();
                if(Canvas.getPantalla() != null){
                   Canvas.getPantalla().llenar();
                }
            } catch (SQLException ex) {
                notificaciones.mostrarDialogo(ex.getMessage(), "Error de Guardado", "Error");
            }
        }
    }
    
     @FXML
    private void btnCancelar (MouseEvent event) {
        Stage stage = (Stage) tablaRutasAsignadas.getScene().getWindow();
        stage.close();
    }
}
