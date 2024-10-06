/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectoprogra2g5a;

import gt.edu.umg.bd.Usuarios;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ProyectoProgra2G5A {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_ProyectoProgra2G5A_jar_1.0-SNAPSHOTPU");
        EntityManager em = emf.createEntityManager();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean loginExitoso = false;
            long idUsuarioActual = -1;

            System.out.println("\n*** Distribuidora Agricola***");
            System.out.println(" Ingrese sus credenciales para inciar sesión");

            while (!loginExitoso) {
                System.out.print("\nIngrese su correo electrónico: ");
                String email = scanner.nextLine();
                System.out.print("Ingrese su contraseña: ");
                String contraseña = scanner.nextLine();

                byte[] contraseñaEncriptada = encriptarContrasena(contraseña);

                TypedQuery<Usuarios> query = em.createQuery("SELECT u FROM Usuarios u WHERE u.correo = :correo", Usuarios.class);
                query.setParameter("correo", email);

                try {
                    Usuarios usuario = query.getSingleResult();

                    if (java.util.Arrays.equals(usuario.getContraseña(), contraseñaEncriptada)) {
                        System.out.println(" Bienvenido, " + usuario.getNombre() + "!");
                        idUsuarioActual = usuario.getUsuarioId();
                        loginExitoso = true;

                        MenuPrincipal(scanner, em, emf);
                    } else {
                        System.out.println("Error** Datos incorrectas. Intente Nuevamente.");
                    }
                } catch (Exception e) {
                    System.out.println("Error** Datos incorrectas. Intente Nuevamente.");
                }
            }
        } finally {
            if (em != null) {
                em.close();
            }
            if (emf != null) {
                emf.close();
            }
        }
    }

    public static byte[] encriptarContrasena(String contraseña) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(contraseña.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void MenuPrincipal(Scanner scanner, EntityManager em, EntityManagerFactory emf) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n*** Menú Principal***");
            System.out.println("1.  Gestionar Roles");
            System.out.println("2.  Gestionar Usuarios");
            System.out.println("3.  Gestionar BitacoraAcceso");
            System.out.println("4.  Gestionar Productos");
            System.out.println("5.  Gestionar Inventario");
            System.out.println("6.  Gestionar Ventas");
            System.out.println("7.  Gestionar DetalleVenta");
            System.out.println("8.  Gestionar Compras");
            System.out.println("9.  Gestionar DetalleCompra");
            System.out.println("10. Gestionar Facturas");
            System.out.println("11. Salir del Sistema");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    GestionarRoles gestionarRoles = new GestionarRoles(emf);
                    gestionarRoles.gestionarRoles(scanner);
                    break;
                case 2:
                    GestionarUsuarios gestionarUsuarios = new GestionarUsuarios(emf);
                    gestionarUsuarios.gestionarUsuarios(scanner);
                    break;
                case 3:
                    GestionarBitacoraAcceso gestionarBitacoraAcceso = new GestionarBitacoraAcceso(emf);
                    gestionarBitacoraAcceso.gestionarBitacoraAcceso(scanner);
                    break;
                case 4:
                    GestionarProductos gestionarProductos = new GestionarProductos(emf);
                    gestionarProductos.gestionarProductos(scanner);

                    break;
                case 5:
                    GestionarInventario gestionarInventario = new GestionarInventario(emf);
                    gestionarInventario.gestionarInventario(scanner);

                    break;
                case 6:
                    GestionarVentas gestionarVentas = new GestionarVentas(emf);
                    gestionarVentas.gestionarVentas(scanner);

                    break;
                case 7:
                    GestionarDetalleVentas gestionarDetalleVentas = new GestionarDetalleVentas(emf);
                    gestionarDetalleVentas.gestionarDetalleVentas(scanner);

                    break;
                case 8:
                    GestionarCompras gestionarCompras = new GestionarCompras(emf);
                    gestionarCompras.gestionarCompras(scanner);

                    break;
                case 9:
                    GestionarDetalleCompra gestionarDetalleCompra = new GestionarDetalleCompra(emf);
                    gestionarDetalleCompra.gestionarDetalleCompra(scanner);

                    break;
                case 10:
                    GestionarFacturas gestionarFacturas = new GestionarFacturas(emf);
                    gestionarFacturas.gestionarFacturas(scanner);

                    break;
                case 11:
                    System.out.println("Seseión cerrada");
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }
}