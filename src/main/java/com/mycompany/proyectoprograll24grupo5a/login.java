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
    public boolean acceder(String usuario, String contra) {
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
                return true;
            }

            rs.close();
            stmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            miconexion.closeConnection(conn);
        }
        
        return false;
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
                   GestionCrear gestionCrear = new GestionCrear();  
                    gestionCrear.mostrargestionCrear();;
                    break;
                case 2:
                    GestionLeer gestionLeer = new GestionLeer();  
                    gestionLeer.mostrargestionLeer();
                    break;
                case 3:
                    GestionarActualizaciones gestionarActualizaciones = new GestionarActualizaciones();  
                    gestionarActualizaciones.mostrargestionarActualizaciones(); 
                    break;
                case 4:
                    GestionarEliminaciones gestionarEliminaciones = new GestionarEliminaciones();  
                    gestionarEliminaciones.mostrarGestionarElinimaciones();  
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

    private void gestionarSalida() {
        System.out.println("Saliendo del sistema.");
    }
}
