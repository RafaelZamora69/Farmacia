package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class FormaDePagoController extends Application implements Initializable {
    public FormaDePagoController(){

    }
    public void finalizarVenta(MouseEvent mouseEvent) {
    }

    public void cerrarVentana(MouseEvent mouseEvent) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
            Stage s = new Stage();
            Parent r = FXMLLoader.load(getClass().getResource("FormaDePago.fxml"));
            Scene scene = new Scene(r);
            s.setResizable(false);
            s.setTitle("");
            s.setScene(scene);
            s.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}