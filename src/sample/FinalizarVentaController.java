package sample;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import tray.notification.NotificationType;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

public class FinalizarVentaController implements Initializable {

    @FXML
    private JFXTextField TxtId;

    @FXML
    private JFXTextField TxtFiltro;

    @FXML
    private JFXToggleButton MetodoPago;

    @FXML
    private Label LblClient;

    @FXML
    private Label LblPts;

    @FXML
    private JFXTextField TxtPago;

    @FXML
    private Label LblTotal;

    @FXML
    private Label LblCambio;

    @FXML
    private JFXButton BtnFin;

    @FXML
    private JFXButton BtnCerrar;

    @FXML
    private JFXTreeTableView<Cliente> TableClientes;

    @FXML
    private TreeTableColumn<Cliente, String> IdCliente;

    @FXML
    private TreeTableColumn<Cliente, String> Nombre;

    @FXML
    private TreeTableColumn<Cliente, String> Puntos;

    ObservableList<Cliente> Clientes = FXCollections.observableArrayList();

    Connection con = Conexion.getConnection();
    DecimalFormat df = new DecimalFormat("0.00");
    private double puntos = 0, PuntosUtilizados;
    private int Id;
    public static Double Total;
    private Double TotalBruto;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.LblTotal.setText(String.valueOf(Total));
        TotalBruto = Total;
        CargarTabla();
    }

    private void CargarTabla(){
        IdCliente.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().sGetId());
        Nombre.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().sGetNombre());
        Puntos.setCellValueFactory((TreeTableColumn.CellDataFeatures<Cliente, String> param) -> param.getValue().getValue().sGetPuntos());
        final TreeItem<Cliente> root = new RecursiveTreeItem<>(Clientes, RecursiveTreeObject::getChildren);
        this.TableClientes.setRoot(root);
        this.TableClientes.setShowRoot(false);
        //Cargar info
        try {
            ResultSet rs = con.createStatement().executeQuery("select idCliente, Nombre, Puntos from Cliente");
            while(rs.next()){
                Clientes.add(new Cliente(rs.getString(1), rs.getString(2), df.format(rs.getDouble(3))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Filtro
        this.TxtFiltro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            TableClientes.setPredicate((TreeItem<Cliente> t) -> {
                Boolean flag = t.getValue().sGetNombre().getValue().contains(newValue);
                return flag;
            });
        });

    }

    public void Vender(MouseEvent mouseEvent) {
        try{
            PreparedStatement statement;
            if(MetodoPago.isSelected()){
                statement = con.prepareStatement("update Cliente set Puntos = ? where idCliente = ?");
                if(Double.parseDouble(LblPts.getText()) - TotalBruto < 0){
                    statement.setInt(1, 0);
                    PuntosUtilizados = Double.parseDouble(LblPts.getText());
                } else {
                    statement.setDouble(1, Double.parseDouble(LblPts.getText()) - TotalBruto);
                    PuntosUtilizados = TotalBruto;
                }
                statement.setInt(2, Integer.parseInt(TxtId.getText()));
                statement.executeUpdate();
            }
            statement = con.prepareStatement("insert into Venta(Fecha, Total, idEmpleado, idCliente) values (curdate(), ?, ?, ?)");
            statement.setDouble(1, TotalBruto);
            statement.setInt(2, PrincipalController.Id);
            statement.setInt(3, this.Id);
            statement.executeUpdate();
            statement = con.prepareStatement("insert into Detalle_Venta(idVenta, idProducto, Precio_Compra, Precio_Venta, Total, Cantidad, promocion) values (?, ?, ?, ?, ?, ?, 1);");
            for(CajaRegistradoraController.ProductoVenta Producto : CajaRegistradoraController.lista){
                statement.setInt(1, GetIdVenta());
                statement.setInt(2, Integer.parseInt(Producto.GetId()));
                statement.setDouble(3, Double.parseDouble(Producto.GetCompra()));
                statement.setDouble(4, Double.parseDouble(Producto.GetPrecio()));
                statement.setDouble(5, Double.parseDouble(Producto.GetPrecio()) * Integer.parseInt(Producto.GetCantidad()));
                statement.setInt(6, Integer.parseInt(Producto.GetCantidad()));
                statement.executeUpdate();
            }
            Alertas.MostrarAlerta("Venta registrada", NotificationType.SUCCESS, "Éxito");
            BtnFin.setDisable(true);
            CrearTicket();
        }catch (SQLException | IOException e){
            Alertas.MostrarAlerta("Ocurrió un error al registrar la venta", NotificationType.ERROR, "Error");
            e.printStackTrace();
        }
    }

    private int GetIdVenta(){
        try{
            ResultSet rs = con.createStatement().executeQuery("select Max(IdVenta) from Venta");
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e){

        }
        return 0;
    }

    public void Cerrar(MouseEvent mouseEvent) {
        ((Node) mouseEvent.getSource()).getScene().getWindow().hide();
    }

    public void BuscarCliente(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(!"".equals(TxtId.getText())){
                try{
                    PreparedStatement statement = con.prepareStatement("select Nombre, Puntos from Cliente where idCliente = ?");
                    statement.setString(1, TxtId.getText());
                    ResultSet rs = statement.executeQuery();
                    if(rs.next()){
                        LblClient.setText(rs.getString(1));
                        LblPts.setText(df.format(rs.getDouble(2)));
                        puntos = (Double.parseDouble(rs.getString(2)));
                        Id = Integer.parseInt(TxtId.getText());
                        if(Integer.parseInt(TxtId.getText()) != 1){
                            MetodoPago.setDisable(false);
                        } else {
                            MetodoPago.setDisable(true);
                        }
                        return;
                    }
                    Alertas.MostrarAlerta("Este Cliente no existe", NotificationType.WARNING, "Aviso");
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void CrearTicket() throws IOException, SQLException {
        Date fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("hh:mm a dd-MMM-yyy");
        PdfWriter writer = new PdfWriter("Ticket.pdf");
        PdfDocument pdf = new PdfDocument(writer);
        Document Documento = new Document(pdf);
        Image logo = new Image(ImageDataFactory.create("C:\\Program Files (x86)\\Farmacia\\Imagenes\\farmacia1.png"));
        logo.setWidth(100); logo.setHeight(50);
        Documento.add(new Paragraph("Farmacias veracruzanas\t\t").add(logo));
        Documento.add(new Paragraph("N° de venta: " + String.valueOf(GetIdVenta()) + ", Fecha: " + formato.format(fecha)));
        Documento.add(new Paragraph("Le atendió: " + PrincipalController.Nombre));
        Table tabla = new Table(new float[]{2,1,1,1});
        StringTokenizer tokenizer = new StringTokenizer("Producto, Cantidad, Costo Unitario, Total", ",");
        while(tokenizer.hasMoreTokens()){
            tabla.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken())));
        }
        for(CajaRegistradoraController.ProductoVenta Producto : CajaRegistradoraController.lista){
            Double Total = Integer.parseInt(Producto.GetCantidad()) * Double.parseDouble(Producto.GetPrecio());
            tokenizer = new StringTokenizer(Producto.GetNombre() + "," + Producto.GetCantidad() + ",$" + Producto.GetPrecio() + ",$" + String.valueOf(Total), ",");
            while(tokenizer.hasMoreTokens()){
                tabla.addCell(new Cell().add(new Paragraph(tokenizer.nextToken())));
            }
        }
        Documento.add(tabla);
        Documento.add(new Paragraph("SubTotal: $" + TotalBruto));
        if(MetodoPago.isSelected()){
            Documento.add(new Paragraph("Usted utilizó " + String.valueOf(PuntosUtilizados) + " puntos como saldo"));
        }
        Documento.add(new Paragraph("Total: $" + df.format(Total)));
        Documento.add(new Paragraph("Usted pagó: $" + TxtPago.getText()));
        Documento.add(new Paragraph("Su cambio: $" + LblCambio.getText()));
        if(Id != 1){
            Documento.add(new Paragraph("Usted acumuló " + df.format((3*TotalBruto) / 100) + " puntos"));
            PreparedStatement statement = con.prepareStatement("select Puntos from Cliente where idCLiente = ?");
            statement.setString(1, TxtId.getText());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                Documento.add(new Paragraph("Usted posee " + rs.getString(1) + " puntos"));
            }

        }
        Documento.close();
    }

    public void CargarCambio(KeyEvent keyEvent) {
        if(keyEvent.getCode().equals(KeyCode.ENTER)){
            if(!"".equals(TxtPago.getText())){
                LblCambio.setText(df.format(Double.parseDouble(TxtPago.getText()) - Total));
                if(Double.parseDouble(LblCambio.getText()) >= 0 ){
                    BtnFin.setDisable(false);
                } else {
                    BtnFin.setDisable(true);
                }
            }
        }
    }

    public void Descuento(MouseEvent mouseEvent) {
        if(MetodoPago.isSelected()){
            if(puntos >= Total){
                Total = 0.0;
                LblTotal.setText("0.0");
                TxtPago.setDisable(true);
                BtnFin.setDisable(false);
            } else {
                Total = Total - puntos;
                LblTotal.setText(df.format(Total));
                TxtPago.clear();
                LblCambio.setText("0.0");
            }
        } else {
            Total = TotalBruto;
            LblTotal.setText(df.format(Total));
            TxtPago.clear();
            LblCambio.setText("0.0");
        }
    }

    public class Cliente extends RecursiveTreeObject<Cliente> {
        StringProperty Id, Nombre, Puntos;
        public Cliente(String Id, String Nombre, String Puntos){
            this.Id = new SimpleStringProperty(Id);
            this.Nombre = new SimpleStringProperty(Nombre);
            this.Puntos = new SimpleStringProperty(Puntos);
        }
        public StringProperty sGetId(){ return Id; }
        public StringProperty sGetNombre(){ return Nombre; }
        public StringProperty sGetPuntos(){ return Puntos; }
        public String GetId(){ return Id.get(); }
        public String GetNombre(){ return Nombre.get(); }
        public String GetPuntos(){ return Puntos.get(); }

    }
}
