package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import tray.notification.NotificationType;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ExtrasController implements Initializable {

    @FXML
    private JFXPasswordField EPassEmp;

    @FXML
    private JFXTextField BuscarEmp;

    @FXML
    private JFXTextField ENombreEmp;

    @FXML
    private JFXTextField ETelEmp;

    @FXML
    private JFXTextField EusuEmp;

    @FXML
    private JFXComboBox<String> EPuestoEmp;

    @FXML
    private JFXPasswordField EValidarPass;

    @FXML
    private JFXTreeTableView<Empleado> TableEmpleados;

    @FXML
    private TreeTableColumn<?, ?> IDEmpleado;

    @FXML
    private TreeTableColumn<?, ?> NombreEmpleado;

    @FXML
    private TreeTableColumn<?, ?> TelefonoEmpleado;

    @FXML
    private TreeTableColumn<?, ?> PuestoEmpleado;

    @FXML
    private JFXTextField NNombreEmp;

    @FXML
    private JFXTextField NTelEmp;

    @FXML
    private JFXTextField NUsuEmp;

    @FXML
    private JFXPasswordField NPassEmp;

    @FXML
    private JFXPasswordField NValidarPass;

    @FXML
    private JFXComboBox<String> NPuestoEmp;

    @FXML
    private Label ValidarPass;

    @FXML
    private Label ValidarPass2;

    @FXML
    private JFXButton BtnAgregar;

    @FXML
    private JFXButton BtnActualizar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CargarComboBox();
    }

    private void CargarComboBox(){
        try {
            Connection con = Conexion.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select distinct Jerarquia from Empleado;");
            while(rs.next()){
                this.EPuestoEmp.getItems().add(rs.getString(1));
                this.NPuestoEmp.getItems().add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void BuscarEmpleado(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(!"".equals(this.BuscarEmp.getText())){
                try {
                    ValidarPass.setText("");
                    Connection con = Conexion.getConnection();
                    PreparedStatement statement = con.prepareStatement("select Nombre, Telefono, Usuario, Jerarquia from Empleado where idEmpleado = ?;");
                    statement.setString(1, this.BuscarEmp.getText());
                    ResultSet rs = statement.executeQuery();
                    while(rs.next()){
                        this.ENombreEmp.setText(rs.getString(1));
                        this.ETelEmp.setText(rs.getString(2));
                        this.EusuEmp.setText(rs.getString(3));
                        this.EPuestoEmp.setValue(rs.getString(4));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void InsertarEmpleado(MouseEvent mouseEvent) {
        try {
            Connection con = Conexion.getConnection();
            PreparedStatement statement = con.prepareStatement("insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values (?, ?, ?, sha1(?), ?)");
            statement.setString(1, NNombreEmp.getText());
            statement.setString(2, NTelEmp.getText());
            statement.setString(3, NUsuEmp.getText());
            if(this.ValidarPass2.getText().equals("Correcto")){
                statement.setString(4, NPassEmp.getText());
            } else {
                Alertas.MostrarAlerta("Las contraseñas no coinciden", NotificationType.ERROR, "Error!");
                throw new SQLException();
            }
            statement.setString(5, NPuestoEmp.getValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void ActualizarEmpleado(MouseEvent mouseEvent) {
        try {
            Connection con = Conexion.getConnection();
            PreparedStatement statement;
            if(ValidarPass.getText().equals("")){
                statement = con.prepareStatement("update Empleado set Nombre = ?, Telefono = ?, Puesto = ?;");
                statement.setString(1, ENombreEmp.getText());
                statement.setString(2, ETelEmp.getText());
                statement.setString(3, EPuestoEmp.getValue());
                statement.executeUpdate();
                Alertas.MostrarAlerta("Empleado actualizado", NotificationType.SUCCESS, "Correcto");
            } else {
                statement = con.prepareStatement("update Empleado set Nombre = ?, Telefono = ?, Puesto = ?, Password = sha1(?);");
                statement.setString(1, ENombreEmp.getText());
                statement.setString(2, ETelEmp.getText());
                statement.setString(3, EPuestoEmp.getValue());
                if(ValidarPass.getText().equals("Correcto")){
                    statement.setString(4, EPassEmp.getText());
                } else {
                    Alertas.MostrarAlerta("Las contraseñas no coinciden", NotificationType.ERROR, "Error!");
                    throw new SQLException();
                }
                statement.executeUpdate();
                Alertas.MostrarAlerta("Empleado actualizado", NotificationType.SUCCESS, "Correcto");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ValidarPass(KeyEvent keyEvent) {
        if (keyEvent.getSource().equals(NPassEmp)){
            if(this.NValidarPass.getText().equals(this.NPassEmp.getText()) && this.NPassEmp.getText().equals(this.NValidarPass.getText())){
                this.ValidarPass2.setText("Correcto");
            } else {
                this.ValidarPass2.setText("No coinciden");
            }
        } else if (keyEvent.getSource().equals(EPassEmp)){
            if(this.EValidarPass.getText().equals(this.EPassEmp.getText()) && this.EPassEmp.getText().equals(this.EValidarPass.getText())){
                this.ValidarPass.setText("Correcto");
            } else {
                this.ValidarPass.setText("No coinciden");
            }
        }
    }

    class Empleado extends RecursiveTreeObject<Empleado> {
        StringProperty ID, Nombre, Telefono, Puesto;

        public Empleado(String ID, String Nombre, String Telefono, String Puesto){
            this.ID = new SimpleStringProperty(ID);
            this.Nombre = new SimpleStringProperty(Nombre);
            this.Telefono = new SimpleStringProperty(Telefono);
            this.Puesto = new SimpleStringProperty(Puesto);
        }

        public String GetID(){ return ID.get(); }
        public String GetNombre(){ return Nombre.get(); }
        public String GetTelefono(){ return Telefono.get(); }
        public String GetPuesto(){ return Puesto.get(); }
        public StringProperty sGetID(){ return ID; }
        public StringProperty sGetNombre(){ return Nombre; }
        public StringProperty sGetTelefono(){ return Telefono; }
        public StringProperty sGetPuesto(){ return Puesto; }

    }
}
