package sample;

import Objetos.Producto;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import tray.notification.NotificationType;

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
    private JFXTextField TxtFiltrar, TxtCod, TxtCantidad, TxtPrecio, TxtPVenta, TxtBuscar, ModCod, ModPCompra, ModPVenta;

    @FXML
    private JFXTextArea ModDesc;

    @FXML
    private JFXCheckBox ModReceta;

    @FXML
    private JFXButton BtnFinalizar;

    @FXML
    private JFXTreeTableView<Producto> TreeViewCompra;

    @FXML
    private Label lblTotal, lbl;

    @FXML
    private JFXComboBox<String> Proveedores, ModProveedor, ModPresen, ModCateg;

    public static final ObservableList<Producto> LProducto = FXCollections.observableArrayList();

    final FilteredList<Producto> FiltroProductos = new FilteredList<>(LProducto, e -> true);

    public static final ObservableList<Producto> LProductoCompra = FXCollections.observableArrayList();

    private Double Total = 0.0, Aux = 0.0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LProducto.clear();
        CargarInventario();
        InicializarTablaCompra();
        CargarComboBox();
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
        Cant.setCellFactory(new Callback<TreeTableColumn<Producto, String>, TreeTableCell<Producto, String>>() {
            @Override
            public TreeTableCell<Producto, String> call(TreeTableColumn<Producto, String> param) {
                return new TextFieldTreeTableCell<>();
            }
        });
        Cant.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        Cant.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<Producto, String>>() {
            @Override
            public void handle(TreeTableColumn.CellEditEvent<Producto, String> event) {
                TreeItem<Producto> Edit = TreeViewCompra.getTreeItem(event.getTreeTablePosition().getRow());
                Edit.getValue().SetSstock(event.getNewValue());
                TxtCantidad.setText(event.getNewValue());
                Total = Total + Double.parseDouble(TxtPrecio.getText()) * Double.parseDouble(event.getNewValue());
                lblTotal.setText(String.valueOf(Total));
            }
        });
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
        this.TreeViewCompra.setEditable(true);
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

    public void CargarComboBox() {
        try {
            Connection con = Conexion.getConnection();
            ResultSet rs = null;
            rs = con.createStatement().executeQuery("select Nombre from Proveedor");
            while(rs.next()){
                this.ModProveedor.getItems().add(rs.getString(1));
                this.Proveedores.getItems().add(rs.getString(1));
            }
            rs = con.createStatement().executeQuery("select distinct Categoria from Producto;");
            while(rs.next()){
                this.ModCateg.getItems().add(rs.getString(1));
            }
            rs = con.createStatement().executeQuery("select distinct Presentacion from Producto;");
            while(rs.next()){
                this.ModPresen.getItems().add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Buscar(KeyEvent event) throws SQLException {
        if(event.getCode().equals(KeyCode.ENTER)) {
            if (!"".equals(this.TxtBuscar.getText())) {
                Connection con = Conexion.getConnection();
                PreparedStatement statement;
                if(this.TxtBuscar.getText().length() == 13){
                    statement = con.prepareStatement("select idProducto, Precio_Compra, Precio_Venta, Producto.Descripcion, Proveedor.Nombre, Presentacion, Categoria, Receta\n" +
                            "from Producto inner join Proveedor on Producto.Proveedor = Proveedor.idProveedor where Cod_Barras = ?;");
                } else {
                    statement = con.prepareStatement("select idProducto, Precio_Compra, Precio_Venta, Producto.Descripcion, Proveedor.Nombre, Presentacion, Categoria, Receta\n" +
                            "from Producto inner join Proveedor on Producto.Proveedor = Proveedor.idProveedor where idProducto = ?;");
                }
                statement.setString(1, this.TxtBuscar.getText());
                ResultSet rs = statement.executeQuery();
                while(rs.next()){
                    this.ModCod.setText(rs.getString(1));
                    this.ModPCompra.setText(rs.getString(2));
                    this.ModPVenta.setText(rs.getString(3));
                    this.ModDesc.setText(rs.getString(4));
                    this.ModProveedor.setValue(rs.getString(5));
                    this.ModPresen.setValue(rs.getString(6));
                    this.ModCateg.setValue(rs.getString(7));
                    if(rs.getString(8).equals("1")){
                        this.ModReceta.setSelected(true);
                    } else {
                        this.ModReceta.setSelected(false);
                    }
                    return;
                }
                Alertas.MostrarAlerta("No se ha encontrado el producto", NotificationType.WARNING, "Advertencia");
            }
        }
    }

    public void Actualizar(MouseEvent mouseEvent){
        try {
            Connection con = Conexion.getConnection();
            PreparedStatement Statement = con.prepareStatement("update Producto set Descripcion = ?, Precio_Compra = ?, Precio_Venta = ?, Proveedor = ?, Presentacion = ?, Categoria = ?, Receta = ? where idProducto = ?;");
            Statement.setString(1,this.ModDesc.getText());
            Statement.setDouble(2, Double.parseDouble(this.ModPCompra.getText()));
            Statement.setDouble(3, Double.parseDouble(this.ModPVenta.getText()));
            Statement.setInt(4,GetIdProveedor());
            Statement.setString(5, this.ModPresen.getValue());
            Statement.setString(6, this.ModCateg.getValue());
            Statement.setBoolean(7, this.ModReceta.isSelected());
            Statement.setString(8, this.ModCod.getText());
            Statement.executeUpdate();
            Alertas.MostrarAlerta("Producto actualizado con exito", NotificationType.SUCCESS, "Success");
        } catch (SQLException e) {
            Alertas.MostrarAlerta("A ocurrido un error", NotificationType.ERROR, "Error");
        }
    }

    public int GetIdProveedor() throws SQLException {
        Connection con = Conexion.getConnection();
        PreparedStatement Statement = con.prepareStatement("Select idProveedor from Proveedor where Nombre = ?");
        Statement.setString(1,this.ModProveedor.getValue());
        ResultSet rs = Statement.executeQuery();
        while(rs.next()){
            return rs.getInt(1);
        }
        return 0;
    }
}
