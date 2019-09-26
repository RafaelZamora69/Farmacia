package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class PrincipalController {

    @FXML
    private Label Lbl;

    @FXML
    private Label LblName;

    @FXML
    private AnchorPane root;

    @FXML
    private AnchorPane top;

    @FXML
    private AnchorPane left;

    @FXML
    private JFXButton BtnExtras;

    @FXML
    private JFXButton BtnCaja;

    @FXML
    private JFXButton BtnInventario;

    @FXML
    private JFXButton BtnCerrar;

    @FXML
    private AnchorPane Content;

    public void AbrirExtras(MouseEvent mouseEvent) {
    }

    public void AbrirCaja(MouseEvent mouseEvent) {
    }

    public void AbrirInventario(MouseEvent mouseEvent) {
    }

    public void Cerrar(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void SetNombre(String nombre){
        this.LblName.setText(nombre);
    }
}
