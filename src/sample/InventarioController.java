package sample;

import Objetos.Producto;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class InventarioController implements Initializable {

    @FXML
    private AnchorPane Aroot;

    @FXML
    private JFXTabPane root;

    @FXML
    private JFXTreeTableView<Producto> TreeTable;

    @FXML
    private JFXTextField TxtFiltrar, TxtCod, TxtCantidad, TxtPrecio, TxtPVenta;

    @FXML
    private JFXButton BtnFinalizar;

    @FXML
    private JFXTreeTableView<Producto> TreeViewCompra;

    @FXML
    private Label lblTotal, lbl;

    @FXML
    private JFXComboBox<String> Proveedores;

    public static final ObservableList<Producto> LProducto = FXCollections.observableArrayList();

    final FilteredList<Producto> FiltroProductos = new FilteredList<>(LProducto, e -> true);

    public static final ObservableList<Producto> LProductoCompra = FXCollections.observableArrayList();

    private Double Total = 0.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LProducto.clear();
        CargarInventario();
        InicializarTablaCompra();
        CargarProveedores();
    }

    public void InicializarTablaCompra(){
        JFXTreeTableColumn<Producto, String> ID = new JFXTreeTableColumn<>("Código");
        ID.setPrefWidth(100);
        ID.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetID());
        JFXTreeTableColumn<Producto, String> Desc = new JFXTreeTableColumn<>("Descripción");
        Desc.setPrefWidth(385);
        Desc.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetDesc());
        JFXTreeTableColumn<Producto, String> Cant = new JFXTreeTableColumn<>("Cantidad");
        Cant.setPrefWidth(100);
        Cant.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetStock());
        JFXTreeTableColumn<Producto, String> Comp = new JFXTreeTableColumn<>("$ Compra");
        Comp.setPrefWidth(100);
        Comp.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetCompra());
        JFXTreeTableColumn<Producto, String> Venta = new JFXTreeTableColumn<>("$ Venta");
        Venta.setPrefWidth(100);
        Venta.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetVenta());
        JFXTreeTableColumn<Producto, String> Prov = new JFXTreeTableColumn<>("$ Proveedor");
        Prov.setPrefWidth(100);
        Prov.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetProveedor());
        final TreeItem<Producto> root = new RecursiveTreeItem<>(LProductoCompra, RecursiveTreeObject::getChildren);
        this.TreeViewCompra.getColumns().setAll(ID, Desc, Cant, Comp, Venta, Prov);
        this.TreeViewCompra.setRoot(root);
        this.TreeViewCompra.setShowRoot(false);
    }

    public void Agregar(KeyEvent event) throws SQLException {
        if(event.getCode().equals(KeyCode.ENTER)){
            if("".equals(this.TxtCod.getText())){
            } else {
                Connection con = Conexion.getConnection();
                PreparedStatement statement;
                if(this.TxtCod.getText().length() == 13){
                    statement = con.prepareStatement("select idProducto, Descripcion, Precio_Compra, Precio_Venta, Proveedor.Nombre from Producto inner join Proveedor on Producto.Proveedor = Proveedor.idProveedor" +
                            " where Cod_Barras = ?;");
                } else {
                    statement = con.prepareStatement("select idProducto, Descripcion, Precio_Compra, Precio_Venta, Proveedor.Nombre from Producto inner join Proveedor on Producto.Proveedor = Proveedor.idProveedor" +
                            " where idProducto = ?;");
                }
                statement.setString(1, this.TxtCod.getText());
                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    LProductoCompra.add(new Producto(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), "1", rs.getString(5), null));
                    this.TxtCantidad.setText("1");
                    this.TxtPrecio.setText(rs.getString(3));
                    this.TxtPVenta.setText(rs.getString(4));
                    this.Proveedores.setValue(rs.getString(5));
                    this.Total += Double.parseDouble(rs.getString(3));
                    this.lblTotal.setText(String.valueOf(this.Total));
                }
            }
        }
    }

    public void CargarInventario(){
        JFXTreeTableColumn<Producto, String> ID = new JFXTreeTableColumn<>("Código");
        ID.setPrefWidth(70);
        ID.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetID());
        JFXTreeTableColumn<Producto, String> Desc = new JFXTreeTableColumn<>("Descripción");
        Desc.setPrefWidth(230);
        Desc.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetDesc());
        JFXTreeTableColumn<Producto, String> Compra = new JFXTreeTableColumn<>("$ Compra");
        Compra.setPrefWidth(70);
        Compra.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetCompra());
        JFXTreeTableColumn<Producto, String> Venta = new JFXTreeTableColumn<>("$ Venta");
        Venta.setPrefWidth(70);
        Venta.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetVenta());
        JFXTreeTableColumn<Producto, String> Stock = new JFXTreeTableColumn<>("Stock");
        Stock.setPrefWidth(70);
        Stock.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetStock());
        JFXTreeTableColumn<Producto, String> Provee = new JFXTreeTableColumn<>("Proveedor");
        Provee.setPrefWidth(250);
        Provee.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetProveedor());
        JFXTreeTableColumn<Producto, String> Tel = new JFXTreeTableColumn<>("Teléfono");
        Tel.setPrefWidth(110);
        Tel.setCellValueFactory((TreeTableColumn.CellDataFeatures<Producto, String> param) -> (ObservableValue<String>) param.getValue().getValue().sGetTel());
        try{
            Connection con = Conexion.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select Producto.idProducto As 'Código del producto', \n" +
                    "    Producto.Descripcion As 'Descripción', \n" +
                    "    concat('$', Producto.Precio_Compra) As 'Precio de compra',\n" +
                    "    concat('$', Producto.Precio_Venta) As 'Precio de venta',\n" +
                    "    Producto.Cantidad As 'En inventario',\n" +
                    "    Proveedor.Nombre As 'Proveedor',\n" +
                    "    Proveedor.Telefono As 'Teléfono'\n" +
                    "    from Producto inner join Proveedor\n" +
                    "    on Producto.Proveedor = Proveedor.idProveedor\n" +
                    "    order by Producto.Cantidad asc;");
            while (rs.next()){
                LProducto.add(new Producto(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        final TreeItem<Producto> root = new RecursiveTreeItem<>(LProducto, RecursiveTreeObject::getChildren);
        this.TreeTable.getColumns().setAll(ID, Desc, Compra, Venta, Stock, Provee, Tel);
        this.TreeTable.setRoot(root);
        this.TreeTable.setShowRoot(false);
        //Filtro
        this.TxtFiltrar.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            TreeTable.setPredicate((TreeItem<Producto> t) -> {
                Boolean flag = t.getValue().sGetDesc().getValue().contains(newValue);
                return flag;
            });
            TreeTable.setPredicate((TreeItem<Producto> t) -> {
                Boolean flag = t.getValue().sGetProveedor().getValue().contains(newValue);
                return flag;
            });
        });
    }

    public void CargarProveedores() {
        try {
            Connection con = Conexion.getConnection();
            ResultSet rs = null;
            rs = con.createStatement().executeQuery("select Nombre from Proveedor");
            while(rs.next()){
                this.Proveedores.getItems().add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ActualizarCantidad(KeyEvent keyEvent) {
        for (i = 0; i < LProductoCompra.size(); i++){
            if()
        }
    }
}
