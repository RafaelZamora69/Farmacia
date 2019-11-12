package sample;

import Objetos.Persona;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReporteController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXTabPane TabPane;

    @FXML
    private JFXDatePicker FechaDe;

    @FXML
    private JFXDatePicker FechaHasta;

    @FXML
    private JFXTreeTableView<ObjetoReporte> TableReporte;

    @FXML
    private Label Total;

    @FXML
    private JFXTreeTableView<Persona> TableVendedores;

    @FXML
    private JFXTextField TxtFiltro;

    @FXML
    private JFXButton BtnBuscar;

    @FXML
    private Label VentasTotales;

    @FXML
    private TreeTableColumn<Persona, String> IdEmpleado;

    @FXML
    private TreeTableColumn<Persona, String> Nombre;

    @FXML
    private TreeTableColumn<Persona, String> Puesto;

    @FXML
    private TreeTableColumn<ObjetoReporte, String> Cliente;

    @FXML
    private TreeTableColumn<ObjetoReporte, String> IDVenta;

    @FXML
    private TreeTableColumn<ObjetoReporte, String> TotalVenta;

    @FXML
    private TreeTableColumn<ObjetoReporte, String> Vendedor;

    @FXML
    private TreeTableColumn<ObjetoReporte, String> Fecha;

    @FXML
    private StackedAreaChart<?, ?> Chart;

    public static final ObservableList<Persona> LVendedor = FXCollections.observableArrayList();
    public static final ObservableList<ObjetoReporte> LReporte = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CargarEmpleados();
        IDVenta.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetIdVenta());
        Vendedor.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetVendedor());
        Fecha.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetFecha());
        TotalVenta.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetTotal());
        Cliente.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetCliente());
        final TreeItem<ObjetoReporte> root = new RecursiveTreeItem<>(LReporte, RecursiveTreeObject::getChildren);
        this.TableReporte.setRoot(root);
        this.TableReporte.setShowRoot(false);
    }

    private void CargarEmpleados() {
        IdEmpleado.setCellValueFactory((TreeTableColumn.CellDataFeatures<Persona, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetIdEmpleado());
        Nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<Persona, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetNombre());
        Puesto.setCellValueFactory((TreeTableColumn.CellDataFeatures<Persona, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetPuesto());
        try {
            Connection con = Conexion.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select idEmpleado, Nombre, Jerarquia from Empleado where Jerarquia = 'Vendedor';");
            while (rs.next()) {
                LVendedor.add(new Persona(rs.getString(1), null, rs.getString(2), null, null, null, rs.getString(3), null, null));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        final TreeItem<Persona> root = new RecursiveTreeItem<>(LVendedor, RecursiveTreeObject::getChildren);
        this.TableVendedores.setRoot(root);
        this.TableVendedores.setShowRoot(false);
    }

    public void CargarReporte() {
        LReporte.clear();
        try {
            Connection con = Conexion.getConnection();
            ResultSet rs;
            PreparedStatement statement = con.prepareStatement("select count(*) from Venta where Fecha between ? and ?;");
            statement.setString(1, this.FechaDe.getValue().toString());
            statement.setString(2, this.FechaHasta.getValue().toString());
            rs = statement.executeQuery();
            while (rs.next()) {
                this.VentasTotales.setText(rs.getString(1));
            }
            statement = con.prepareStatement("select sum(Total) from Venta where Fecha between ? and ?;");
            statement.setString(1, this.FechaDe.getValue().toString());
            statement.setString(2, this.FechaHasta.getValue().toString());
            rs = statement.executeQuery();
            while (rs.next()) {
                this.Total.setText(rs.getString(1));
            }
            statement = con.prepareStatement("select idVenta, Fecha, Total, Empleado.Nombre, Cliente.Nombre\n" +
                    "from Venta inner join Empleado on Venta.idEmpleado = Empleado.idEmpleado\n" +
                    "inner join Cliente on Venta.idCliente = Cliente.idCliente\n" +
                    "where Fecha between ? and ?;");
            statement.setString(1, this.FechaDe.getValue().toString());
            statement.setString(2, this.FechaHasta.getValue().toString());
            rs = statement.executeQuery();
            while (rs.next()) {
                LReporte.add(new ObjetoReporte(rs.getString(1), rs.getString(4), rs.getString(2), rs.getString(3), rs.getString(5)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public class ObjetoReporte extends RecursiveTreeObject<ObjetoReporte> {
        StringProperty IdVenta, Vendedor, Fecha, Total, Cliente;

        public ObjetoReporte(String IdVenta, String Vendedor, String Fecha, String Total, String Cliente) {
            this.IdVenta = new SimpleStringProperty(IdVenta);
            this.Vendedor = new SimpleStringProperty(Vendedor);
            this.Fecha = new SimpleStringProperty(Fecha);
            this.Total = new SimpleStringProperty(Total);
            this.Cliente = new SimpleStringProperty(Cliente);
        }

        public String GetIdVenta() {
            return IdVenta.get();
        }

        public String GetVendedor() {
            return Vendedor.get();
        }

        public String GetFecha() {
            return Fecha.get();
        }

        public String GetTotal() {
            return Total.get();
        }

        public String GetCliente() {
            return Cliente.get();
        }

        public StringProperty sGetIdVenta() {
            return IdVenta;
        }

        public StringProperty sGetVendedor() {
            return Vendedor;
        }

        public StringProperty sGetFecha() {
            return Fecha;
        }

        public StringProperty sGetTotal() {
            return Total;
        }

        public StringProperty sGetCliente() {
            return Cliente;
        }
    }
}
