package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.awt.event.ActionEvent;

public class Alertas {

    public static void MostrarAlerta(String msg, StackPane stackPane){
        JFXDialogLayout Contenido = new JFXDialogLayout();
        Contenido.setHeading(new Text("Aviso"));
        Contenido.setBody(new Text(msg));
        JFXDialog Alerta = new JFXDialog(stackPane, Contenido, JFXDialog.DialogTransition.CENTER);
        JFXButton button = new JFXButton("Aceptar");
        button.setOnAction((ActionEvent) -> {
            Alerta.close();
        });
        Contenido.setActions(button);
        Alerta.show();
    }
}
