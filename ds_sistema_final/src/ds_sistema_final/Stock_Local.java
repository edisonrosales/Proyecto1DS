
package ds_sistema_final;

import controlador.CtrlMaster;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import Inventario.ad_producto;
import Inventario.Matriz;
import Inventario.Producto;
import Local.Usuario;
import BD.Conexion;

/**
 * FXML Controller class
 *
 */
public class Stock_Local implements Initializable {
    Conexion bd = Conexion.getInstance();
    Connection conn = null;
    String query;
    @FXML
    private Label fechaactual;
    @FXML
    private Label empleado;
     @FXML
    private ImageView insertar;
    @FXML
    private ImageView act;
    @FXML
    private ImageView regreso;
    @FXML
    private TextField busqueda;
    @FXML
    private ComboBox<?> ComboLugar;
    @FXML
    private ComboBox idenlocal;
    @FXML
    private TableView<ad_producto> tablaStock;
    @FXML
    private TableColumn<String, ad_producto> idpro;
    @FXML
    private TableColumn<String, ad_producto> nombrePro;
    @FXML
    private TableColumn<String, ad_producto> stock;
    @FXML
    private TableColumn<String, ad_producto> idlocal;
    @FXML
    private TableColumn<String, ad_producto> direccion;
    @FXML
    private TableColumn<String, ad_producto> lugar;
     @FXML
    private Label lblstock;
    @FXML
    private TextField txtstock;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        busqueda.setDisable(true);
         insertar.setVisible(false);
         act.setVisible(false);
        Calendar calendar = GregorianCalendar.getInstance();
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("     dd/MM/yyyy");
        ocultar();
        try {
            llenar();
        } catch (SQLException ex) {
            Logger.getLogger(Stock_Local.class.getName()).log(Level.SEVERE, null, ex);
        }
        setCenter();
        fechaactual.setText(sdf.format(date));
        Usuario user = CtrlMaster.getUser();
        empleado.setText(user.getNombre() + " " + user.getApellido());
    }

        private void ocultar(){
    
         lblstock.setVisible(false);
        txtstock.setVisible(false);

    }
        
         private void mostrar(){

         lblstock.setVisible(true);
        txtstock.setVisible(true);
        
    }
    public void llenar() throws SQLException {
        conn = bd.conectarMySQL();
        String query = "select p.id_producto,p.nombre,s.Stock,m.tipoLocalidad,m.direccion,m.id_matriz\n"
                + "from producto  p\n"
                + "join stock s on p.id_producto=s.id_producto\n"
                + "join matriz m on m.id_matriz=s.id_matriz ;";
        ResultSet rs = bd.seleccionarDatos(query, conn);
        llenartable(conn, rs);
    }

    public void llenarMa() throws SQLException {
        conn = bd.conectarMySQL();
        String query = "select p.id_producto,p.nombre,s.Stock,m.tipoLocalidad,m.direccion,m.id_matriz\n"
                + "from producto  p\n"
                + "join stock s on p.id_producto=s.id_producto\n"
                + "join matriz m on m.id_matriz=s.id_matriz\n"
                + "where tipoLocalidad=\"Matriz\";";
        ResultSet rs = bd.seleccionarDatos(query, conn);
        llenartable(conn, rs);
    }

    public void llenarSu() throws SQLException {
        conn = bd.conectarMySQL();
        String query = "select p.id_producto,p.nombre,s.Stock,m.tipoLocalidad,m.direccion,m.id_matriz\n"
                + "from producto  p\n"
                + "join stock s on p.id_producto=s.id_producto\n"
                + "join matriz m on m.id_matriz=s.id_matriz\n"
                + "where tipoLocalidad=\"Sucursal\";";
        ResultSet rs = bd.seleccionarDatos(query, conn);
        llenartable(conn, rs);
    }

    public void llenarBo() throws SQLException {
        conn = bd.conectarMySQL();
        String query = "select p.id_producto,p.nombre,s.Stock,m.tipoLocalidad,m.direccion,m.id_matriz\n"
                + "from producto  p\n"
                + "join stock s on p.id_producto=s.id_producto\n"
                + "join matriz m on m.id_matriz=s.id_matriz\n"
                + "where tipoLocalidad=\"Bodega\";";
        ResultSet rs = bd.seleccionarDatos(query, conn);
        llenartable(conn, rs);
    }

    public Object[] poblar_combox(String tabla, String nombrecol) {
        int registros = 0;
        try {
            conn = bd.conectarMySQL();
            String sql = "SELECT count(*) as total FROM " + tabla;
            ResultSet rs = bd.seleccionarDatos(sql, conn);
            rs.next();
            registros = rs.getInt("total");
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

        Object[] datos = new Object[registros];
        try {
            conn = bd.conectarMySQL();
            ResultSet rs = bd.seleccionarDatos("SELECT id_matriz FROM matriz where tipoLocalidad='"+ComboLugar.getValue().toString()+"'", conn);
            int i = 0;
            while (rs.next()) {
                datos[i] = rs.getObject(nombrecol);
                i++;
            }
            rs.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return datos;
    }

    public void llenartable(Connection conn, ResultSet rs) {
        nombrePro.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        stock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        idlocal.setCellValueFactory(new PropertyValueFactory<>("tipoLocalidad"));
        direccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        lugar.setCellValueFactory(new PropertyValueFactory<>("id_matriz"));
        idpro.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
        tablaStock.setVisible(true);
        celdas(conn, rs);
    }

    private void celdas(Connection st, ResultSet rs) {
        tablaStock.setVisible(true);
        try {
            ObservableList<ad_producto> datos = FXCollections.observableArrayList();
            while (rs.next()) {
                if (!rs.getString("nombre").equalsIgnoreCase("0")) {
                    String nom = rs.getString("nombre");
                    String sto = rs.getString("Stock");
                    String tloc = rs.getString("tipoLocalidad");
                    String dir = rs.getString("direccion");
                    String lug = rs.getString("id_matriz");
                    String idprod = rs.getString("id_producto");
                    ad_producto dv = new ad_producto(nom, tloc, Integer.parseInt(sto), dir, lug,Integer.parseInt(idprod));
                    datos.add(dv);
                }
            }
            tablaStock.setItems(datos);
            tablaStock.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        } catch (SQLException ex) {
            Logger.getLogger(Producto_F.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setCentercopy() throws SQLException {
        
        idenlocal.setOnAction((l) -> {
            insertar.setVisible(true);
            act.setVisible(true);
            busqueda.setDisable(false);
            Object o = idenlocal.getValue();
            if(o != null){
            String numlocal = o.toString();
            ResultSet rs = null;
            if (numlocal != null && !numlocal.equals("") && !numlocal.equals(" ")) {
                try {
                    conn = bd.conectarMySQL();
                } catch (SQLException ex) {
                    Logger.getLogger(Stock_Local.class.getName()).log(Level.SEVERE, null, ex);
                }
                query = "select p.id_producto,p.nombre,s.Stock,m.tipoLocalidad,m.direccion,m.id_matriz\n"
                        + "from producto  p\n"
                        + "join stock s on p.id_producto=s.id_producto\n"
                        + "join matriz m on m.id_matriz=s.id_matriz\n"
                        + "where m.tipoLocalidad='"+ ComboLugar.getValue().toString()+"' and  m.id_matriz='" + numlocal + "';";
                try {
                    rs = bd.seleccionarDatos(query, conn);
                } catch (SQLException ex) {
                    System.out.println("Aqui?");
                    Logger.getLogger(Stock_Local.class.getName()).log(Level.SEVERE, null, ex);
                }
                llenartable(conn, rs);
            }
            }

        });

        busqueda.textProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue args0, Object o1, Object o2) {
                String comboText = (String) idenlocal.getValue();
                if (comboText != null && !comboText.equals("") && !comboText.equals(" ")) {
                    String stringActual = (String) o2;
                    String numlocal = idenlocal.getValue().toString();
                    String stbuscar = "";
                    ResultSet rss = null;
                    try {
                        conn = bd.conectarMySQL();
                    } catch (SQLException ex) {
                        Logger.getLogger(Stock_Local.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    stbuscar = "select p.id_producto,p.nombre,s.Stock,m.tipoLocalidad,m.direccion,m.id_matriz\n"
                            + "from producto  p\n"
                            + "join stock s on p.id_producto=s.id_producto\n"
                            + "join matriz m on m.id_matriz=s.id_matriz \n"
                            + "where m.tipoLocalidad='"+ ComboLugar.getValue().toString()+"' and m.id_matriz='"+ idenlocal.getValue().toString()+"' and p.nombre like " + " \'" + busqueda.getText() + "%\' ;";
                    try {
                        rss = bd.seleccionarDatos(stbuscar, conn);
                    } catch (SQLException ex) {
                        Logger.getLogger(Stock_Local.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    llenartable(conn, rss);
                }

            }
        });
    }

    public void setCenter() {
        busqueda.setPromptText("Ingrese su búsqueda");
        ObservableList ob = FXCollections.observableArrayList("Matriz", "Sucursal", "Bodega");
        ComboLugar.setItems(ob);
        ComboLugar.setPromptText("Filtrar");
        ComboLugar.setOnAction((l) -> {
            Connection st = null;
            ResultSet rs = null;
            String stbuscar = "";
            idenlocal.getItems().clear();
            if (((String) ComboLugar.getValue()).equals("Matriz")) {
                busqueda.setPromptText("Matriz");
                try {
                    llenarMa();
                } catch (SQLException ex) {
                    Logger.getLogger(Stock_Local.class.getName()).log(Level.SEVERE, null, ex);
                }
                com();
            } else if (((String) ComboLugar.getValue()).equals("Sucursal")) {
                busqueda.setPromptText("Sucursal");
                try {
                    llenarSu();
                } catch (SQLException ex) {
                    Logger.getLogger(Stock_Local.class.getName()).log(Level.SEVERE, null, ex);
                }
                com();
            } else if (((String) ComboLugar.getValue()).equals("Bodega")) {
                busqueda.setPromptText("Bodega");
                try {
                    llenarBo();
                } catch (SQLException ex) {
                    Logger.getLogger(Stock_Local.class.getName()).log(Level.SEVERE, null, ex);
                }
                com();}
                 else {
                busqueda.setPromptText("Ingrese su búsqueda");
            }
    });}

    public void com(){
        Object[] idarticulo = poblar_combox("matriz", "id_matriz");
                for (int i = 0; i < idarticulo.length; i++) {
                    idenlocal.getItems().add(idarticulo[i]);
                }
                try {
                    setCentercopy();
                } catch (SQLException ex) {
                    Logger.getLogger(Stock_Local.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
  
    @FXML
    private void regreso(MouseEvent event) {
    }

    @FXML
    private void Modificar(MouseEvent event) {
        if(CtrlMaster.getUser().isIsAdmin()){
            try{
        mostrar();
        ad_producto p = tablaStock.getSelectionModel().getSelectedItem();
        txtstock.setText(String.valueOf(p.getStock()));  
        }catch (Exception e) {
                    ocultar();
                          Alert mensajeExp = new Alert(Alert.AlertType.CONFIRMATION);
        mensajeExp.setHeaderText("Diálogo de confirmación");
        mensajeExp.setContentText ("No has seleccionado ninguna celda");
        mensajeExp.showAndWait();
                }
        }
        
    }

    
      @FXML
    private void actualizar(MouseEvent event) throws SQLException {
        if(CtrlMaster.getUser().isIsAdmin()){
            ad_producto p = tablaStock.getSelectionModel().getSelectedItem();
        String modify= "update stock set stock= '" + txtstock.getText() 
                + "' where id_producto= '" + p.getId_producto() + "' and id_matriz='"+p.getId_matriz()+"' ; ";
        Conexion bd = Conexion.getInstance();
                    Connection conn = bd.conectarMySQL();
        Statement st = conn.createStatement();
            st.execute(modify);
        String show = "select p.id_producto,p.nombre,s.Stock,m.tipoLocalidad,m.direccion,m.id_matriz\n"
                + "from producto  p\n"
                + "join stock s on p.id_producto=s.id_producto\n"
                + "join matriz m on m.id_matriz=s.id_matriz\n"
                + "where m.tipoLocalidad='"+ComboLugar.getValue().toString()+"' and m.id_matriz='"+ idenlocal.getValue().toString()+"' ;";
        Connection connn = bd.conectarMySQL();
        ResultSet rs = bd.seleccionarDatos(show, connn);
        celdas(conn,rs);
            ocultar();
    }
        }
    
    
   
        
    
    
}
