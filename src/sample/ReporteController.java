package sample;

import Objetos.Persona;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
    private JFXTreeTableView<Persona> TableReporte;

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
    private TreeTableColumn<Persona, String> Cliente;

    @FXML
    private TreeTableColumn<Persona, String> IDVenta;

    @FXML
    private TreeTableColumn<Persona, String> Vendedor;

    @FXML
    private TreeTableColumn<Persona, String> Fecha;

    @FXML
    private StackedAreaChart<?, ?> Chart;

    public static final ObservableList<Persona> LVendedor = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CargarEmpleados();
    }

    public void CargarEmpleados(){
        IdEmpleado.setCellValueFactory((TreeTableColumn.CellDataFeatures<Persona, String> param ) -> (ObservableValue<String>) param.getValue().getValue().sGetIdEmpleado());
        Nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<Persona, String> param ) -> (ObservableValue<String>) param.getValue().getValue().sGetNombre());
        Puesto.setCellValueFactory((TreeTableColumn.CellDataFeatures<Persona, String> param ) -> (ObservableValue<String>) param.getValue().getValue().sGetPuesto());
        try{
            Connection con = Conexion.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select idEmpleado, Nombre, Jerarquia from Empleado where Jerarquia = 'Vendedor';");
            while (rs.next()){
                LVendedor.add(new Persona(rs.getString(1), null, rs.getString(2), null, null, null, rs.getString(3), null, null));
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
        final TreeItem<Persona> root = new RecursiveTreeItem<>(LVendedor, RecursiveTreeObject::getChildren);
        this.TableVendedores.setRoot(root);
        this.TableVendedores.setShowRoot(false);
    }

    public void CargarReporte(){

    }
}
