package Objetos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Producto extends RecursiveTreeObject<Producto>{

    StringProperty Cod,ID, Desc, Compra, Venta, Stock, Proveedor, Telefono;

    public Producto(String ID, String Desc, String Compra, String Venta, String Stock, String Proveedor, String Telefono){
        this.ID = new SimpleStringProperty(ID);
        this.Desc = new SimpleStringProperty(Desc);
        this.Compra = new SimpleStringProperty(Compra);
        this.Venta = new SimpleStringProperty(Venta);
        this.Stock = new SimpleStringProperty(Stock);
        this.Proveedor = new SimpleStringProperty(Proveedor);
        this.Telefono = new SimpleStringProperty(Telefono);
    }

    public Producto(int id,String sku,String desc,String cantidad,String compra ,String venta){
        this.Cod = new SimpleStringProperty(sku);
        this.ID = new SimpleStringProperty(String.valueOf(id));
        this.Desc = new SimpleStringProperty(desc);
        this.Stock = new SimpleStringProperty(cantidad);
        this.Compra = new SimpleStringProperty(compra);
        this.Venta = new SimpleStringProperty(venta);
    }

    public StringProperty sGetCod() {return Cod;}

    public String GetCod() { return Cod.get();}

    public void setStock(String Stock){this.Stock.set(Stock);}

    public String GetID(){
        return ID.get();
    }

    public StringProperty sGetID(){
        return ID;
    }

    public void SetID(StringProperty ID){
        this.ID = ID;
    }

    public String GetDesc(){
        return Desc.get();
    }

    public StringProperty sGetDesc(){
        return Desc;
    }

    public void SetDesc(StringProperty Desc){
        this.Desc = Desc;
    }

    public String GetCompra(){
        return Compra.get();
    }

    public StringProperty sGetCompra(){
        return this.Compra;
    }

    public void SetCompra(StringProperty Compra){
        this.Compra = Compra;
    }

    public String GetVenta(){
        return Venta.get();
    }

    public  StringProperty sGetVenta(){
        return this.Venta;
    }

    public void SetVenta(StringProperty Venta){
        this.Venta = Venta;
    }

    public String GetStock(){
        return Stock.get();
    }

    public StringProperty sGetStock(){
        return Stock;
    }

    public void SetSstock(String Stock){
        this.Stock = new SimpleStringProperty(Stock);
    }

    public String GetProveedor(){
        return Proveedor.get();
    }

    public StringProperty sGetProveedor(){
        return Proveedor;
    }

    public void SetProveedor(StringProperty Provee){
        this.Proveedor = Provee;
    }

    public String GetTel(){
        return Telefono.get();
    }

    public  StringProperty sGetTel(){
        return Telefono;
    }

    public void SetTel(StringProperty Tel){
        this.Telefono = Tel;
    }
}
