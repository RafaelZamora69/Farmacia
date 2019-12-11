package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReporteController implements Initializable {

    @FXML
    private JFXDatePicker FechaDe;

    @FXML
    private JFXDatePicker FechaHasta;

    @FXML
    private JFXTreeTableView<ObjetoReporte> TableReporte;

    @FXML
    private Label Total;

    @FXML
    private JFXTextField TxtFiltro;

    @FXML
    private JFXButton BtnBuscar;

    @FXML
    private Label VentasTotales;

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
    private JFXTreeTableView<ObjetoDetalleReporte> TableDetalleVenta;

    @FXML
    private TreeTableColumn<ObjetoDetalleReporte, String> Detalle_IDVenta;

    @FXML
    private TreeTableColumn<ObjetoDetalleReporte, String> DetalleProducto;

    @FXML
    private TreeTableColumn<ObjetoDetalleReporte, String> DetallePrecio;

    @FXML
    private TreeTableColumn<ObjetoDetalleReporte, String> DetalleCantidad;

    @FXML
    private TreeTableColumn<ObjetoDetalleReporte, String> DetallePromocion;

    @FXML
    private JFXTreeTableView<ObjetoCompra> TableReporteCompra;

    @FXML
    private TreeTableColumn<ObjetoCompra, String> IDCompra;

    @FXML
    private TreeTableColumn<ObjetoCompra, String> Proveedor;

    @FXML
    private TreeTableColumn<ObjetoCompra, String> TotalCompra;

    @FXML
    private TreeTableColumn<ObjetoCompra, String> FechaCompra;

    @FXML
    private JFXTreeTableView<ObjetoDetalleCompra> TableDetalleCompra;

    @FXML
    private TreeTableColumn<ObjetoDetalleCompra, String> DtIDCompra;

    @FXML
    private TreeTableColumn<ObjetoDetalleCompra, String> ProductoCompra;

    @FXML
    private TreeTableColumn<ObjetoDetalleCompra, String> PrecioProducto;

    @FXML
    private TreeTableColumn<ObjetoDetalleCompra, String> Cantidad;

    @FXML
    private TreeTableColumn<ObjetoDetalleCompra, String> TotalProd;

    @FXML
    private JFXTextField TxtCompra;

    @FXML
    private Label lblTotalC;

    @FXML
    private JFXButton BtnCargarCompras;

    @FXML
    private Label LblTotalCompras;

    @FXML
    private JFXDatePicker FechaDeC;

    @FXML
    private JFXDatePicker FechaHastaC;

    @FXML
    private JFXTreeTableView<ObjetoUtilidad> TableUtilidad;

    @FXML
    private TreeTableColumn<ObjetoUtilidad, String> UtilIdVenta;

    @FXML
    private TreeTableColumn<ObjetoUtilidad, String> UtilProd;

    @FXML
    private TreeTableColumn<ObjetoUtilidad, String> UtilPCompra;

    @FXML
    private TreeTableColumn<ObjetoUtilidad, String> UtilPVenta;

    @FXML
    private TreeTableColumn<ObjetoUtilidad, String> UtilCantidad;

    @FXML
    private TreeTableColumn<ObjetoUtilidad, String> UtilTotal;

    @FXML
    private TreeTableColumn<ObjetoUtilidad, String> UtilUtilidad;

    @FXML
    private TreeTableColumn<ObjetoUtilidad, String> UtilPromo;

    @FXML
    private Label TotalUtil;

    @FXML
    private JFXDatePicker UtilDeFecha;

    @FXML
    private JFXDatePicker UtilHastaFecha;

    @FXML
    private JFXButton BtnCargarUtil;

    @FXML
    private LineChart<String, Number> ChartVentas;

    @FXML
    private AreaChart<String, Number> ChartUtil;

    @FXML
    private LineChart<String, Number> ChartCompras;

    @FXML
    private Tab ReporteVenta;
    @FXML
    private Tab ReporteCompra;
    @FXML
    private Tab ReporteUtil;

    private static final ObservableList<ObjetoReporte> LReporte = FXCollections.observableArrayList();
    private static final ObservableList<ObjetoDetalleReporte> LDReporte = FXCollections.observableArrayList();
    private static final ObservableList<ObjetoCompra> LCompra = FXCollections.observableArrayList();
    private static final ObservableList<ObjetoDetalleCompra> LDCompra = FXCollections.observableArrayList();
    private static final ObservableList<ObjetoUtilidad> LUtilidad = FXCollections.observableArrayList();
    private Connection con = Conexion.getConnection();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InicializarTablas();
        if(PrincipalController.Jerarquia.equals("Inventario")){
            ReporteVenta.setDisable(true);
            ReporteUtil.setDisable(true);
        } else if(PrincipalController.Jerarquia.equals("Encargado de turno")){
            ReporteUtil.setDisable(true);
        }
    }

    private void InicializarTablas() {
        //Tabla Reporte Venta
        IDVenta.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> param.getValue().getValue().sGetIdVenta());
        Vendedor.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> param.getValue().getValue().sGetVendedor());
        Fecha.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> param.getValue().getValue().sGetFecha());
        TotalVenta.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> param.getValue().getValue().sGetTotal());
        Cliente.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoReporte, String> param) -> param.getValue().getValue().sGetCliente());
        final TreeItem<ObjetoReporte> root = new RecursiveTreeItem<>(LReporte, RecursiveTreeObject::getChildren);
        this.TableReporte.setRoot(root);
        this.TableReporte.setShowRoot(false);
        //Filtro
        this.TxtFiltro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            TableReporte.setPredicate((TreeItem<ObjetoReporte> t) -> {
                Boolean flag = t.getValue().sGetIdVenta().getValue().contains(newValue);
                return flag;
            });
        });
        //Tabla Detalle de reporte Venta
        Detalle_IDVenta.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleReporte, String> param) -> param.getValue().getValue().GetIdVenta());
        DetalleProducto.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleReporte, String> param) -> param.getValue().getValue().GetDescripcion());
        DetallePrecio.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleReporte, String> param) -> param.getValue().getValue().GetPrecion());
        DetalleCantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleReporte, String> param) -> param.getValue().getValue().GetCantidad());
        DetallePromocion.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleReporte, String> param) -> param.getValue().getValue().GetPromocion());
        final TreeItem<ObjetoDetalleReporte> root2 = new RecursiveTreeItem<>(LDReporte, RecursiveTreeObject::getChildren);
        this.TableDetalleVenta.setRoot(root2);
        this.TableDetalleVenta.setShowRoot(false);
        //Filtro
        this.TxtFiltro.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            TableDetalleVenta.setPredicate((TreeItem<ObjetoDetalleReporte> t) -> {
                Boolean flag = t.getValue().GetIdVenta().getValue().contains(newValue);
                return flag;
            });
        });
        //Tabla Compra
        IDCompra.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoCompra, String> param) -> param.getValue().getValue().sGetIdCompra());
        Proveedor.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoCompra, String> param) -> param.getValue().getValue().sGetProveedor());
        TotalCompra.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoCompra, String> param) -> param.getValue().getValue().sGetTotal());
        FechaCompra.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoCompra, String> param) -> param.getValue().getValue().sGetFecha());
        final TreeItem<ObjetoCompra> root3 = new RecursiveTreeItem<>(LCompra, RecursiveTreeObject::getChildren);
        this.TableReporteCompra.setRoot(root3);
        this.TableReporteCompra.setShowRoot(false);
        //Filtro
        this.TxtCompra.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            TableReporteCompra.setPredicate((TreeItem<ObjetoCompra> t) -> {
                Boolean flag = t.getValue().sGetIdCompra().getValue().contains(newValue);
                return flag;
            });
        });
        //Tabla Reporte Compra
        DtIDCompra.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleCompra, String> param) -> param.getValue().getValue().sGetIdCompra());
        ProductoCompra.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleCompra, String> param) -> param.getValue().getValue().sGetProcuto());
        PrecioProducto.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleCompra, String> param) -> param.getValue().getValue().sGetPrecio());
        Cantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleCompra, String> param) -> param.getValue().getValue().sGetCantidad());
        TotalProd.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoDetalleCompra, String> param) -> param.getValue().getValue().sGetTotal());
        final TreeItem<ObjetoDetalleCompra> root4 = new RecursiveTreeItem<>(LDCompra, RecursiveTreeObject::getChildren);
        this.TableDetalleCompra.setRoot(root4);
        this.TableDetalleCompra.setShowRoot(false);
        //Filtro
        this.TxtCompra.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            TableDetalleCompra.setPredicate((TreeItem<ObjetoDetalleCompra> t) -> {
                Boolean flag = t.getValue().sGetIdCompra().getValue().contains(newValue);
                return flag;
            });
        });
        //Tabla Utilidades
        UtilIdVenta.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoUtilidad, String> param) -> param.getValue().getValue().sGetidVenta());
        UtilProd.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoUtilidad, String> param) -> param.getValue().getValue().sGetProducto());
        UtilPCompra.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoUtilidad, String> param) -> param.getValue().getValue().sGetPrecioCompra());
        UtilPVenta.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoUtilidad, String> param) -> param.getValue().getValue().sGetPrecioVenta());
        UtilCantidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoUtilidad, String> param) -> param.getValue().getValue().sGetCantidad());
        UtilTotal.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoUtilidad, String> param) -> param.getValue().getValue().sGetTotal());
        UtilUtilidad.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoUtilidad, String> param) -> param.getValue().getValue().sGetUtilidad());
        UtilPromo.setCellValueFactory((TreeTableColumn.CellDataFeatures<ObjetoUtilidad, String> param) -> param.getValue().getValue().sGetPromocion());
        final TreeItem<ObjetoUtilidad> root5 = new RecursiveTreeItem<>(LUtilidad, RecursiveTreeObject::getChildren);
        this.TableUtilidad.setRoot(root5);
        this.TableUtilidad.setShowRoot(false);
    }

    public void CargarReporteVentas() {
        LReporte.clear();
        try {
            PreparedStatement statement = con.prepareStatement("select count(*) from Venta where Fecha between ? and ?;");
            statement.setString(1, this.FechaDe.getValue().toString());
            statement.setString(2, this.FechaHasta.getValue().toString());
            ResultSet rs = statement.executeQuery();
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
            CargarDetalleVenta();
            GraficarVentas(LReporte);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void CargarDetalleVenta(){
        LDReporte.clear();
        try {
            for(ObjetoReporte Objeto : LReporte){
                PreparedStatement statement = con.prepareStatement("select Detalle_Venta.idVenta, Producto.Descripcion, Detalle_Venta.Precio_Venta, Detalle_Venta.Cantidad, Promocion.Descripcion\n" +
                        "from Detalle_Venta inner join Producto on Producto.idProducto = Detalle_Venta.idProducto\n" +
                        "inner join Promocion on Promocion.idPromocion = Detalle_Venta.Promocion\n" +
                        "where idVenta = ?;");
                statement.setString(1, Objeto.GetIdVenta());
                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    LDReporte.add(new ObjetoDetalleReporte(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    private void GraficarVentas(ObservableList<ObjetoReporte> LReporte){
        ChartVentas.getData().clear();
        XYChart.Series<String, Number> Venta = new XYChart.Series();
        Venta.setName("Total Ventas por día");
        String fecha = LReporte.get(0).GetFecha();
        Double Total = 0.0;
        for(ObjetoReporte Objeto : LReporte){
            if(Objeto.GetFecha().equals(fecha)){
                Total += Double.parseDouble(Objeto.GetTotal());
            } else {
                Venta.getData().add(new XYChart.Data(fecha, Total));
                fecha = Objeto.GetFecha();
                Total = Double.parseDouble(Objeto.GetTotal());
            }
        }
        Venta.getData().add(new XYChart.Data(fecha, Total));
        ChartVentas.getData().add(Venta);
        for(final XYChart.Data<String, Number> data : Venta.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Tooltip.install(data.getNode(), new Tooltip("Fecha: " + data.getXValue() + "\nTotal: $" + data.getYValue()));
                }
            });
        }
    }

    private void GraficarUtilidades(ObservableList<ObjetoUtilidad> Objeto){
        ChartUtil.getData().clear();
        XYChart.Series <String, Number> Compra = new XYChart.Series();
        XYChart.Series <String, Number> Venta = new XYChart.Series();
        XYChart.Series <String, Number> Utilidad = new XYChart.Series();
        Compra.setName("Total Compra");
        Venta.setName("Total Venta");
        Utilidad.setName("Utilidad");
        String fecha= Objeto.get(0).sGetFecha().get();
        Double TotalCompra = 0.0, TotalVenta = 0.0, Utilidades = 0.0;
        for(ObjetoUtilidad objeto : LUtilidad){
            if(objeto.sGetFecha().get().equals(fecha)){
                TotalCompra += Double.parseDouble(objeto.sGetPrecioCompra().get());
                TotalVenta += Double.parseDouble(objeto.sGetPrecioVenta().get());
            } else {
                Utilidades = TotalVenta - TotalCompra;
                Compra.getData().add(new XYChart.Data(fecha, TotalCompra));
                Venta.getData().add(new XYChart.Data(fecha, TotalVenta));
                Utilidad.getData().add(new XYChart.Data(fecha, Utilidades));
                fecha = objeto.sGetFecha().get();
                TotalCompra = Double.parseDouble(objeto.sGetPrecioCompra().get());
                TotalVenta = Double.parseDouble(objeto.sGetPrecioVenta().get());
            }
        }
        Utilidades = TotalVenta - TotalCompra;
        Compra.getData().add(new XYChart.Data(fecha, TotalCompra));
        Venta.getData().add(new XYChart.Data(fecha, TotalVenta));
        Utilidad.getData().add(new XYChart.Data(fecha, Utilidades));
        ChartUtil.getData().addAll(Compra, Venta, Utilidad);
    }

    private void GraficarCompras(ObservableList<ObjetoCompra> LCompra){
        ChartCompras.getData().clear();
        XYChart.Series<String, Number> Compra = new XYChart.Series();
        Compra.setName("Total de compras por día");
        String fecha = LCompra.get(0).GetFecha();
        Double Total = 0.0;
        for(ObjetoCompra Objeto : LCompra){
            if(Objeto.GetFecha().equals(fecha)){
                Total += Double.parseDouble(Objeto.GetTotal());
            } else {
                Compra.getData().add(new XYChart.Data(fecha, Total));
                fecha = Objeto.GetFecha();
                Total = Double.parseDouble(Objeto.GetTotal());
            }
        }
        Compra.getData().add(new XYChart.Data(fecha, Total));
        ChartCompras.getData().add(Compra);
        for(final XYChart.Data<String, Number> data : Compra.getData()){
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Tooltip.install(data.getNode(), new Tooltip("Fecha: " + data.getXValue() + "\nTotal: $" + data.getYValue()));
                }
            });
        }
    }

    public void CargarReporteCompras(MouseEvent mouseEvent) {
        LCompra.clear();
        try{
            PreparedStatement statement = con.prepareStatement("select count(*) from Compra where Compra.Fecha between ? and ?;");
            statement.setString(1, FechaDeC.getValue().toString());
            statement.setString(2, FechaHastaC.getValue().toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                LblTotalCompras.setText(rs.getString(1));
            }
            statement = con.prepareStatement("select Compra.idCompra, Proveedor.Nombre, Compra.Total_Compra, Compra.Fecha\n" +
                    "from Compra inner join Proveedor on Proveedor.idProveedor = Compra.idProveedor\n" +
                    "where Compra.Fecha between ? and ? ;");
            statement.setString(1, FechaDeC.getValue().toString());
            statement.setString(2, FechaHastaC.getValue().toString());
            rs = statement.executeQuery();
            while (rs.next()){
                LCompra.add(new ObjetoCompra(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
            GraficarCompras(LCompra);
            CargarDetalleCompra();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    private void CargarDetalleCompra(){
        Double Total = 0.0;
        LDCompra.clear();
        try {
            for(ObjetoCompra Objeto : LCompra){
                PreparedStatement statement = con.prepareStatement("select Detalle_Compra.idCompra, Producto.Descripcion, Detalle_Compra.Precio_Compra, Detalle_Compra.Cantidad\n" +
                        "from Detalle_Compra inner join Producto on Detalle_Compra.idProducto = Producto.idProducto\n" +
                        "where Detalle_Compra.idCompra = ?");
                statement.setString(1, Objeto.GetIdCompra());
                ResultSet rs = statement.executeQuery();
                while (rs.next()){
                    Total += Double.parseDouble(rs.getString(3)) * Integer.parseInt(rs.getString(4));
                    LDCompra.add(new ObjetoDetalleCompra(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), String.valueOf(Double.parseDouble(rs.getString(3)) * Integer.parseInt(rs.getString(4)))));
                }
            }
            this.lblTotalC.setText(String.valueOf(Total));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void CargarUtilidad(MouseEvent mouseEvent) {
        try {
            LUtilidad.clear();
            PreparedStatement statement = con.prepareStatement("SELECT idVenta, Descripcion, `Precio de compra`, `Precio de venta`, Cantidad, Total, Promocion, Utilidad, Fecha FROM farmacia.`reporte de utilidades` where Fecha between ? and ?;");
            statement.setString(1, this.UtilDeFecha.getValue().toString());
            statement.setString(2, this.UtilHastaFecha.getValue().toString());
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                LUtilidad.add(new ObjetoUtilidad(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9)));
            }
            statement = con.prepareStatement("select sum(Utilidad) from farmacia.`reporte de utilidades` where Fecha between ? and ?;");
            statement.setString(1, this.UtilDeFecha.getValue().toString());
            statement.setString(2, this.UtilHastaFecha.getValue().toString());
            rs = statement.executeQuery();
            while (rs.next()){
                this.TotalUtil.setText(rs.getString(1));
            }
            GraficarUtilidades(LUtilidad);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    class ObjetoReporte extends RecursiveTreeObject<ObjetoReporte> {
        StringProperty IdVenta, Vendedor, Fecha, Total, Cliente;

        public ObjetoReporte(String IdVenta, String Vendedor, String Fecha, String Total, String Cliente) {
            this.IdVenta = new SimpleStringProperty(IdVenta);
            this.Vendedor = new SimpleStringProperty(Vendedor);
            this.Fecha = new SimpleStringProperty(Fecha);
            this.Total = new SimpleStringProperty(Total);
            this.Cliente = new SimpleStringProperty(Cliente);
        }

        public String GetIdVenta() { return IdVenta.get(); }
        public String GetVendedor() { return Vendedor.get(); }
        public String GetFecha() { return Fecha.get(); }
        public String GetTotal() { return Total.get(); }
        public String GetCliente() { return Cliente.get(); }

        public StringProperty sGetIdVenta() { return IdVenta; }
        public StringProperty sGetVendedor() { return Vendedor; }
        public StringProperty sGetFecha() { return Fecha; }
        public StringProperty sGetTotal() { return Total; }
        public StringProperty sGetCliente() { return Cliente; }
    }

    class ObjetoDetalleReporte extends RecursiveTreeObject<ObjetoDetalleReporte> {
        StringProperty IdVenta, Descripcion, Precio, Cantidad, Promocion;

        public ObjetoDetalleReporte(String IdVenta, String Descripcion, String Precio, String Cantidad, String Promocion){
            this.IdVenta = new SimpleStringProperty(IdVenta);
            this.Descripcion = new SimpleStringProperty(Descripcion);
            this.Precio = new SimpleStringProperty(Precio);
            this.Cantidad = new SimpleStringProperty(Cantidad);
            this.Promocion = new SimpleStringProperty(Promocion);
        }

        public StringProperty GetIdVenta(){ return IdVenta; }
        public StringProperty GetDescripcion(){ return Descripcion; }
        public StringProperty GetPrecion(){ return Precio; }
        public StringProperty GetCantidad(){ return Cantidad; }
        public StringProperty GetPromocion(){ return Promocion; }
    }

    class ObjetoCompra extends RecursiveTreeObject<ObjetoCompra>{
        StringProperty IdCompra, Proveedor, Total, Fecha;

        public ObjetoCompra(String IdCompra, String Proveedor, String Total, String Fecha){
            this.IdCompra = new SimpleStringProperty(IdCompra);
            this.Proveedor = new SimpleStringProperty(Proveedor);
            this.Total = new SimpleStringProperty(Total);
            this.Fecha = new SimpleStringProperty(Fecha);
        }

        public String GetIdCompra(){ return IdCompra.get(); }
        public String GetProveedor(){ return Proveedor.get(); }
        public String GetTotal(){ return Total.get(); }
        public String GetFecha(){ return Fecha.get(); }

        public StringProperty sGetIdCompra(){return IdCompra; }
        public StringProperty sGetProveedor(){ return Proveedor; }
        public StringProperty sGetTotal(){ return Total; }
        public StringProperty sGetFecha(){ return Fecha; }

    }

    class ObjetoDetalleCompra extends  RecursiveTreeObject<ObjetoDetalleCompra>{
        StringProperty IdCompra, Producto, Precio, Cantidad, Total;

        public ObjetoDetalleCompra(String IdCompra, String Producto, String Precio, String Cantidad, String Total){
            this.IdCompra = new SimpleStringProperty(IdCompra);
            this.Producto = new SimpleStringProperty(Producto);
            this.Precio = new SimpleStringProperty(Precio);
            this.Cantidad = new SimpleStringProperty(Cantidad);
            this.Total = new SimpleStringProperty(Total);
        }

        public String GetIdCompra(){ return IdCompra.get(); }
        public String GetProducto(){ return Producto.get(); }
        public String GetPrecio(){ return Precio.get(); }
        public String GetCantidad(){ return Cantidad.get(); }
        public String GetTotal(){ return Total.get(); }

        public StringProperty sGetIdCompra(){return IdCompra; }
        public StringProperty sGetProcuto(){return Producto; }
        public StringProperty sGetPrecio(){return Precio; }
        public StringProperty sGetCantidad(){return Cantidad; }
        public StringProperty sGetTotal(){return Total; }
    }

    class ObjetoUtilidad extends RecursiveTreeObject<ObjetoUtilidad>{
        StringProperty idVenta, Producto, PrecioCompra, PrecioVenta, Cantidad, Total, Promocion, Utilidad, Fecha;

        public ObjetoUtilidad(String idVenta, String Producto, String PrecioCompra, String PrecioVenta, String Cantidad, String Total, String Promocion, String Utilidad, String Fecha){
            this.idVenta = new SimpleStringProperty(idVenta);
            this.Producto = new SimpleStringProperty(Producto);
            this.PrecioCompra = new SimpleStringProperty(PrecioCompra);
            this.PrecioVenta = new SimpleStringProperty(PrecioVenta);
            this.Cantidad = new SimpleStringProperty(Cantidad);
            this.Total = new SimpleStringProperty(Total);
            this.Promocion = new SimpleStringProperty(Promocion);
            this.Utilidad = new SimpleStringProperty(Utilidad);
            this.Fecha = new SimpleStringProperty(Fecha);
        }

        public StringProperty sGetidVenta(){ return idVenta; }
        public StringProperty sGetProducto(){ return Producto; }
        public StringProperty sGetPrecioCompra(){ return PrecioCompra; }
        public StringProperty sGetPrecioVenta(){ return PrecioVenta; }
        public StringProperty sGetCantidad(){ return Cantidad; }
        public StringProperty sGetTotal(){ return Total; }
        public StringProperty sGetPromocion(){ return Promocion; }
        public StringProperty sGetUtilidad(){ return Utilidad; }
        public StringProperty sGetFecha(){ return Fecha; }
    }

}
