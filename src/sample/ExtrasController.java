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

import java.net.URL;
import java.sql.Connection;
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
    private JFXComboBox<?> EPuestoEmp;

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
    private JFXComboBox<?> NPuestoEmp;

    @FXML
    private Label ValidarPass;

    @FXML
    private Label ValidarPass2;

    @FXML
    private JFXButton BtnAgregar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void BuscarEmpleado(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(!"".equals(this.BuscarEmp.getText())){
                try {
                    Connection con = Conexion.getConnection();
                    ResultSet rs = con.createStatement().executeQuery("select Nombre, Telefono, Usuario, Jerarquia from Empleado where idEmpleado = ?;");
                    while(rs.next()){
                        this.ENombreEmp.setText(rs.getString(1));
                        this.ETelEmp.setText(rs.getString(2));
                        this.EusuEmp.setText(rs.getString(3));

                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void InsertarEmpleado(MouseEvent mouseEvent) {
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
