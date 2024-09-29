/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograll24grupo5a;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

class GestionarEliminaciones {
    private conexion conexion = new conexion();

    public void mostrarGestionarElinimaciones() {
        Scanner scanner = new Scanner(System.in);
        boolean continuarEliminar = true;

        while (continuarEliminar) {
            System.out.println("SUBMENÚ - ELIMINAR");
            System.out.println("1. ELIMINAR BITACORAS");
            System.out.println("2. ELIMINAR USUARIOS");
            System.out.println("3. ELIMINAR ROLES");
            System.out.println("4. ELIMINAR VENTAS");
            System.out.println("5. ELIMINAR DETALLE DE VENTA");
            System.out.println("6. ELIMINAR COMPRAS");
            System.out.println("7. ELIMINAR DETALLE DE COMPRA");
            System.out.println("8. ELIMINAR PRODUCTOS");
            System.out.println("9. ELIMINAR FACTURAS");
            System.out.println("10. ELIMINAR INVENTARIO");
            System.out.println("11. Salir del submenú");
            System.out.print("Seleccione una opción: ");

            int opcionEliminar = scanner.nextInt();
            scanner.nextLine();

            switch (opcionEliminar) {
                case 1:
                    eliminarBitacora(scanner);
                    break;
                case 2:
                    eliminarUsuario(scanner);
                    break;
                case 3:
                    eliminarRol(scanner);
                    break;
                case 4:
                    System.out.println("ELIMINAR VENTAS.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 5:
                    System.out.println("ELIMINAR DETALLE DE VENTA.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 6:
                    System.out.println("ELIMINAR COMPRA.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 7:
                    System.out.println("ELIMINAR DETALLE DE COMPRA.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 8:
                    System.out.println("ELIMINAR PRODUCTOS.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 9:
                    System.out.println("ELIMINAR FACTURAS");
                    confirmarRegresoMenu(scanner);
                    break;
                case 10:
                    System.out.println("ELIMINAR INVENTARIO");
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

    private void eliminarBitacora(Scanner scanner) {
        System.out.print("Ingrese el ID de la bitácora a eliminar: ");
        int bitacoraId = scanner.nextInt();

        try (Connection conn = conexion.getConnection()) {
            String sql = "DELETE FROM BitacoraAcceso WHERE bitacora_id = ?";
            try (var preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setInt(1, bitacoraId);
                int filasEliminadas = preparedStatement.executeUpdate();
                if (filasEliminadas > 0) {
                    System.out.println("Bitácora eliminada exitosamente.");
                } else {
                    System.out.println("No se encontró una bitácora con ID " + bitacoraId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarUsuario(Scanner scanner) {
    System.out.print("Ingrese el ID del usuario a eliminar: ");
    int usuarioId = scanner.nextInt();

    try (Connection conn = conexion.getConnection()) {
        String sqlVerificarBitacoras = "SELECT COUNT(*) FROM BitacoraAcceso WHERE usuario_id = ?";
        try (var preparedStatement = conn.prepareStatement(sqlVerificarBitacoras)) {
            preparedStatement.setInt(1, usuarioId);
            var resultado = preparedStatement.executeQuery();
            resultado.next();
            int conteoBitacoras = resultado.getInt(1);
            
            if (conteoBitacoras > 0) {
                System.out.println("No se puede eliminar el usuario porque hay " + conteoBitacoras + " registros en BitacoraAcceso asociados a él. Elimine primero esos registros.");
                return; 
            }
        }

        String sql = "DELETE FROM Usuarios WHERE usuario_id = ?";
        try (var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, usuarioId);
            int filasEliminadas = preparedStatement.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Usuario eliminado exitosamente.");
            } else {
                System.out.println("No se encontró un usuario con ID " + usuarioId);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

  private void eliminarRol(Scanner scanner) {
    System.out.print("Ingrese el ID del rol a eliminar: ");
    int rolId = scanner.nextInt();

    try (Connection conn = conexion.getConnection()) {
        String sqlVerificarUsuarios = "SELECT COUNT(*) FROM Usuarios WHERE rol_id = ?";
        try (var preparedStatement = conn.prepareStatement(sqlVerificarUsuarios)) {
            preparedStatement.setInt(1, rolId);
            var resultado = preparedStatement.executeQuery();
            resultado.next();
            int conteoUsuarios = resultado.getInt(1);
            
            if (conteoUsuarios > 0) {
                System.out.println("No se puede eliminar el rol porque hay " + conteoUsuarios + " usuarios asociados a él. Elimine primero los usuarios.");
                return; 
            }
        }

        String sql = "DELETE FROM Roles WHERE rol_id = ?";
        try (var preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, rolId);
            int filasEliminadas = preparedStatement.executeUpdate();
            if (filasEliminadas > 0) {
                System.out.println("Rol eliminado exitosamente.");
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
            System.out.println("12. Regresar al menú de eliminación");
            System.out.println("13. Regresar al menú CRUD principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 12:
                    continuar = false;
                    System.out.println("Regresando al submenú de ELIMINAR.");
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



