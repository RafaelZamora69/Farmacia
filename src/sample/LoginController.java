package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tray.notification.NotificationType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.security.Principal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;

    @FXML
    private JFXTextField TxtUser;

    @FXML
    private JFXPasswordField TxtPass;

    @FXML
    private JFXButton BtnEntrar;

    @FXML
    private Label LblTitle;

    @FXML
    private JFXButton BtnSalir;

    @FXML
    private JFXButton BtnAjustes;

    @FXML
    private Label lblStatus;

    String [] datos = new String[2];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            LeerConfiguracion();
            Conexion.getConnection(datos[0], datos[1]);
            this.lblStatus.setStyle("-fx-text-fill: #1b5e20");
            this.lblStatus.setText("Conectado");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            this.lblStatus.setStyle("-fx-text-fill: #d50000");
            this.lblStatus.setText("Desconectado");
        }
    }

    public void Ingresar(javafx.scene.input.MouseEvent mouseEvent) {
        Validar();
    }

    public void Salir(MouseEvent mouseEvent){
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void AbrirAjustes(MouseEvent mouseEvent) throws IOException {
        Stage Main = new Stage();
        Parent Mroot = FXMLLoader.load(getClass().getResource("Conexion.fxml"));
        Scene scene = new Scene(Mroot);
        Main.setScene(scene);
        Main.setResizable(false);
        Main.show();
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void LeerConfiguracion() throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader("Configuracion.txt"));
        String sScadena;
        int i = 0;
        while ((sScadena = bf.readLine()) != null){
            datos[i] = sScadena;
            i++;
        }
    }

    public void Validar(){
        try{
            Connection con = Conexion.getConnection();
            PreparedStatement statement;
            statement = con.prepareStatement("select Nombre, Jerarquia from Empleado where Usuario = ? and Password = sha1(?)");
            statement.setString(1, this.TxtUser.getText());
            statement.setString(2, this.TxtPass.getText());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                //Cosas je
                PrincipalController.Nombre = rs.getString(1);
                PrincipalController.Jerarquia = rs.getString(2);
                Stage Main = new Stage();
                Parent Mroot = FXMLLoader.load(getClass().getResource("Principal.fxml"));
                Scene scene = new Scene(Mroot);
                Main.setScene(scene);
                Main.setResizable(true);
                Main.initStyle(StageStyle.UNDECORATED);
                Main.show();
                Stage stage = (Stage) TxtUser.getScene().getWindow();
                stage.close();
                return;
            }
            Alertas.MostrarAlerta("Usuario/contraseña incorrecto", NotificationType.WARNING, "Alerta");
        } catch (SQLException | IOException ex){
            Alertas.MostrarAlerta("Error al conectarse a la base de datos", NotificationType.ERROR, "Error");
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void IngresarT(KeyEvent event) {
        if(event.getCode().equals(KeyCode.ENTER)){
            if("".equals(this.TxtPass.getText()) || "".equals(this.TxtUser.getText())){
            } else {
                Validar();
            }
        }
    }
}
