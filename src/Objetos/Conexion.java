package Objetos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    public static Connection getConnection() throws SQLException{
        Connection conexion = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Farmacia?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "root123");
        return conexion;
    }
}
