/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograll24grupo5a;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {
    private static final String DB_URL = "jdbc:sqlserver://LAPTOP-JPSJM2L6:1433;databaseName=Distribuidora Agricola;sslProtocol=TLSv1.2;trustServerCertificate=true";
    private static final String USUARIO = "sa";
    private static final String CONTRA = "41705232";

    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USUARIO, CONTRA);
            if (conn != null) {
                DatabaseMetaData dm = conn.getMetaData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


    

