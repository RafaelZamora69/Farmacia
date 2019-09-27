package Objetos;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Conexion {

    @FXML
    private StackPane StackPane;

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTextField TxtUser;

    @FXML
    private JFXPasswordField TxtPass;

    @FXML
    private Label LblTitle;

    @FXML
    private JFXButton BtnConectar;

    @FXML
    private JFXButton BtnCerrar;

    public static Connection getConnection() throws SQLException{
        Connection conexion = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Farmacia?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root1234");
        return conexion;
    }

    public void Conectar(MouseEvent mouseEvent) {
    }

    public void Cerrar(MouseEvent mouseEvent) throws IOException {
        Stage Main = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        Main.setResizable(false);
        Main.initStyle(StageStyle.UNDECORATED);
        Main.setScene(scene);
        Main.setResizable(false);
        Main.show();
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }
}
