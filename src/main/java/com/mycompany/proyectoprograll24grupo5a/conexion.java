/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograll24grupo5a;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Marlon Cuco
 */
class conexion {
    private static final String DB_URL = "jdbc:sqlserver://LAPTOP-JPSJM2L6:1433;databaseName=Distribuidora Agricola;sslProtocol=TLSv1.2;trustServerCertificate=true";
    private static final String USER = "sa";
    private static final String PASS = "41705232";

    
    public Connection getConnection() {
       
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (conn != null) {
                System.out.println("Conexión establecida exitosamente.");
                DatabaseMetaData dm = conn.getMetaData();
//                System.out.println("Driver name: " + dm.getDriverName());
//                System.out.println("Driver version: " + dm.getDriverVersion());
//                System.out.println("Product name: " + dm.getDatabaseProductName());
//                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
        } catch (SQLException e) {
            System.out.println("Error al establecer la conexión:");
            e.printStackTrace();
        }
        return conn;
    }

   
    public void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexión cerrada exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión:");
            e.printStackTrace();
        }
    }
    
    
}

    

