/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograll24grupo5a;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

class GestionCrear {

    private conexion conexion = new conexion();

    public void mostrargestionCrear() {
        Scanner scanner = new Scanner(System.in);
        boolean continuarEliminar = true;

        while (continuarEliminar) {
            System.out.println("SUBMENÚ - CREAR");
            System.out.println("1. CREAR BITACORAS");
            System.out.println("2. CREAR USUARIOS");
            System.out.println("3. CREAR ROLES");
            System.out.println("4. CREAR VENTAS");
            System.out.println("5. CREAR DETALLE DE VENTA");
            System.out.println("6. CREAR COMPRAS");
            System.out.println("7. CREAR DETALLE DE COMPRA");
            System.out.println("8. CREAR PRODUCTOS");
            System.out.println("9. CREAR FACTURAS");
            System.out.println("10. CREAR INVENTARIO");
            System.out.println("11. Salir del submenú");
            System.out.print("Seleccione una opción: ");

            int opcionEliminar = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcionEliminar) {
                case 1:
                    crearBitacora(scanner);
                    break;
                case 2:
                    crearUsuario(scanner);
                    break;
                case 3:
                    crearRol(scanner);
                    break;
                case 4:
                    System.out.println("CREAR VENTAS.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 5:
                    System.out.println("CREAR DETALLE DE VENTA.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 6:
                    System.out.println("CREAR COMPRA.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 7:
                    System.out.println("CREAR DETALLE DE COMPRA.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 8:
                    System.out.println("CREAR PRODUCTOS.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 9:
                    System.out.println("CREAR FACTURAS");
                    confirmarRegresoMenu(scanner);
                    break;
                case 10:
                    System.out.println("CREAR INVENTARIO");
                    confirmarRegresoMenu(scanner);
                    break;
                case 11:
                    continuarEliminar = false;
                    System.out.println("Regresando al menú CRUD.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
private void crearBitacora(Scanner scanner) {
    System.out.print("Ingrese el ID del usuario: ");
    int usuarioId = scanner.nextInt();
    scanner.nextLine(); 
    System.out.print("Ingrese la acción de la bitácora: ");
    String accion = scanner.nextLine();

    try (Connection conn = conexion.getConnection()) {
        String checkSql = "SELECT COUNT(*) FROM BitacoraAcceso WHERE usuario_id = ? AND accion = ?";
        try (var checkStatement = conn.prepareStatement(checkSql)) {
            checkStatement.setInt(1, usuarioId);
            checkStatement.setString(2, accion);
            var resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                System.out.println("La bitácora ya existe.");
            } else {
                String sql = "INSERT INTO BitacoraAcceso (usuario_id, accion) VALUES (?, ?)";
                try (var preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setInt(1, usuarioId);
                    preparedStatement.setString(2, accion);
                    preparedStatement.executeUpdate();
                }
                System.out.println("Bitácora creada exitosamente.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    private void crearUsuario(Scanner scanner) {
    System.out.print("Ingrese el nombre del usuario: ");
    String nombre = scanner.nextLine();
    System.out.print("Ingrese el correo del usuario: ");
    String correo = scanner.nextLine();
    System.out.print("Ingrese la contraseña del usuario: ");
    String contraseña = scanner.nextLine();
    System.out.print("Ingrese el ID del rol: ");
    int rolId = scanner.nextInt();

    byte[] contraseñaBytes = contraseña.getBytes();

    try (Connection conn = conexion.getConnection()) {
        String checkSql = "SELECT COUNT(*) FROM Usuarios WHERE correo = ?";
        try (var checkStatement = conn.prepareStatement(checkSql)) {
            checkStatement.setString(1, correo);
            var resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                System.out.println("El usuario con este correo ya existe.");
            } else {
                String sql = "INSERT INTO Usuarios (nombre, correo, contraseña, rol_id) VALUES (?, ?, ?, ?)";
                try (var preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, nombre);
                    preparedStatement.setString(2, correo);
                    preparedStatement.setBytes(3, contraseñaBytes);
                    preparedStatement.setInt(4, rolId);
                    preparedStatement.executeUpdate();
                }
                System.out.println("Usuario creado exitosamente.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

private void crearRol(Scanner scanner) {
    System.out.print("Ingrese el nombre del rol: ");
    String nombreRol = scanner.nextLine();

    try (Connection conn = conexion.getConnection()) {
        String checkSql = "SELECT COUNT(*) FROM Roles WHERE nombre = ?";
        try (var checkStatement = conn.prepareStatement(checkSql)) {
            checkStatement.setString(1, nombreRol);
            var resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0) {
                System.out.println("El rol ya existe.");
                System.out.print("¿Desea agregar un nuevo rol? (s/n): ");
                String respuesta = scanner.nextLine();
                if (respuesta.equalsIgnoreCase("s")) {
                    crearRol(scanner);
                }
            } else {
                String sql = "INSERT INTO Roles (nombre) VALUES (?)";
                try (var preparedStatement = conn.prepareStatement(sql)) {
                    preparedStatement.setString(1, nombreRol);
                    preparedStatement.executeUpdate();
                }
                System.out.println("Rol creado exitosamente.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    private void confirmarRegresoMenu(Scanner scanner) {
        boolean continuar = true;

        while (continuar) {
            System.out.println("¿Qué deseas hacer ahora?");
            System.out.println("12. Regresar al submenú CREAR");
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

