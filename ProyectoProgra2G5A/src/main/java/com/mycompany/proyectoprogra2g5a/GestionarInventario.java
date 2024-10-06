/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprogra2g5a;

/**
 *
 * @author Dany Alexsis Tahual
 */
import gt.edu.umg.bd.Inventario;
import gt.edu.umg.bd.InventarioJpaController;
import gt.edu.umg.bd.Productos;
import gt.edu.umg.bd.exceptions.NonexistentEntityException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Scanner;

public class GestionarInventario {

    private EntityManagerFactory emf;

    public GestionarInventario(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void gestionarInventario(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n** Gestión de Inventario **");
            System.out.println("1. Crear Inventario");
            System.out.println("2. Leer Inventarios");
            System.out.println("3. Actualizar Inventario");
            System.out.println("4. Eliminar Inventario");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearInventario(scanner);
                    break;
                case 2:
                    leerInventarios();
                    break;
                case 3:
                    actualizarInventario(scanner);
                    break;
                case 4:
                    eliminarInventario(scanner);
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void crearInventario(Scanner scanner) {
        System.out.print("Ingrese la cantidad: ");
        Integer cantidad = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese el ID del producto: ");
        Integer productoId = scanner.nextInt();
        scanner.nextLine();

        Inventario nuevoInventario = new Inventario();
        nuevoInventario.setCantidad(cantidad);
        nuevoInventario.setFechaUltimaActualizacion(new java.util.Date());
        EntityManager em = emf.createEntityManager();
        InventarioJpaController inventarioController = new InventarioJpaController(emf);

        try {
            Productos producto = em.find(Productos.class, productoId);
            if (producto != null) {
                nuevoInventario.setProductoId(producto);
                inventarioController.create(nuevoInventario);
                System.out.println("Inventario creado con éxito.");
            } else {
                System.out.println("Error: Producto no encontrado con ID " + productoId);
            }
        } catch (Exception e) {
            System.out.println("Error al crear el inventario: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void leerInventarios() {
        EntityManager em = emf.createEntityManager();
        InventarioJpaController inventarioController = new InventarioJpaController(emf);

        try {
            List<Inventario> inventarios = inventarioController.findInventarioEntities();
            for (Inventario inventario : inventarios) {
                System.out.println("ID: " + inventario.getInventarioId()
                        + ", Cantidad: " + inventario.getCantidad()
                        + ", Producto ID: " + (inventario.getProductoId() != null ? inventario.getProductoId().getProductoId() : "No existe en el Sistema")
                        + ", Fecha Última Actualización: " + inventario.getFechaUltimaActualizacion());
            }
        } finally {
            em.close();
        }
    }

    private void actualizarInventario(Scanner scanner) {
        System.out.print("Ingrese el ID del inventario que desea actualizar: ");
        int idInventario = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        InventarioJpaController inventarioController = new InventarioJpaController(emf);

        try {
            Inventario inventario = inventarioController.findInventario(idInventario);

            if (inventario != null) {
                System.out.println("Inventario encontrado:");
                System.out.println("ID: " + inventario.getInventarioId()
                        + ", Cantidad: " + inventario.getCantidad());

                System.out.print("Ingrese la nueva cantidad (dejar vacío para no cambiar): ");
                String nuevaCantidadInput = scanner.nextLine();

                if (!nuevaCantidadInput.isEmpty()) {
                    Integer nuevaCantidad = Integer.parseInt(nuevaCantidadInput);
                    inventario.setCantidad(nuevaCantidad);
                }

                inventario.setFechaUltimaActualizacion(new java.util.Date());

                inventarioController.edit(inventario);
                System.out.println("Inventario actualizado con éxito.");
            } else {
                System.out.println("Error: Inventario no encontrado con el ID " + idInventario);
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar el inventario: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void eliminarInventario(Scanner scanner) {
        System.out.print("Ingrese el ID del inventario que desea eliminar: ");
        int idInventario = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        InventarioJpaController inventarioController = new InventarioJpaController(emf);

        try {
            inventarioController.destroy(idInventario);
            System.out.println("Inventario eliminado con éxito.");
        } catch (NonexistentEntityException e) {
            System.out.println("Error: El inventario no existe con el ID " + idInventario);
        } catch (Exception e) {
            System.out.println("Error al eliminar el inventario: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}

