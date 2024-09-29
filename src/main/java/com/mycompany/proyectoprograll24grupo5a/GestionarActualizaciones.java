/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograll24grupo5a;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

class GestionarActualizaciones {
    private conexion conexion = new conexion();

    public void mostrargestionarActualizaciones() {
        Scanner scanner = new Scanner(System.in);
        boolean continuarEliminar = true;

        while (continuarEliminar) {
            System.out.println("SUBMENÚ - ACTUALIZAR");
            System.out.println("1. ACTUALIZAR BITACORAS");
            System.out.println("2. ACTUALIZAR USUARIOS");
            System.out.println("3. ACTUALIZAR ROLES");
            System.out.println("4. ACTUALIZAR VENTAS");
            System.out.println("5. ACTUALIZAR DETALLE DE VENTA");
            System.out.println("6. ACTUALIZAR COMPRAS");
            System.out.println("7. ACTUALIZAR DETALLE DE COMPRA");
            System.out.println("8. ACTUALIZAR PRODUCTOS");
            System.out.println("9. ACTUALIZAR FACTURAS");
            System.out.println("10. ACTUALIZAR INVENTARIO");
            System.out.println("11. Salir del submenú");
            System.out.print("Seleccione una opción: ");

            int opcionEliminar = scanner.nextInt();
            scanner.nextLine();

            switch (opcionEliminar) {
                case 1:
                    actualizarBitacora(scanner);
                    break;
                case 2:
                    actualizarUsuario(scanner);
                    break;
                case 3:
                    actualizarRol(scanner);
                    break;
                case 4:
                    System.out.println("ACTUALIZAR VENTAS.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 5:
                    System.out.println("ACTUALIZAR DETALLE DE VENTA.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 6:
                    System.out.println("ACTUALIZAR COMPRA.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 7:
                    System.out.println("ACTUALIZAR DETALLE DE COMPRA.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 8:
                    System.out.println("ACTUALIZAR PRODUCTOS.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 9:
                    System.out.println("ACTUALIZAR FACTURAS.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 10:
                    System.out.println("ACTUALIZAR INVENTARIO.");
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

private void actualizarBitacora(Scanner scanner) {
    System.out.print("Ingrese el ID del usuario: ");
    int usuarioId = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Ingrese la nueva acción de la bitácora: ");
    String nuevaAccion = scanner.nextLine();

    try (Connection conn = conexion.getConnection()) {
        String sql = "UPDATE BitacoraAcceso SET accion = ?, fecha_acceso = GETDATE() WHERE usuario_id = ?";
        try (var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, nuevaAccion);
            preparedStatement.setInt(2, usuarioId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Bitácora actualizada exitosamente.");
                System.out.println("Nueva acción: " + nuevaAccion);
            } else {
                System.out.println("No se encontró un registro con el ID de usuario especificado.");
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


private void actualizarUsuario(Scanner scanner) {
    System.out.print("Ingrese el ID del usuario a actualizar: ");
    int usuarioId = scanner.nextInt();
    scanner.nextLine();
    System.out.print("Ingrese el nuevo nombre del usuario ***dejar vacío si no desea cambiarlo***: ");
    String nuevoNombre = scanner.nextLine();
    System.out.print("Ingrese el nuevo correo del usuario ***dejar vacío si no desea cambiarlo***: ");
    String nuevoCorreo = scanner.nextLine();
    System.out.print("Ingrese la nueva contraseña del usuario ***dejar vacío si no desea cambiarlo***: ");
    String nuevaContraseña = scanner.nextLine();
    System.out.print("Ingrese el nuevo ID del rol ***dejar vacío si no desea cambiarlo***: ");
    String nuevoRolIdInput = scanner.nextLine();
    Integer nuevoRolId = nuevoRolIdInput.isEmpty() ? null : Integer.parseInt(nuevoRolIdInput);

    try (Connection conn = conexion.getConnection()) {
        StringBuilder sql = new StringBuilder("UPDATE Usuarios SET ");
        boolean cambios = false;

        if (!nuevoNombre.isEmpty()) {
            sql.append("nombre = ?, ");
            cambios = true;
        }
        if (!nuevoCorreo.isEmpty()) {
            sql.append("correo = ?, ");
            cambios = true;
        }
        if (!nuevaContraseña.isEmpty()) {
            sql.append("contraseña = ?, ");
            cambios = true;
        }
        if (nuevoRolId != null) {
            sql.append("rol_id = ? ");
            cambios = true;
        }

        if (cambios) {
            sql.append("WHERE usuario_id = ?");

            try (var preparedStatement = conn.prepareStatement(sql.toString())) {
                int index = 1;
                if (!nuevoNombre.isEmpty()) {
                    preparedStatement.setString(index++, nuevoNombre);
                }
                if (!nuevoCorreo.isEmpty()) {
                    preparedStatement.setString(index++, nuevoCorreo);
                }
                if (!nuevaContraseña.isEmpty()) {
                    preparedStatement.setBytes(index++, nuevaContraseña.getBytes());
                }
                if (nuevoRolId != null) {
                    preparedStatement.setInt(index++, nuevoRolId);
                }
                preparedStatement.setInt(index, usuarioId);

                int filasActualizadas = preparedStatement.executeUpdate();
                if (filasActualizadas > 0) {
                    System.out.println("Usuario actualizado exitosamente.");
                } else {
                    System.out.println("No se encontró el usuario con ID " + usuarioId);
                }
            }
        } else {
            System.out.println("No se realizaron cambios en el usuario.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


private void actualizarRol(Scanner scanner) {
    System.out.print("Ingrese el ID del rol a actualizar: ");
    int rolId = scanner.nextInt();
    scanner.nextLine();
    
    System.out.print("Ingrese el nuevo nombre del rol: ");
    String nuevoNombreRol = scanner.nextLine();

    try (Connection conn = conexion.getConnection()) {
        String sql = "UPDATE Roles SET nombre = ? WHERE rol_id = ?";
        try (var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, nuevoNombreRol);
            preparedStatement.setInt(2, rolId);

            int filasActualizadas = preparedStatement.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Rol actualizado exitosamente.");
            } else {
                System.out.println("No se encontró un rol con ID " + rolId);
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
        System.out.println("12. Regresar al menú de ACTUALIZAR");
        System.out.println("13. Regresar al menú CRUD principal");
        System.out.print("Seleccione una opción: ");

        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 12:
                continuar = false;
                System.out.println("Regresando al submenú de ACTUALIZAR.");
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