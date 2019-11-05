package sample;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;

public class CajaRegistradoraController extends Application {
    @FXML
    private JFXTreeTableView<?> tablaVentas;


    @FXML
    private ImageView imgLogo;

    @FXML
    private ImageView imgBuscar;

    @FXML
    void buscar(MouseEvent event) {

    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage s = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("CajaRegistradora.fxml"));
        s.setResizable(false);
        s.setTitle("");
        s.setScene(new Scene(root));
        s.show();

    }


    public void buscar(javafx.event.ActionEvent actionEvent) {

    }
}
