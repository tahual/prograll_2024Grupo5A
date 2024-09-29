/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograll24grupo5a;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class GestionLeer {

    private conexion conexion = new conexion();

    public void mostrargestionLeer() {
        Scanner scanner = new Scanner(System.in);
        boolean continuarLeer = true;

        while (continuarLeer) {
            System.out.println("SUBMENÚ - LEER");
            System.out.println("1. LEER BITACORAS");
            System.out.println("2. LEER USUARIOS");
            System.out.println("3. LEER ROLES");
            System.out.println("4. LEER VENTAS");
            System.out.println("5. LEER DETALLE DE VENTA");
            System.out.println("6. LEER COMPRAS");
            System.out.println("7. LEER DETALLE DE COMPRA");
            System.out.println("8. LEER PRODUCTOS");
            System.out.println("9. LEER FACTURAS");
            System.out.println("10. LEER INVENTARIO");
            System.out.println("11. Salir del submenú");
            System.out.print("Seleccione una opción: ");

            int opcionLeer = scanner.nextInt();
            scanner.nextLine();

            switch (opcionLeer) {
                case 1:
                    leerBitacoras();
                    break;
                case 2:
                    leerUsuarios();
                    break;
                case 3:
                    leerRoles();
                    break;
                case 4:
                    System.out.println("LEER VENTAS.");
                    break;
                case 5:
                    System.out.println("LEER DETALLE DE VENTA.");
                    break;
                case 6:
                    System.out.println("LEER COMPRAS.");
                    break;
                case 7:
                    System.out.println("LEER DETALLE DE COMPRA.");
                    break;
                case 8:
                    System.out.println("LEER PRODUCTOS.");
                    break;
                case 9:
                    System.out.println("LEER FACTURAS.");
                    break;
                case 10:
                    System.out.println("LEER INVENTARIO.");
                    break;
                case 11:
                    continuarLeer = false;
                    System.out.println("Regresando al menú CRUD.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }

            if (continuarLeer) {
                confirmarRegresoMenu(scanner);
            }
        }
    }

    private void leerBitacoras() {
    try (Connection conn = conexion.getConnection();
         Statement statement = conn.createStatement()) {

        String sql = "SELECT * FROM BitacoraAcceso";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("bitacora_id") + 
                               ", Usuario ID: " + resultSet.getInt("usuario_id") + 
                               ", Fecha de Acceso: " + resultSet.getTimestamp("fecha_acceso") +
                               ", Acción: " + resultSet.getString("accion"));
        }
    } catch (SQLException e) {
        System.out.println("Error al leer las bitácoras: " + e.getMessage());
    }
}


    private void leerUsuarios() {
    try (Connection conn = conexion.getConnection();
         Statement statement = conn.createStatement()) {

        String sql = "SELECT * FROM Usuarios";
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("usuario_id") + 
                               ", Nombre: " + resultSet.getString("nombre") + 
                               ", Correo: " + resultSet.getString("correo"));
        }
    } catch (SQLException e) {
        System.out.println("Error al leer los usuarios: " + e.getMessage());
    }
}

    private void leerRoles() {
        try (Connection conn = conexion.getConnection();
             Statement statement = conn.createStatement()) {

            String sql = "SELECT * FROM Roles";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println("Rol ID: " + resultSet.getInt("rol_id") + 
                                   ", Nombre: " + resultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            System.out.println("Error al leer los roles: " + e.getMessage());
        }
    }

    private void confirmarRegresoMenu(Scanner scanner) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("¿Qué deseas hacer ahora?");
            System.out.println("12. Regresar al submenú LEER");
            System.out.println("13. Regresar al menú CRUD principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 12:
                    continuar = false;
                    System.out.println("Regresando al submenú.");
                    break;
                case 13:
                    continuar = false;
                    System.out.println("Regresando al menú CRUD.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}

