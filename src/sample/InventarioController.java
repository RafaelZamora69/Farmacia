package sample;

import Objetos.Producto;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tray.notification.NotificationType;

import java.io.IOException;
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
    private JFXTreeTableView<ProductoCompra> TreeViewCompra;

    @FXML
    private Label lblTotal;

    @FXML
    private JFXTextField TxtMod;

    @FXML
    private JFXTextField TxtEliminar;

    @FXML
    private TreeTableColumn<ProductoCompra, String> Num;

    @FXML
    private TreeTableColumn<ProductoCompra, String> ID;

    @FXML
    private TreeTableColumn<ProductoCompra, String> Desc;

    @FXML
    private TreeTableColumn<ProductoCompra, String> Cant;

    @FXML
    private TreeTableColumn<ProductoCompra, String> Comp;

    @FXML
    private TreeTableColumn<ProductoCompra, String> Venta;

    @FXML
    private TreeTableColumn<ProductoCompra, String> Prov;

    @FXML
    private JFXComboBox<String> Proveedores, ModProveedor, ModPresen, ModCateg;

    public static final ObservableList<Producto> LProducto = FXCollections.observableArrayList();

    public static final ObservableList<ProductoCompra> LProductoCompra = FXCollections.observableArrayList();

    private Double Total = 0.0, Aux = 0.0, AuxPCompra = 0.0;
    int i = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LProducto.clear();
        CargarInventario();
        InicializarTablaCompra();
        CargarComboBox();
        this.TxtPrecio.setTooltip(new Tooltip("Precio de compra"));
        this.TxtPVenta.setTooltip(new Tooltip("Precio de venta"));
        this.TxtCantidad.setTooltip(new Tooltip("Cantidad"));
    }

    public void InicializarTablaCompra(){
        ID.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoCompra, String> param) -> param.getValue().getValue().sGetId());
        Desc.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoCompra, String> param) -> param.getValue().getValue().sGetDescripcion());
        Cant.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoCompra, String> param) -> param.getValue().getValue().sGetCantidad());
        Comp.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoCompra, String> param) -> param.getValue().getValue().sGetCompra());
        Venta.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoCompra, String> param) -> param.getValue().getValue().sGetVenta());
        Prov.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoCompra, String> param) -> param.getValue().getValue().sGetProveedor());
        final TreeItem<ProductoCompra> root = new RecursiveTreeItem<>(LProductoCompra, RecursiveTreeObject::getChildren);
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
                    LProductoCompra.add(new ProductoCompra(String.valueOf(i), rs.getString(1), rs.getString(2),"1", rs.getString(3), rs.getString(4), rs.getString(5)));
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

    public void Editar(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(!"".equals(this.TxtMod.getText())){
                Aux = Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCompra()) * Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCantidad());
                TxtCod.setText(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetId());
                TxtCantidad.setText(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCantidad());
                TxtPrecio.setText(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCompra());
                TxtPVenta.setText(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetVenta());
                Proveedores.setValue(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetProveedor());
                if(keyEvent.getSource().equals(TxtCantidad)){
                    LProductoCompra.get(Integer.parseInt(TxtMod.getText())- 1).Cantidad = new SimpleStringProperty(TxtCantidad.getText());
                } else if(keyEvent.getSource().equals(TxtPrecio)){
                    LProductoCompra.get(Integer.parseInt(TxtMod.getText())- 1).Compra = new SimpleStringProperty(TxtCantidad.getText());
                }
            }
        }
    }

    public void ActualizarTotalCompra(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(keyEvent.getSource().equals(TxtCantidad)){
                if(!"".equals(TxtMod.getText())){
                    if((Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCompra()) * Integer.parseInt(this.TxtCantidad.getText())) < Aux){
                        this.Total -= Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCompra()) * Integer.parseInt(this.TxtCantidad.getText());
                        this.lblTotal.setText(String.valueOf(this.Total));
                    } else {
                        this.Total += Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCompra()) * Integer.parseInt(this.TxtCantidad.getText());
                        this.lblTotal.setText(String.valueOf(this.Total));
                    }
                } else {
                    if((Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCompra()) * Integer.parseInt(this.TxtCantidad.getText())) < Aux){

                    }
                }
                LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).Cantidad = new SimpleStringProperty(this.TxtCantidad.getText());
                Aux = Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCompra()) * Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCantidad());
            } else if(keyEvent.getSource().equals(TxtPrecio)){
                if((Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCompra()) * Double.parseDouble(this.TxtPrecio.getText())) < Aux){
                    this.Total -= Double.parseDouble(this.TxtPrecio.getText()) * Integer.parseInt(this.TxtCantidad.getText());
                    this.lblTotal.setText(String.valueOf(this.Total));
                } else {
                    this.Total += Double.parseDouble(this.TxtPrecio.getText()) * Integer.parseInt(this.TxtCantidad.getText());
                    this.lblTotal.setText(String.valueOf(this.Total));
                }
                LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).Compra = new SimpleStringProperty(this.TxtCantidad.getText());
                Aux = Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCompra()) * Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtMod.getText()) - 1).GetCantidad());
            }
        }
    }

    public void FinalizarCompra(MouseEvent mouseEvent) throws IOException {
        if(LProductoCompra.size() > 0){
            FinalizarCompraController.Total = Total;
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FinalizarCompra.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void Eliminar(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(!"".equals(TxtEliminar.getText())){
                Total -= Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtEliminar.getText()) - 1).GetCompra()) * Double.parseDouble(LProductoCompra.get(Integer.parseInt(TxtEliminar.getText()) - 1).GetCantidad());
                lblTotal.setText(String.valueOf(Total));
                LProductoCompra.remove(Integer.parseInt(TxtEliminar.getText()) - 1);
            }
        }
    }

    public class ProductoCompra extends RecursiveTreeObject<ProductoCompra>{
        StringProperty num, Id, Descripcion, Cantidad, Compra, Venta, Proveedor;
        public ProductoCompra(String num, String Id, String Descripcion, String Cantidad, String Compra, String Venta, String Proveedor){
            this.num = new SimpleStringProperty(num);
            this.Id = new SimpleStringProperty(Id);
            this.Descripcion = new SimpleStringProperty(Descripcion);
            this.Cantidad = new SimpleStringProperty(Cantidad);
            this.Compra = new SimpleStringProperty(Compra);
            this.Venta = new SimpleStringProperty(Venta);
            this.Proveedor = new SimpleStringProperty(Proveedor);
        }
        String GetNum(){ return num.get(); }
        String GetId(){ return Id.get(); }
        String GetDescripcion(){ return Descripcion.get(); }
        String GetCantidad(){ return Cantidad.get(); }
        String GetCompra(){ return Compra.get(); }
        String GetVenta(){ return Venta.get(); }
        String GetProveedor(){ return Proveedor.get(); }
        StringProperty sGetNum(){ return num; }
        StringProperty sGetId(){ return Id; }
        StringProperty sGetDescripcion(){ return Descripcion; }
        StringProperty sGetCantidad(){ return Cantidad; }
        StringProperty sGetCompra(){ return Compra; }
        StringProperty sGetVenta(){ return Venta; }
        StringProperty sGetProveedor(){ return Proveedor; }
    }
}
