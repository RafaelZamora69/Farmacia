package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @FXML
    private JFXTextField BuscarProm;

    @FXML
    private JFXToggleButton MActProm;

    @FXML
    private JFXTreeTableView<Promocion> TableMProm;

    @FXML
    private TreeTableColumn<Promocion, String> MIdProm;

    @FXML
    private TreeTableColumn<Promocion, String> MProdProm;

    @FXML
    private JFXTextField MAgregarID;

    @FXML
    private JFXTextField MElimIdProm;

    @FXML
    private JFXTextArea CDescProm;

    @FXML
    private JFXTreeTableView<Promocion> TableCPromo;

    @FXML
    private TreeTableColumn<Promocion, String> CIdProm;

    @FXML
    private TreeTableColumn<Promocion, String> CProdProm;

    @FXML
    private JFXTextField CAddIDProm;

    @FXML
    private JFXTextField CDelIdProm;

    @FXML
    private JFXTextArea MDescProm;

    @FXML
    private JFXButton BtnEliminarProm;

    @FXML
    private JFXTextField BuscarProveedor;

    @FXML
    private JFXTextArea ENombProv;

    @FXML
    private JFXTextField ETelProv;

    @FXML
    private JFXTextField ECorrProv;

    @FXML
    private JFXTextArea EDirProv;

    @FXML
    private JFXTextArea NProvNom;

    @FXML
    private JFXTextField NTelProv;

    @FXML
    private JFXTextField NCorrProv;

    @FXML
    private JFXTextArea NDirProv;

    @FXML
    private JFXButton BtnActualizarProveedor;

    @FXML
    private JFXButton BtnNuevoProveedor;

    @FXML
    private JFXTextArea ProdDEsc;

    @FXML
    private JFXTextField CodBarras;

    @FXML
    private JFXComboBox<String> ProdPresen;

    @FXML
    private JFXComboBox<String> ProdProveedor;

    @FXML
    private JFXTextField ProdPCompra;

    @FXML
    private JFXTextField ProdPVenta;

    @FXML
    private JFXToggleButton ProdReceta;

    @FXML
    private JFXComboBox<String> ProdCategoria;

    @FXML
    private JFXButton BtnNuevoProd;


    private static final ObservableList<Empleado> LEmpleados = FXCollections.observableArrayList();
    private static final ObservableList<Cliente> LClientes = FXCollections.observableArrayList();
    private static final ObservableList<Promocion> LPromocion = FXCollections.observableArrayList();
    private static final ObservableList<Promocion> LNPromocion = FXCollections.observableArrayList();
    private Connection ConTransact = Conexion.GetConexionPromocion();
    private Connection con = Conexion.getConnection();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LEmpleados.clear();
        LClientes.clear();
        CargarComboBox();
        CargarTablas();
        CargarListas();
    }

    private void CargarComboBox() {
        try {
            ResultSet rs = con.createStatement().executeQuery("select distinct Jerarquia from Empleado;");
            while (rs.next()) {
                this.EPuestoEmp.getItems().add(rs.getString(1));
                this.NPuestoEmp.getItems().add(rs.getString(1));
            }
            rs = con.createStatement().executeQuery("select Nombre from Proveedor;");
            while (rs.next()){
                this.ProdProveedor.getItems().add(rs.getString(1));
            }
            rs = con.createStatement().executeQuery("select distinct(Presentacion) from Producto;");
            while(rs.next()){
                this.ProdPresen.getItems().add(rs.getString(1));
            }
            rs = con.createStatement().executeQuery("select distinct(Categoria) from Producto;");
            while(rs.next()){
                this.ProdCategoria.getItems().add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void CargarListas() {
        try {
            //Lista Empleados
            ResultSet rs = con.createStatement().executeQuery("select idEmpleado, Nombre, Telefono, Jerarquia from Empleado;");
            while (rs.next()) {
                LEmpleados.add(new Empleado(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            rs = con.createStatement().executeQuery("select idCliente, Nombre, Direccion, Telefono, Puntos from Cliente where idCliente != 1");
            while (rs.next()) {
                LClientes.add(new Cliente(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void CargarTablas() {
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
        //Promocion
        MIdProm.setCellValueFactory((TreeTableColumn.CellDataFeatures<Promocion, String> param) -> param.getValue().getValue().sGetIdProducto());
        MProdProm.setCellValueFactory((TreeTableColumn.CellDataFeatures<Promocion, String> param) -> param.getValue().getValue().sGetProducto());
        final TreeItem<Promocion> root3 = new RecursiveTreeItem<>(LPromocion, RecursiveTreeObject::getChildren);
        this.TableMProm.setRoot(root3);
        this.TableMProm.setShowRoot(false);
        //Nueva Promocion
        CIdProm.setCellValueFactory((TreeTableColumn.CellDataFeatures<Promocion, String> param) -> param.getValue().getValue().sGetIdProducto());
        CProdProm.setCellValueFactory((TreeTableColumn.CellDataFeatures<Promocion, String> param) -> param.getValue().getValue().sGetProducto());
        final TreeItem<Promocion> root4 = new RecursiveTreeItem<>(LNPromocion, RecursiveTreeObject::getChildren);
        this.TableCPromo.setRoot(root4);
        this.TableCPromo.setShowRoot(false);
    }

    public void BuscarEmpleado(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (!"".equals(this.BuscarEmp.getText())) {
                try {
                    PreparedStatement statement = con.prepareStatement("select Nombre, Telefono, Usuario, Jerarquia from Empleado where idEmpleado = ?;");
                    statement.setString(1, this.BuscarEmp.getText());
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
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
            PreparedStatement statement = con.prepareStatement("insert into Empleado (Nombre, Telefono, Usuario, Password, Jerarquia) values (?, ?, ?, sha1(?), ?)");
            statement.setString(1, NNombreEmp.getText());
            statement.setString(2, NTelEmp.getText());
            statement.setString(3, NUsuEmp.getText());
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
            PreparedStatement statement;
            if(EPassEmp.getText().equals("")){
                statement = con.prepareStatement("update Empleado set Nombre = ?, Telefono = ?, Jerarquia = ? where idEmpleado = ?;");
                statement.setString(1, ENombreEmp.getText());
                statement.setString(2, ETelEmp.getText());
                statement.setString(3, EPuestoEmp.getValue());
                statement.setString(4, BuscarEmp.getText());
            } else {
                statement = con.prepareStatement("update Empleado set Nombre = ?, Telefono = ?, Jerarquia = ?, Password = sha1(?) where idEmpleado = ?;");
                statement.setString(1, ENombreEmp.getText());
                statement.setString(2, ETelEmp.getText());
                statement.setString(3, EPuestoEmp.getValue());
                statement.setString(4, EPassEmp.getText());
                statement.setString(5, BuscarEmp.getText());
            }
            statement.executeUpdate();
            Alertas.MostrarAlerta("Empleado actualizado", NotificationType.SUCCESS, "Correcto");
        } catch (SQLException e) {
            Alertas.MostrarAlerta("Ocurrió un problema al actualizar", NotificationType.ERROR, "Error");
            e.printStackTrace();
        }
    }

    public void BuscarCliente(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (!"".equals(this.BuscarCli.getText())) {
                try {
                    PreparedStatement statement = con.prepareStatement("select Nombre, Direccion, Telefono, Edad, Puntos, Rfc from Cliente where idCliente = ?;");
                    statement.setString(1, this.BuscarCli.getText());
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
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
        try{
            PreparedStatement statement = con.prepareStatement("insert into Cliente (Nombre, Direccion, Telefono, Edad, Puntos, Rfc) values (?, ?, ?, ?, 0, ?);");
            statement.setString(1, NNomCli.getText());
            statement.setString(2, NDirCli.getText());
            statement.setString(3, NTelCli.getText());
            statement.setString(4, NEdadCli.getText());
            statement.setString(5, NRfcCli.getText());
            statement.executeUpdate();
            Alertas.MostrarAlerta("Cliente registrado", NotificationType.SUCCESS, "Correcto");
            ResultSet rs = con.createStatement().executeQuery("select max(idCliente) from Cliente;");
            if(rs.next()){
                LClientes.add(new Cliente(rs.getString(1), NNomCli.getText(), NDirCli.getText(), NTelCli.getText(), "0"));
            }
        }catch(SQLException e){
            Alertas.MostrarAlerta("Hubo un problema al registrar el cliente", NotificationType.ERROR, "Error");
            e.printStackTrace();
        }
    }

    public void BuscarPromocion(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (!"".equals(this.BuscarProm.getText())) {
                LPromocion.clear();
                try {
                    PreparedStatement statement = con.prepareStatement("select Descripcion, Activa from Promocion where idPromocion != 1 and idPromocion = ?;");
                    statement.setString(1, this.BuscarProm.getText());
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        this.MDescProm.setText(rs.getString(1));
                        if (rs.getString(2).equals("1")) {
                            MActProm.setSelected(true);
                        } else {
                            MActProm.setSelected(false);
                        }
                        statement = con.prepareStatement("select idPromocion, Detalle_Promocion.idProducto, Producto.Descripcion from Detalle_Promocion inner join Producto on Detalle_Promocion.idProducto = Producto.idProducto where idPromocion = ?;");
                        statement.setString(1, BuscarProm.getText());
                        ResultSet result = statement.executeQuery();
                        while (result.next()) {
                            LPromocion.add(new Promocion(result.getString(1), result.getString(2), result.getString(3)));
                        }
                        return;
                    }
                    Alertas.MostrarAlerta("No se ha encontrado la promoción", NotificationType.WARNING, "Advertencia");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void CrearPromo(MouseEvent mouseEvent) {
        if (LNPromocion.isEmpty()) {
            Alertas.MostrarAlerta("No se puede ingresar una promoción sin articulos", NotificationType.ERROR, "Error");
            return;
        }
        try {
            PreparedStatement statement = con.prepareStatement("insert into Promocion (Descripcion, Activa) values (?, 1)");
            statement.setString(1, CDescProm.getText());
            statement.executeUpdate();
            for (Promocion objeto : LNPromocion) {
                statement = con.prepareStatement("insert into detalle_promocion values(?, ?)");
                ResultSet rs = con.createStatement().executeQuery("select max(idPromocion) from Promocion");
                if (rs.next()) {
                    statement.setInt(1, rs.getInt(1));
                }
                statement.setInt(2, Integer.parseInt(objeto.GetIdProducto()));
                statement.executeUpdate();
            }
            Alertas.MostrarAlerta("Promoción registrada", NotificationType.SUCCESS, "Éxito");
        } catch (SQLException e) {
            Alertas.MostrarAlerta("Ocurrió un error", NotificationType.ERROR, "Error");
            e.printStackTrace();
        }
    }

    public void AgregarProd(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (!"".equals(this.MAgregarID.getText())) {
                try {
                    PreparedStatement statement = ConTransact.prepareStatement("select idProducto, Descripcion from Producto where idProducto = ?");
                    statement.setString(1, MAgregarID.getText());
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        LPromocion.add(new Promocion(this.BuscarProm.getText(), rs.getString(1), rs.getString(2)));
                        statement = con.prepareStatement("insert into Detalle_Promocion (idPromocion, idProducto) values (?, ?)");
                        statement.setString(1, BuscarProm.getText());
                        statement.setString(2, MAgregarID.getText());
                        statement.executeUpdate();
                        return;
                    }
                    Alertas.MostrarAlerta("No se encuentra el articulo", NotificationType.ERROR, "Error");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void EliminarProd(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (keyEvent.getSource().equals(MElimIdProm)) {
                if (!"".equals(this.MElimIdProm.getText())) {
                    try {
                        int i = 0;
                        for (Promocion objeto : LPromocion) {
                            if (objeto.GetIdProducto().equals(MElimIdProm.getText())) {
                                PreparedStatement statement = ConTransact.prepareStatement("delete from Detalle_Promocion where idProducto = ?");
                                statement.setString(1, MElimIdProm.getText());
                                LPromocion.remove(i);
                                statement.executeUpdate();
                                return;
                            }
                            i++;
                        }
                        Alertas.MostrarAlerta("No se encuentra el articulo", NotificationType.ERROR, "Error");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void ActualizarProveedor(MouseEvent mouseEvent) {
        try {
            PreparedStatement statement = con.prepareStatement("update Proveedor set Nombre = ?, Telefono = ?, Correo = ?, Direccion = ? where idProveedor = ?;");
            statement.setString(1, ENombProv.getText());
            statement.setString(2, ETelProv.getText());
            statement.setString(3, ECorrProv.getText());
            statement.setString(4, EDirProv.getText());
            statement.setString(5, BuscarProveedor.getText());
            statement.executeUpdate();
            Alertas.MostrarAlerta("Proveedor actualizado", NotificationType.SUCCESS, "Éxito");
        } catch (SQLException e) {
            Alertas.MostrarAlerta("Hubo un problema al actualizar el proveedor", NotificationType.ERROR, "Error");
            e.printStackTrace();
        }
    }

    public void NuevoProveedor(MouseEvent mouseEvent) {
        try {
            if(NProvNom.getText().equals("") || NTelProv.getText().equals("") || NDirProv.getText().equals("")){
                Alertas.MostrarAlerta("No debe haber campos importantes vacios", NotificationType.ERROR, "Error");
            } else {
                PreparedStatement statement = con.prepareStatement("insert into Proveedor(Nombre, Telefono, Correo, Direccion) values (?, ?, ?, ?)");
                statement.setString(1, NProvNom.getText());
                statement.setString(2, NTelProv.getText());
                statement.setString(3, NCorrProv.getText());
                statement.setString(4, NDirProv.getText());
                statement.executeUpdate();
                Alertas.MostrarAlerta("Proveedor registrado", NotificationType.SUCCESS, "Éxito");
            }
        } catch (SQLException e) {
            Alertas.MostrarAlerta("Hubo un problema al registrar el proveedor", NotificationType.ERROR, "Error");
            e.printStackTrace();
        }
    }

    public void BuscarProveedor(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (!"".equals(BuscarProveedor.getText())) {
                try {
                    PreparedStatement statement = con.prepareStatement("select Nombre, Telefono, Correo, Direccion from Proveedor where idProveedor = ?");
                    statement.setString(1, BuscarProveedor.getText());
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        ENombProv.setText(rs.getString(1));
                        ETelProv.setText(rs.getString(2));
                        ECorrProv.setText(rs.getString(3));
                        EDirProv.setText(rs.getString(4));
                        return;
                    }
                    Alertas.MostrarAlerta("No se encontró el proveedor", NotificationType.ERROR, "Error");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void CancelEditProm(MouseEvent mouseEvent) throws SQLException {
        ConTransact.rollback();
        PreparedStatement statement = con.prepareStatement("select idPromocion, Detalle_Promocion.idProducto, Producto.Descripcion from Detalle_Promocion inner join Producto on Detalle_Promocion.idProducto = Producto.idProducto where idPromocion = ?;");
        statement.setString(1, BuscarProm.getText());
        ResultSet rs = statement.executeQuery();
        LPromocion.clear();
        while (rs.next()) {
            LPromocion.add(new Promocion(rs.getString(1), rs.getString(2), rs.getString(3)));
        }
    }

    public void ActualizarPromo(MouseEvent mouseEvent) {
        try {
            PreparedStatement statement = ConTransact.prepareStatement("update promocion set Descripcion = ? where idPromocion = ?;");
            statement.setString(1, MDescProm.getText());
            statement.setString(2, BuscarProm.getText());
            statement.executeUpdate();
            ConTransact.commit();
            Alertas.MostrarAlerta("Promoción actualizada", NotificationType.SUCCESS, "Éxito");
        } catch (SQLException e) {
            Alertas.MostrarAlerta("No se pudo Actualizar la promoción", NotificationType.ERROR, "Error");
            e.printStackTrace();
        }
    }

    public void Agregar(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (!"".equals(CAddIDProm.getText())) {
                try {
                    PreparedStatement statement = con.prepareStatement("select idProducto, Descripcion from Producto where idProducto = ?");
                    statement.setString(1, CAddIDProm.getText());
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                        LNPromocion.add(new Promocion(null, rs.getString(1), rs.getString(2)));
                        return;
                    }
                    Alertas.MostrarAlerta("No se encuentra el articulo", NotificationType.ERROR, "Error");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void EliminarProm(MouseEvent mouseEvent) {
        try{
            PreparedStatement statement = con.prepareStatement("delete from Promocion where idPromocion = ?;");
            statement.setString(1, BuscarProm.getText());
            statement.executeUpdate();
            Alertas.MostrarAlerta("Promoción eliminada!", NotificationType.SUCCESS, "Correcto");
        } catch(SQLException e){
            Alertas.MostrarAlerta("Hubo un error al eliminar la promoción", NotificationType.ERROR, "Error");
            e.printStackTrace();
        }
    }

    public void GuardarProd(MouseEvent mouseEvent) {
        try{
            PreparedStatement statement = con.prepareStatement("insert into Producto (Cod_Barras, Descripcion, Presentacion, Proveedor, Precio_Compra, Precio_Venta, Cantidad, Receta, Categoria) \n" +
                    "values (?, ?, ?, ?, ?, ?, 0, ?, ?);");
            statement.setString(1, CodBarras.getText());
            statement.setString(2, ProdDEsc.getText());
            statement.setString(3, ProdPresen.getValue());
            statement.setInt(4, GetIdProveedor());
            statement.setDouble(5, Double.parseDouble(ProdPCompra.getText()));
            statement.setDouble(6, Double.parseDouble(ProdPVenta.getText()));
            statement.setBoolean(7, ProdReceta.isSelected());
            statement.setString(8, ProdCategoria.getValue());
            statement.executeUpdate();
            Alertas.MostrarAlerta("Producto registrado correctamente", NotificationType.SUCCESS, "Correcto");
        }catch (SQLException e){
            Alertas.MostrarAlerta("Hubo un error al registrar el producto", NotificationType.ERROR, "Error");
            e.printStackTrace();
        }
    }

    private int GetIdProveedor(){
        try{
            PreparedStatement statement = con.prepareStatement("select idProveedor from Proveedor where Nombre = ?");
            statement.setString(1, ProdProveedor.getValue());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    public void CancelarProd(MouseEvent mouseEvent) {
        CodBarras.clear();
        ProdDEsc.clear();
        ProdPCompra.clear();
        ProdPVenta.clear();
        ProdReceta.setSelected(false);
    }

    class Empleado extends RecursiveTreeObject<Empleado> {
        StringProperty ID, Nombre, Telefono, Puesto;

        public Empleado(String ID, String Nombre, String Telefono, String Puesto) {
            this.ID = new SimpleStringProperty(ID);
            this.Nombre = new SimpleStringProperty(Nombre);
            this.Telefono = new SimpleStringProperty(Telefono);
            this.Puesto = new SimpleStringProperty(Puesto);
        }

        public String GetID() {
            return ID.get();
        }

        public String GetNombre() {
            return Nombre.get();
        }

        public String GetTelefono() {
            return Telefono.get();
        }

        public String GetPuesto() {
            return Puesto.get();
        }

        public StringProperty sGetID() {
            return ID;
        }

        public StringProperty sGetNombre() {
            return Nombre;
        }

        public StringProperty sGetTelefono() {
            return Telefono;
        }

        public StringProperty sGetPuesto() {
            return Puesto;
        }

    }

    class Cliente extends RecursiveTreeObject<Cliente> {
        StringProperty Id, Nombre, Direccion, Telefono, Puntos;

        public Cliente(String Id, String Nombre, String Direccion, String Telefono, String Puntos) {
            this.Id = new SimpleStringProperty(Id);
            this.Nombre = new SimpleStringProperty(Nombre);
            this.Direccion = new SimpleStringProperty(Direccion);
            this.Telefono = new SimpleStringProperty(Telefono);
            this.Puntos = new SimpleStringProperty(Puntos);
        }

        public String GetID() {
            return Id.get();
        }

        public String GetNombre() {
            return Nombre.get();
        }

        public String GetDireccion() {
            return Direccion.get();
        }

        public String GetTelefono() {
            return Telefono.get();
        }

        public String GetPuntos() {
            return Puntos.get();
        }

        public StringProperty sGetID() {
            return Id;
        }

        public StringProperty sGetNombre() {
            return Nombre;
        }

        public StringProperty sGetDireccion() {
            return Direccion;
        }

        public StringProperty sGetTelefono() {
            return Telefono;
        }

        public StringProperty sGetPuntos() {
            return Puntos;
        }
    }

    class Promocion extends RecursiveTreeObject<Promocion> {
        StringProperty IdPromocion, IdProducto, Producto;

        public Promocion(String IdPromocion, String IdProducto, String Producto) {
            this.IdPromocion = new SimpleStringProperty(IdPromocion);
            this.IdProducto = new SimpleStringProperty(IdProducto);
            this.Producto = new SimpleStringProperty(Producto);
        }

        public String GetIdPromocion() {
            return IdPromocion.get();
        }

        public String GetIdProducto() {
            return IdProducto.get();
        }

        public String GetProducto() {
            return Producto.get();
        }

        public StringProperty sGetIdPromocion() {
            return IdPromocion;
        }

        public StringProperty sGetIdProducto() {
            return IdProducto;
        }

        public StringProperty sGetProducto() {
            return Producto;
        }
    }
}
