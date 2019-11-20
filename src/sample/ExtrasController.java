package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
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
    private TreeTableColumn<Empleado, String> IDEmpleado;

    @FXML
    private TreeTableColumn<Empleado, String> NombreEmpleado;

    @FXML
    private TreeTableColumn<Empleado, String> TelefonoEmpleado;

    @FXML
    private TreeTableColumn<Empleado, String> PuestoEmpleado;

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

    @FXML
    private JFXTreeTableView<Cliente> TableClientes;

    @FXML
    private TreeTableColumn<Cliente, String> IdCliente;

    @FXML
    private TreeTableColumn<Cliente, String> NombreCliente;

    @FXML
    private TreeTableColumn<Cliente, String> DireccionCliente;

    @FXML
    private TreeTableColumn<Cliente, String> TelefonoCliente;

    @FXML
    private TreeTableColumn<Cliente, String> PuntosCliente;

    @FXML
    private JFXTextField BuscarCli;

    @FXML
    private JFXTextField ENomCli;

    @FXML
    private JFXTextArea EDirCli;

    @FXML
    private JFXTextField ETelCli;

    @FXML
    private JFXTextField EPtsCli;



    @FXML
    private JFXTextField ERfcCli;

    @FXML
    private JFXTextField EEdadCli;

    @FXML
    private JFXTextField NNomCli;

    @FXML
    private JFXTextArea NDirCli;

    @FXML
    private JFXTextField NTelCli;

    @FXML
    private JFXTextField NRfcCli;

    @FXML
    private JFXTextField NEdadCli;

    @FXML
    private JFXButton BtnEditarCli;

    @FXML
    private JFXButton BtnAgregarCli;

    private static final ObservableList<Empleado> LEmpleados = FXCollections.observableArrayList();
    private static final ObservableList<Cliente> LClientes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CargarComboBox();
        CargarTablas();
        CargarListas();
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

    private void CargarListas(){
        try {
            Connection con = Conexion.getConnection();
            //Lista Empleados
            ResultSet rs = con.createStatement().executeQuery("select idEmpleado, Nombre, Telefono, Jerarquia from Empleado;");
            while(rs.next()){
                LEmpleados.add(new Empleado(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            rs = con.createStatement().executeQuery("select idCliente, Nombre, Direccion, Telefono, Puntos from Cliente where idCliente != 1");
            while(rs.next()){
                LClientes.add(new Cliente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void CargarTablas(){
        //Empleados
        IDEmpleado.setCellValueFactory((TreeTableColumn.CellDataFeatures<Empleado, String> param) -> param.getValue().getValue().sGetID());
        NombreEmpleado.setCellValueFactory((TreeTableColumn.CellDataFeatures<Empleado, String> param) -> param.getValue().getValue().sGetNombre());
        TelefonoEmpleado.setCellValueFactory((TreeTableColumn.CellDataFeatures<Empleado, String> param) -> param.getValue().getValue().sGetTelefono());
        PuestoEmpleado.setCellValueFactory((TreeTableColumn.CellDataFeatures<Empleado, String> param) -> param.getValue().getValue().sGetPuesto());
        final TreeItem<Empleado> root = new RecursiveTreeItem<>(LEmpleados, RecursiveTreeObject::getChildren);
        this.TableEmpleados.setRoot(root);
        this.TableEmpleados.setShowRoot(false);
        //Clientes
        IdCliente.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().sGetID());
        NombreCliente.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().sGetNombre());
        DireccionCliente.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().sGetDireccion());
        TelefonoCliente.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().sGetTelefono());
        PuntosCliente.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().sGetPuntos());
        final TreeItem<Cliente> root2 = new RecursiveTreeItem<>(LClientes, RecursiveTreeObject::getChildren);
        this.TableClientes.setRoot(root2);
        this.TableClientes.setShowRoot(false);
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
            statement.execute();
            LEmpleados.add(new Empleado(String.valueOf(Integer.parseInt(LEmpleados.get(LEmpleados.size() - 1).GetID()) + 1), NNombreEmp.getText(), NTelEmp.getText(), NUsuEmp.getText()));
            Alertas.MostrarAlerta("Empleado registrado", NotificationType.ERROR, "Exito");
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
        if (keyEvent.getSource().equals(NPassEmp) || keyEvent.getSource().equals(NValidarPass)){
            if(this.NValidarPass.getText().equals(this.NPassEmp.getText()) && this.NPassEmp.getText().equals(this.NValidarPass.getText())){
                this.ValidarPass2.setText("Correcto");
            } else {
                this.ValidarPass2.setText("No coinciden");
            }
        } else if (keyEvent.getSource().equals(EPassEmp) || keyEvent.getSource().equals(EValidarPass)){
            if(this.EValidarPass.getText().equals(this.EPassEmp.getText()) && this.EPassEmp.getText().equals(this.EValidarPass.getText())){
                this.ValidarPass.setText("Correcto");
            } else {
                this.ValidarPass.setText("No coinciden");
            }
        }
    }

    public void BuscarCliente(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (!"".equals(this.BuscarCli.getText())) {
                try {
                    Connection con = Conexion.getConnection();
                    PreparedStatement statement = con.prepareStatement("select Nombre, Direccion, Telefono, Edad, Puntos, Rfc from Cliente where idCliente = ?;");
                    statement.setString(1, this.BuscarCli.getText());
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()){
                        this.ENomCli.setText(rs.getString(1));
                        this.EDirCli.setText(rs.getString(2));
                        this.ETelCli.setText(rs.getString(3));
                        this.EEdadCli.setText(rs.getString(4));
                        this.EPtsCli.setText(rs.getString(5));
                        this.ERfcCli.setText(rs.getString(6));
                        return;
                    }
                    Alertas.MostrarAlerta("No se encuentra el cliente", NotificationType.ERROR, "Error");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void ActualizarCliente(MouseEvent mouseEvent) {
        try {
            Connection con = Conexion.getConnection();
            PreparedStatement statement = con.prepareStatement("update Cliente set Nombre = ?, Direccion = ?, Telefono = ?, Rfc = ?, Edad = ?");
            statement.setString(1, ENomCli.getText());
            statement.setString(2, EDirCli.getText());
            statement.setString(3, ETelCli.getText());
            statement.setString(4, ERfcCli.getText());
            statement.setString(5, EEdadCli.getText());
            statement.executeUpdate();
            Alertas.MostrarAlerta("CLiente actualizado", NotificationType.SUCCESS, "Correcto");
        } catch (SQLException e) {
            Alertas.MostrarAlerta("Ha ocurrido un error", NotificationType.ERROR, "Error!");
            e.printStackTrace();
        }

    }

    public void AgregarCliente(MouseEvent mouseEvent) {
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

    class Cliente extends RecursiveTreeObject<Cliente>{
        StringProperty Id, Nombre, Direccion, Telefono, Puntos;

        public Cliente(String Id, String Nombre, String Direccion, String Telefono, String Puntos){
            this.Id = new SimpleStringProperty(Id);
            this.Nombre = new SimpleStringProperty(Nombre);
            this.Direccion = new SimpleStringProperty(Direccion);
            this.Telefono = new SimpleStringProperty(Telefono);
            this.Puntos = new SimpleStringProperty(Puntos);
        }

        public String GetID(){ return Id.get(); }
        public String GetNombre(){ return Nombre.get(); }
        public String GetDireccion(){ return Direccion.get(); }
        public String GetTelefono(){ return Telefono.get(); }
        public String GetPuntos(){ return Puntos.get(); }
        public StringProperty sGetID(){ return Id; }
        public StringProperty sGetNombre(){ return Nombre; }
        public StringProperty sGetDireccion(){ return Direccion; }
        public StringProperty sGetTelefono(){ return Telefono; }
        public StringProperty sGetPuntos(){ return Puntos; }
    }
}
