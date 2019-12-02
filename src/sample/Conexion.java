package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import tray.notification.NotificationType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;

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

    static String user, password;
    static Connection ConexionTransaction;

    public  static Connection getConnection(String User, String Password) throws SQLException{
        Connection conexion = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Farmacia?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", User, Password);
        user = User;
        password = Password;
        return conexion;
    }

    public void Conectar(MouseEvent mouseEvent) {
        user = this.TxtUser.getText();
        password = this.TxtPass.getText();
        try {
            Connection con = this.getConnection(user, password);
            Alertas.MostrarAlerta("Conectado a la base de datos", NotificationType.SUCCESS, "Exito");
            GuardarConfiguracion();
        } catch (SQLException | IOException e) {
            Alertas.MostrarAlerta("Error al conectarse", NotificationType.ERROR, "Error");
            e.printStackTrace();
        }
    }

    public static Connection getConnection()  {
        try{
            Connection conexion = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Farmacia?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
            return conexion;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Connection GetConexionPromocion() {
        try {
            Connection conexion = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Farmacia?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user, password);
            conexion.setAutoCommit(false);
            return conexion;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void Cerrar(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void GuardarConfiguracion() throws IOException {
        File archivo = new File("Configuracion.txt");
        archivo.delete();
        FileWriter escribir = new FileWriter(archivo, true);
        archivo.createNewFile();
        escribir.write(user + "\r\n");
        escribir.write(password);
        escribir.close();
    }
}
