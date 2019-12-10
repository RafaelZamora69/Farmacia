package sample;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import tray.notification.NotificationType;

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

    public static final ObservableList<ProductoVenta> lista = FXCollections.observableArrayList();

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
    Double Total = 0.0,  Aux = 0.0;

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
        Num.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoVenta, String> param) -> param.getValue().getValue().sGetNum());
        IdProd.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoVenta, String> param) -> param.getValue().getValue().sGetId());
        NomProd.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoVenta, String> param) -> param.getValue().getValue().sGetNombre());
        CantProd.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoVenta, String> param) -> param.getValue().getValue().sGetCant());
        PrecioProd.setCellValueFactory((TreeTableColumn.CellDataFeatures<ProductoVenta, String> param) -> param.getValue().getValue().sGetPrecio());
        final TreeItem<ProductoVenta> root = new RecursiveTreeItem<>(lista, RecursiveTreeObject::getChildren);
        this.tablaVentas.setRoot(root);
        this.tablaVentas.setShowRoot(false);
    }

    public void vender(MouseEvent mouseEvent) throws IOException {
        if(lista.isEmpty()){
            Alertas.MostrarAlerta("No se puede realizar una venta vacía", NotificationType.WARNING, "Aviso");
        } else {
            FinalizarVentaController.Total = this.Total;
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("FinalizarVenta.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void limpiarVentana(){
        lista.clear();
        tfBuscar.clear();
        LblTotal.setText("0.0");
        TxtCant.clear();
        TxtElim.clear();
        TxtMod.clear();
        i = 1;
        Total = 0.0;
    }

    public void buscar(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            try {
                PreparedStatement statement;
                ResultSet rs;
                if(tfBuscar.getText().length() == 13){
                    statement = con.prepareStatement("select Cantidad from Producto where Cod_Barras = ?");
                    statement.setString(1, tfBuscar.getText());
                    rs = statement.executeQuery();
                    if(rs.next()){
                        if(Integer.parseInt(rs.getString(1)) > 0){
                            statement = con.prepareStatement("select idProducto, Descripcion, Precio_Venta, Precio_Compra from Producto where Cod_Barras = ?");
                            statement.setString(1, tfBuscar.getText());
                        } else {
                            Alertas.MostrarAlerta("Este producto está agotado", NotificationType.WARNING, "Aviso");
                            tfBuscar.clear();
                            return;
                        }
                    } else {
                        Alertas.MostrarAlerta("Este producto no está registrado", NotificationType.ERROR, "Error");
                        tfBuscar.clear();
                        return;
                    }
                } else {
                    statement = con.prepareStatement("select Cantidad from Producto where idProducto = ?");
                    statement.setString(1, tfBuscar.getText());
                    rs = statement.executeQuery();
                    if(rs.next()){
                        if(Integer.parseInt(rs.getString(1)) > 0){
                            statement = con.prepareStatement("select idProducto, Descripcion, Precio_Venta, Precio_Compra from Producto where idProducto = ?");
                            statement.setString(1, tfBuscar.getText());
                        } else {
                            Alertas.MostrarAlerta("Este producto está agotado", NotificationType.WARNING, "Aviso");
                            return;
                        }
                    } else {
                        Alertas.MostrarAlerta("Este producto no está registrado", NotificationType.ERROR, "Error");
                        return;
                    }
                }
                rs = statement.executeQuery();
                if(rs.next()){
                    lista.add(new ProductoVenta(String.valueOf(i), rs.getString(1), rs.getString(2), String.valueOf(1),rs.getString(3), rs.getString(4)));
                    Total += Double.parseDouble(rs.getString(3));
                    i++;
                    TxtMod.clear();
                    TxtCant.setText("1");
                    tfBuscar.clear();
                }
                Aux = Integer.parseInt(lista.get(lista.size() - 1).GetCantidad()) * Double.parseDouble(lista.get(lista.size() - 1).GetPrecio());
                LblTotal.setText(String.valueOf(Total));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void SetEliminar(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(!"".equals(TxtElim.getText())){
                try{
                    Total -= Integer.parseInt(lista.get(Integer.parseInt(TxtElim.getText()) - 1).GetCantidad()) * Double.parseDouble(lista.get(Integer.parseInt(TxtElim.getText()) - 1).GetPrecio());
                    LblTotal.setText(String.valueOf(Total));
                    lista.remove(Integer.parseInt(TxtElim.getText()) - 1);
                } catch (IndexOutOfBoundsException e){
                    Alertas.MostrarAlerta("No hay un producto con este indice", NotificationType.ERROR, "Aviso");
                }
            }
        }
    }

    public void SetCantidad(KeyEvent keyEvent) throws SQLException {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(!"".equals(TxtMod.getText())){
                PreparedStatement statement = con.prepareStatement("select Cantidad from Producto where idProducto = ?");
                statement.setString(1, lista.get(Integer.parseInt(TxtMod.getText()) - 1).GetId());
                ResultSet rs = statement.executeQuery();
                if(rs.next()){
                    if(Integer.parseInt(TxtCant.getText()) > Integer.parseInt(rs.getString(1))){
                        Alertas.MostrarAlerta("No hay suficiente cantidad de este producto", NotificationType.WARNING, "Aviso");
                        TxtCant.setText(rs.getString(1));
                        lista.get(Integer.parseInt(TxtMod.getText()) - 1).Cant.set(rs.getString(1));
                    } else {
                        lista.get(Integer.parseInt(TxtMod.getText()) - 1).Cant.set(TxtCant.getText());
                    }
                }
                if(Integer.parseInt(lista.get(Integer.parseInt(TxtMod.getText()) - 1).GetCantidad()) * Double.parseDouble(lista.get(Integer.parseInt(TxtMod.getText()) - 1).GetPrecio()) < Aux){
                    Total -= Aux - Integer.parseInt(lista.get(Integer.parseInt(TxtMod.getText()) - 1).GetCantidad()) * Double.parseDouble(lista.get(Integer.parseInt(TxtMod.getText()) - 1).GetPrecio());
                } else {
                    Total += Integer.parseInt(lista.get(Integer.parseInt(TxtMod.getText()) - 1).GetCantidad()) * Double.parseDouble(lista.get(Integer.parseInt(TxtMod.getText()) - 1).GetPrecio()) - Aux;
                }
                Aux = Integer.parseInt(lista.get(Integer.parseInt(TxtMod.getText()) - 1).GetCantidad()) * Double.parseDouble(lista.get(Integer.parseInt(TxtMod.getText()) - 1).GetPrecio());
            } else {
                PreparedStatement statement = con.prepareStatement("select Cantidad from Producto where idProducto = ?");
                statement.setString(1, lista.get(lista.size() - 1).GetId());
                ResultSet rs = statement.executeQuery();
                if(rs.next()){
                    if(Integer.parseInt(TxtCant.getText()) > Integer.parseInt(rs.getString(1))){
                        Alertas.MostrarAlerta("No hay suficiente cantidad de este producto", NotificationType.WARNING, "Aviso");
                        TxtCant.setText(rs.getString(1));
                        lista.get(lista.size() - 1).Cant.set(rs.getString(1));
                    } else {
                        lista.get(lista.size() - 1).Cant.set(TxtCant.getText());
                    }
                }
                if((Integer.parseInt(lista.get(lista.size() - 1).GetCantidad()) * Double.parseDouble(lista.get(lista.size() - 1).GetPrecio()) < Aux)){
                    Total -= Aux -Integer.parseInt(lista.get(lista.size() - 1).GetCantidad()) * Double.parseDouble(lista.get(lista.size() - 1).GetPrecio());
                } else {
                    Total += Integer.parseInt(lista.get(lista.size() - 1).GetCantidad()) * Double.parseDouble(lista.get(lista.size() - 1).GetPrecio()) - Aux;
                }
                Aux = Integer.parseInt(lista.get(lista.size() - 1).GetCantidad()) * Double.parseDouble(lista.get(lista.size() - 1).GetPrecio());
            }
            LblTotal.setText(String.valueOf(Total));
        }
    }

    public void Modificar(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(!"".equals(TxtMod.getText())){
                try{
                    TxtCant.setText(lista.get(Integer.parseInt(TxtMod.getText()) - 1).GetCantidad());
                } catch (IndexOutOfBoundsException e){
                    Alertas.MostrarAlerta("No hay un producto con este índice", NotificationType.WARNING, "Aviso");
                }
            }
        }
    }

    public class ProductoVenta extends RecursiveTreeObject<ProductoVenta> {
        StringProperty Num, Id, Nombre, Cant, Precio, Compra;
        public ProductoVenta(String Num, String Id, String Nombre, String Cant, String Precio, String Compra){
            this.Num = new SimpleStringProperty(Num);
            this.Id = new SimpleStringProperty(Id);
            this.Nombre = new SimpleStringProperty(Nombre);
            this.Cant = new SimpleStringProperty(Cant);
            this.Precio = new SimpleStringProperty(Precio);
            this.Compra = new SimpleStringProperty(Compra);
        }
        public StringProperty sGetNum(){ return Num; }
        public StringProperty sGetId(){ return Id; }
        public StringProperty sGetNombre(){ return Nombre; }
        public StringProperty sGetCant(){ return Cant; }
        public StringProperty sGetPrecio(){ return Precio; }
        public String GetPrecio(){ return Precio.get(); }
        public String GetCantidad(){ return Cant.get(); }
        public String GetId(){ return Id.get(); }
        public String GetCompra(){ return Compra.get(); }
        public String GetNombre(){ return Nombre.get(); }
    }
}
