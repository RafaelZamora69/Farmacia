package Objetos;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Persona extends RecursiveTreeObject<Persona> {
    StringProperty idEmpleado, idCliente, Nombre, Telefono, Direccion, Edad, Puesto, Puntos, Rfc;

    public Persona(String idEmpleado, String idCliente, String Nombre, String Telefono, String Direccion, String Edad, String Puesto, String Puntos, String Rfc){
        this.idEmpleado = new SimpleStringProperty(idEmpleado);
        this.idCliente = new SimpleStringProperty(idCliente);
        this.Nombre = new SimpleStringProperty(Nombre);
        this.Telefono = new SimpleStringProperty(Telefono);
        this.Direccion = new SimpleStringProperty(Direccion);
        this.Edad = new SimpleStringProperty(Edad);
        this.Puesto = new SimpleStringProperty(Puesto);
        this.Puntos = new SimpleStringProperty(Puntos);
        this.Rfc = new SimpleStringProperty(Rfc);
    }

    public String GetIdEmpleado(){
        return idEmpleado.get();
    }
    public String GetIdCliente(){
        return idCliente.get();
    }
    public String GetNombre(){
        return Nombre.get();
    }
    public String GetTelefono(){
        return Telefono.get();
    }
    public String GetDireccion(){
        return Direccion.get();
    }
    public String GetEdad(){
        return Edad.get();
    }
    public String GetPuesto(){
        return Puesto.get();
    }
    public String GetPuntos(){
        return Puntos.get();
    }
    public String GetRfc(){
        return Rfc.get();
    }

    public StringProperty sGetIdEmpleado() { return idEmpleado; }
    public StringProperty sGetIdCliente(){ return idCliente; }
    public StringProperty sGetNombre(){ return Nombre; }
    public StringProperty sGetTelefono(){ return Telefono; }
    public StringProperty sGetDireccion(){ return Direccion; }
    public StringProperty sGetEdad(){ return Edad; }
    public StringProperty sGetPuesto(){ return Puesto; }
    public StringProperty sGetPuntos(){ return Puntos; }
    public StringProperty sGetRfc(){ return Rfc; }
}
