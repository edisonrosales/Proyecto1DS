/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mensajes;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Francisco
 */
public class notificaciones {
    
    public static void mostrarDialogo(String info, String cabecera, String titulo) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(info);
        alert.setHeaderText(cabecera);
        alert.setTitle(titulo);
        alert.showAndWait();
    }
    
    public static boolean comfirm(String mensaje){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setContentText(mensaje);

        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent())
            return (result.get() == ButtonType.OK); 
        return false;
    }
}

