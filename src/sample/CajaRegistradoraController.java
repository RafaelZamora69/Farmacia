package sample;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class CajaRegistradoraController implements Initializable {
    Connection con = Conexion.getConnection();

    @FXML
    public JFXTreeTableView<ProductoVenta> tablaVentas;

    ObservableList<ProductoVenta> lista = FXCollections.observableArrayList();

    @FXML
    private JFXTextField tfBuscar;

    @FXML
    public Label lbPuntos;

    @FXML
    public Label lbCliente;

    @FXML
    private Label LblTotal;

    @FXML
    private TreeTableColumn<ProductoVenta, String> Num;

    @FXML
    private TreeTableColumn<ProductoVenta, String> IdProd;

    @FXML
    private TreeTableColumn<ProductoVenta, String> NomProd;

    @FXML
    private TreeTableColumn<ProductoVenta, String> CantProd;

    @FXML
    private TreeTableColumn<ProductoVenta, String> PrecioProd;

    @FXML
    private JFXTextField TxtElim;

    @FXML
    private JFXTextField TxtCant;

    @FXML
    private JFXTextField TxtMod;
    int i = 1;
    Double Total = 0.0;
    public CajaRegistradoraController() throws SQLException {
    }

    public void salir(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Principal.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CargarTabla();
    }

    private void CargarTabla(){
        Num.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoVenta, String> param) -> param.getValue().getValue().GetNum());
        IdProd.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoVenta, String> param) -> param.getValue().getValue().GetId());
        NomProd.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoVenta, String> param) -> param.getValue().getValue().GetNombre());
        CantProd.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoVenta, String> param) -> param.getValue().getValue().GetCant());
        PrecioProd.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoVenta, String> param) -> param.getValue().getValue().GetPrecio());
        final TreeItem<ProductoVenta> root = new RecursiveTreeItem<>(lista, RecursiveTreeObject::getChildren);
        this.tablaVentas.setRoot(root);
        this.tablaVentas.setShowRoot(false);
    }

    public void vender(ActionEvent actionEvent) throws IOException {

    }

    public void limpiarVentana(){
        lista.clear();
        tfBuscar.setText("");
        lbCliente.setText(null);
        lbPuntos.setText(null);
        LblTotal.setText("0.0");
        i = 1;
    }

    public void cliente(MouseEvent mouseEvent) {

    }

    public void buscar(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            try {
                PreparedStatement statement;
                if(tfBuscar.getText().length() == 13){
                    statement = con.prepareStatement("select idProducto, Descripcion, Precio_Venta from Producto where Cod_Barras = ?");
                    statement.setString(1, tfBuscar.getText());
                } else {
                    statement = con.prepareStatement("select idProducto, Descripcion, Precio_Venta from Producto where idProducto = ?");
                    statement.setString(1, tfBuscar.getText());
                }
                ResultSet rs = statement.executeQuery();
                if(rs.next()){
                    lista.add(new ProductoVenta(String.valueOf(i), rs.getString(1), rs.getString(2), String.valueOf(1),rs.getString(3)));
                    Total += Double.parseDouble(rs.getString(3));
                    i++;
                    TxtMod.setText("");
                    TxtCant.setText("1");
                }
                LblTotal.setText(String.valueOf(Total));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void SetEliminar(KeyEvent keyEvent) {
    }

    public void SetCantidad(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(!"".equals(TxtMod.getText())){
                lista.get(Integer.parseInt(TxtMod.getText()) - 1).Cant.set(TxtCant.getText());
            } else if(TxtMod.getText().equals("")){
                lista.get(lista.size() - 1).Cant.set(TxtCant.getText());
            }
        }
    }

    public void Modificar(KeyEvent keyEvent) {
    }

    public class ProductoVenta extends RecursiveTreeObject<ProductoVenta> {
        StringProperty Num, Id, Nombre, Cant, Precio;
        public ProductoVenta(String Num, String Id, String Nombre, String Cant, String Precio){
            this.Num = new SimpleStringProperty(Num);
            this.Id = new SimpleStringProperty(Id);
            this.Nombre = new SimpleStringProperty(Nombre);
            this.Cant = new SimpleStringProperty(Cant);
            this.Precio = new SimpleStringProperty(Precio);
        }
        public StringProperty GetNum(){ return Num; }
        public StringProperty GetId(){ return Id; }
        public StringProperty GetNombre(){ return Nombre; }
        public StringProperty GetCant(){ return Cant; }
        public StringProperty GetPrecio(){ return Precio; }
    }
}
