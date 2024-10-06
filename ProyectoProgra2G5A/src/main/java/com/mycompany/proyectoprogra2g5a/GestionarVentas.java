/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprogra2g5a;

/**
 *
 * @author Dany Alexsis Tahual
 */
import gt.edu.umg.bd.Ventas;
import gt.edu.umg.bd.VentasJpaController;
import gt.edu.umg.bd.Usuarios;
import gt.edu.umg.bd.UsuariosJpaController;
import gt.edu.umg.bd.exceptions.NonexistentEntityException;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class GestionarVentas {

    private EntityManagerFactory emf;
    private VentasJpaController ventasJpaController;

    public GestionarVentas(EntityManagerFactory emf) {
        this.emf = emf;
        this.ventasJpaController = new VentasJpaController(emf);
    }

    public void gestionarVentas(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n*** Gestión de Ventas ***");
            System.out.println("1. Crear Venta");
            System.out.println("2. Leer Ventas");
            System.out.println("3. Actualizar Venta");
            System.out.println("4. Eliminar Venta");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada no válida. Intente nuevamente.");
                scanner.nextLine();
                continue;
            }

            switch (opcion) {
                case 1:
                    crearVenta(scanner);
                    break;
                case 2:
                    leerVentas();
                    break;
                case 3:
                    actualizarVenta(scanner);
                    break;
                case 4:
                    eliminarVenta(scanner);
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void crearVenta(Scanner scanner) {
        System.out.print("Ingrese el ID del usuario: ");
        int usuarioId = scanner.nextInt();
        Usuarios usuario = obtenerUsuarioPorId(usuarioId);

        if (usuario == null) {
            System.out.println("Usuario no encontrado. No se puede crear la venta.");
            return;
        }

        Ventas venta = new Ventas();
        venta.setUsuarioId(usuario);
        venta.setFechaVenta(new Date());

        System.out.print("Ingrese el monto de la venta: ");
        double monto = scanner.nextDouble();
        venta.setTotal(BigDecimal.valueOf(monto));

        try {
            ventasJpaController.create(venta);
            System.out.println("Venta creada exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al crear la venta.");
        }
    }

    public void leerVentas() {
        try {
            System.out.println("Intentando leer ventas...");
            List<Ventas> ventasList = ventasJpaController.findVentasEntities();

            if (ventasList.isEmpty()) {
                System.out.println("No hay ventas registradas.");
            } else {
                System.out.println("\n*** Lista de Ventas ***");
                for (Ventas venta : ventasList) {
                    System.out.println("ID: " + venta.getVentaId()
                            + ", Usuario: " + (venta.getUsuarioId() != null ? venta.getUsuarioId().getNombre() : "No existe en nuestro sistema")
                            + ", Total: " + venta.getTotal()
                            + ", Fecha: " + venta.getFechaVenta());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer las ventas: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void actualizarVenta(Scanner scanner) {
        System.out.print("Ingrese el ID de la venta a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            Ventas venta = ventasJpaController.findVentas(id);
            if (venta != null) {
                System.out.print("Ingrese el nuevo monto: ");
                double nuevoMonto = scanner.nextDouble();
                venta.setTotal(BigDecimal.valueOf(nuevoMonto));
                ventasJpaController.edit(venta);
                System.out.println("Venta actualizada exitosamente.");
            } else {
                System.out.println("No se encontró la venta con ID: " + id);
            }
        } catch (NonexistentEntityException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al actualizar la venta: " + e.getMessage());
        }
    }

    private void eliminarVenta(Scanner scanner) {
        System.out.print("Ingrese el ID de la venta a eliminar: ");
        int id = scanner.nextInt();

        try {
            ventasJpaController.destroy(id);
            System.out.println("Venta eliminada exitosamente.");
        } catch (NonexistentEntityException e) {
            System.out.println("No se encontró la venta con ID: " + id);
        } catch (Exception e) {
            System.out.println("Error al eliminar la venta: " + e.getMessage());
        }
    }

    private Usuarios obtenerUsuarioPorId(int usuarioId) {
        UsuariosJpaController usuariosJpaController = new UsuariosJpaController(emf);
        try {
            return usuariosJpaController.findUsuarios(usuarioId);
        } catch (Exception e) {
            System.out.println("Error al obtener el usuario: " + e.getMessage());
            return null;
        }
    }
}
