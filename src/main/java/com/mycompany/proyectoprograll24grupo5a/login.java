/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprograll24grupo5a;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

class login {
    public void acceder(String usuario, String contra) {
        conexion miconexion = new conexion();
        Connection conn = null;

        try {
            conn = miconexion.getConnection();

            String sql = "SELECT usuario_id, correo, nombre FROM Usuarios WHERE nombre = '" + usuario + "' AND contraseña = HashBytes('MD5', '" + contra + "')";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                int id = rs.getInt("usuario_id");
                String correo = rs.getString("correo");
                String nombre = rs.getString("nombre");
                System.out.println("Sesión iniciada: ID: " + id + ", correo: " + correo + ", nombre: " + nombre);
                
                mostrarMenuCRUD();
            } else {
                System.out.println("Credenciales Incorrectas.");
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            miconexion.closeConnection(conn);
        }
    }

    private void mostrarMenuCRUD() {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.println("QUE OPCION DESEAS HACER EN TU SISTEMA");
            System.out.println("1. Crear");
            System.out.println("2. Leer");
            System.out.println("3. Actualizar");
            System.out.println("4. Eliminar");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    System.out.println("Crear un nuevo registro (implementación pendiente).");
                    break;
                case 2:
                    System.out.println("Leer registros (implementación pendiente).");
                    break;
                case 3:
                    System.out.println("Actualizar un registro (implementación pendiente).");
                    break;
                case 4:
                    mostrarSubmenuEliminar(); // Llama al submenú de eliminación
                    break;
                case 5:
                    continuar = false;  
                    gestionarSalida();  
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void mostrarSubmenuEliminar() {
        Scanner scanner = new Scanner(System.in);
        boolean continuarEliminar = true;

        while (continuarEliminar) {
            System.out.println("SUBMENÚ - ELIMINAR");
            System.out.println("1. ELIMINAR BITACORAS");
            System.out.println("2. ELIMINAR USUARIOS");
            System.out.println("3. ELIMINAR ROLES");
            System.out.println("4. ELIMINAR VENTAS");
            System.out.println("5. ELIMINAR DETALLE DE VENTA");
            System.out.println("6. ELIMINAR COMPRAs");
            System.out.println("7. ELIMINAR DETALLE DE COMPRA");
            System.out.println("8. ELIMINAR PRODUCTOS");
            System.out.println("9. ELIMINAR FACTURAS");
            System.out.println("10. Salir del submenú");
            System.out.print("Seleccione una opción: ");
            
            int opcionEliminar = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcionEliminar) {
                case 1:
                    System.out.println("ELIMINAR BITACORAS.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 2:
                    System.out.println("ELIMINAR USUARIOS.");
                    confirmarRegresoMenu(scanner);
                    break;
                case 3:
                    System.out.println("ELIMINAR ROLES.");
                    confirmarRegresoMenu(scanner);
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
                    System.out.println("ELIMINAR DETALLE DE COMPRA .");
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
                    continuarEliminar = false;
                    System.out.println("Regresando al menú CRUD.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void confirmarRegresoMenu(Scanner scanner) {
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("¿Qué deseas hacer ahora?");
            System.out.println("1. Regresar al menú de eliminación");
            System.out.println("2. Regresar al menú CRUD principal");
            System.out.print("Seleccione una opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {
                case 1:
                    continuar = false; 
                    System.out.println("Regresando al submenú de eliminación.");
                    break;
                case 2:
                    continuar = false; 
                    System.out.println("Regresando al menú CRUD.");
                    mostrarMenuCRUD(); 
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void gestionarSalida() {
        Scanner scanner = new Scanner(System.in);
        boolean salir = true;

        while (salir) {
            System.out.println("¿Qué deseas Realizar Ahora?");
            System.out.println("1. Regresar al menú del CRUD");
            System.out.println("2. Volver al inicio de sesión");
            System.out.println("3. Salir del sistema");
            System.out.print("Seleccione una opción: ");
            
            int opcionSalida = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcionSalida) { 
                case 1:
                    System.out.println("Regresando al menú CRUD.");
                    salir = false;  
                    mostrarMenuCRUD();  
                    break;
                case 2:
                    System.out.println("Haz vuelto al inicio de sesión.");
                    salir = false;  
                    ProyectoPrograll24Grupo5A menu = new ProyectoPrograll24Grupo5A();
                    menu.mostrarMenu(); 
                    break;
                case 3:
                    System.out.println("Saliendo del Sistema, gracias por utilizar nuestro sistema.");
                    System.exit(0);  
                default:
                    System.out.println("Opción incorrecta. Intente de nuevo.");
            }
        }
    }
}
