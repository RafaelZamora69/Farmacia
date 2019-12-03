package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

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
    private JFXButton BtnReportes;

    @FXML
    private AnchorPane Content;

    public static String Nombre, Jerarquia;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(Jerarquia.equals("Vendedor")){
            this.BtnInventario.setDisable(true);
            this.BtnExtras.setDisable(true);
            this.BtnReportes.setDisable(true);
        } else if(Jerarquia.equals("Inventario")){
            this.BtnCaja.setDisable(true);
        }
        SetNombre();
    }

    public void AbrirExtras(MouseEvent mouseEvent) throws IOException {
        Parent pane = FXMLLoader.load(getClass().getResource("Extras.fxml"));
        this.Content.getChildren().setAll(pane);
    }

    public void AbrirCaja(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("CajaRegistradora.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void AbrirInventario(MouseEvent mouseEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("Inventario.fxml"));
        this.Content.getChildren().setAll(pane);
    }

    public void Cerrar(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void AbrirReporte(MouseEvent mouseEvent) throws IOException {
        Pane pane = FXMLLoader.load(getClass().getResource("Reportes.fxml"));
        this.Content.getChildren().setAll(pane);
    }

    public void SetNombre(){
        this.LblName.setText(Nombre);
    }
}
