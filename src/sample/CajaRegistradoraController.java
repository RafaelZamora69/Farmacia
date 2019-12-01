package sample;

import Objetos.Producto;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.print.*;
import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class CajaRegistradoraController extends Application implements Initializable {

    double total = 0,puntos = 0;
    boolean lleno = false , existe = false;
    String codBarras = "", cliente = "";
    Connection con = Conexion.getConnection("GerardoGalvan", "Gerardo123");
    ResultSet rs;
    PreparedStatement ps;

    @FXML
    public JFXTreeTableView<Producto> tablaVentas;

    ObservableList<Producto> lista = FXCollections.observableArrayList();

    @FXML
    private JFXTextField tfBuscar;

    @FXML
    private JFXTextField tfTotal;

    @FXML
    public Label lbPuntos;

    @FXML
    public Label lbCliente;

    public CajaRegistradoraController() throws SQLException {
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage s = new Stage();
        Parent r = FXMLLoader.load(getClass().getResource("CajaRegistradora.fxml"));
        Scene scene = new Scene(r);
        s.setResizable(false);
        s.setTitle("");
        s.setScene(scene);
        s.show();
    }

    public void salir(MouseEvent mouseEvent) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXTreeTableColumn<Producto, String> colSku = new JFXTreeTableColumn("Sku");
        colSku.setPrefWidth(120);
        colSku.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Producto, String> param) {
                return param.getValue().getValue().sGetCod();
            }

        });
        JFXTreeTableColumn<Producto, String> colDesc = new JFXTreeTableColumn("Descripción");
        colDesc.setPrefWidth(700);
        colDesc.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Producto, String> param) {
                return param.getValue().getValue().sGetDesc();
            }
        });

        JFXTreeTableColumn<Producto, String> colCant = new JFXTreeTableColumn("Cantidad");
        colCant.setPrefWidth(100);
        colCant.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Producto, String> param) {
                return param.getValue().getValue().sGetStock();
            }
        });

        JFXTreeTableColumn<Producto, String> colPrecio = new JFXTreeTableColumn("Precio");
        colPrecio.setPrefWidth(120);
        colPrecio.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {

            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Producto, String> param) {
                return param.getValue().getValue().sGetVenta();
            }
        });
        final TreeItem<Producto> root = new RecursiveTreeItem<Producto>(lista, RecursiveTreeObject::getChildren);
        tablaVentas.getColumns().addAll(colSku,colDesc,colCant,colPrecio);
        tablaVentas.setRoot(root);
        tablaVentas.setShowRoot(false);

    }

    public void buscar( KeyEvent ke) {
        boolean existe = false;
        if (ke.getCode() == KeyCode.ENTER) {
            codBarras = tfBuscar.getText();
            try {
                ps = con.prepareStatement("SELECT idProducto,Cod_Barras,Descripcion,Precio_Compra,Precio_venta FROM producto WHERE Cod_Barras = ? ");
                ps.setString(1, codBarras);
                rs = ps.executeQuery();
                while (rs.next()) {
                    if (lleno == true ){
                        for (Producto p : lista) {
                            if (p.GetCod().equals(codBarras)) {
                                int n = Integer.parseInt(p.GetStock());
                                p.setStock(new SimpleStringProperty(String.valueOf(n + 1)));
                                total += Double.parseDouble(p.GetVenta());
                                tfTotal.setText("$"+total);
                                existe = true;
                                break;
                            }
                        }
                    }
                    if (lleno == false) {
                            int id = rs.getInt("idProducto");
                            String sku = rs.getString("cod_barras"), descripcion = rs.getString("Descripcion");
                            double venta = rs.getDouble("precio_venta"),compra = rs.getDouble("precio_compra");
                            lista.add(new Producto(id,sku, descripcion, "1", String.valueOf(compra),String.valueOf(venta)));
                            total+=venta;
                            tfTotal.setText("$" + total);
                            lleno = true;
                    } else {
                        if(existe == false){
                            int id = rs.getInt("idProducto");
                            String sku = rs.getString("cod_barras"), descripcion = rs.getString("Descripcion");
                            double venta = rs.getDouble("precio_venta"),compra = rs.getDouble("precio_compra");
                            lista.add(new Producto(id,sku, descripcion, "1", String.valueOf(compra),String.valueOf(venta)));
                            total+=venta;
                            tfTotal.setText("$" + total);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void vender(ActionEvent actionEvent) {
        int idventa = 0;
        try {
            ps = con.prepareStatement("select count(*) from venta ");
            rs = ps.executeQuery();
            while (rs.next()){
                idventa = rs.getInt("count(*)")+1;
            }
            ps = con.prepareStatement("insert into venta values (?,curdate(),?,1,?)");
            ps.setInt(1,idventa);
            ps.setDouble(2,total);
            ps.setInt(3,Integer.parseInt(cliente));
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        DocPrintJob pj = service.createPrintJob();
        String lineas = new String("====================\nFARMACIAS VERACRUZANAS");
        byte[] bytes = lineas.getBytes();
        Doc doc = new SimpleDoc(bytes,flavor,null);
        try {
            pj.print(doc,null);
        } catch (Exception e){

        }

       for (Producto p:lista){
           try {
               ps = con.prepareStatement("insert into detalle_venta values(?,?,?,?,?,?,?)");
               ps.setInt(1,idventa);
               ps.setInt(2,Integer.parseInt(p.GetID()));
               ps.setDouble(3,Double.parseDouble(p.GetCompra()));
               ps.setDouble(4,Double.parseDouble(p.GetVenta()));//
               ps.setDouble(5,Double.parseDouble(p.GetStock())*Double.parseDouble(p.GetVenta()));
               ps.setString(6,p.GetStock());
               ps.setString(7,null);
               ps.execute();
           } catch (SQLException e) {
               e.printStackTrace();
           }
       }
       limpiarVentana();
    }

    public void limpiarVentana(){
        lista.removeAll();
        lleno = false;
        existe = false;
        total = 0.00;
        tfTotal.setText("$"+total);
        tfBuscar.setText("");
        cliente = "";
        puntos = 0;
        rs = null;
        ps = null;
        lbCliente.setText(null);
        lbPuntos.setText(null);
    }

    public void cliente(MouseEvent mouseEvent) {
        int numClientes = 0;
        cliente = JOptionPane.showInputDialog("Ingrese el ID del cliente:");
        try {
            ps = con.prepareStatement("select count(*) from cliente");
            rs = ps.executeQuery();
            while (rs.next()){
                numClientes = rs.getInt("count(*)");
            }
        }catch (Exception e){
        }
        if (esNumerico(cliente) && Integer.parseInt(cliente) <= numClientes){
            try {
                ps = con.prepareStatement("Select * from cliente where idCliente = ?");
                ps.setString(1,cliente);
                rs =ps.executeQuery();
                while (rs.next()){
                    String nom = rs.getString("Nombre");
                    lbCliente.setText(nom);
                    lbPuntos.setText(String.valueOf(rs.getInt("Puntos"))+" Puntos");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            cliente  = "1";
            lbCliente.setText("Sin cliente");
            lbPuntos.setText(null);
        }
    }

    private static boolean esNumerico (String s) {
        if (s == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
