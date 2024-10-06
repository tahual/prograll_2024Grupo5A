/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprogra2g5a;

/**
 *
 * @author Dany Alexsis Tahual
 */
import gt.edu.umg.bd.DetalleVenta;
import gt.edu.umg.bd.DetalleVentaJpaController;
import gt.edu.umg.bd.Productos;
import gt.edu.umg.bd.ProductosJpaController;
import gt.edu.umg.bd.Ventas;
import gt.edu.umg.bd.VentasJpaController;
import gt.edu.umg.bd.exceptions.NonexistentEntityException;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

class GestionarDetalleVentas {

    private EntityManagerFactory emf;
    private DetalleVentaJpaController detalleVentaJpaController;
    private VentasJpaController ventasJpaController;
    private ProductosJpaController productosJpaController;

    public GestionarDetalleVentas(EntityManagerFactory emf) {
        this.emf = emf;
        this.detalleVentaJpaController = new DetalleVentaJpaController(emf);
        this.ventasJpaController = new VentasJpaController(emf);
        this.productosJpaController = new ProductosJpaController(emf);
    }

    public void gestionarDetalleVentas(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n*** Gestión de Detalle de Ventas ***");
            System.out.println("1. Crear Detalle de Venta");
            System.out.println("2. Leer Detalles de Ventas");
            System.out.println("3. Actualizar Detalle de Venta");
            System.out.println("4. Eliminar Detalle de Venta");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearDetalleVenta(scanner);
                    break;
                case 2:
                    leerDetalleVentas();
                    break;
                case 3:
                    actualizarDetalleVenta(scanner);
                    break;
                case 4:
                    eliminarDetalleVenta(scanner);
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void crearDetalleVenta(Scanner scanner) {
        System.out.print("Ingrese el ID de la venta: ");
        int ventaId = scanner.nextInt();
        Ventas venta = ventasJpaController.findVentas(ventaId);

        System.out.print("Ingrese el ID del producto: ");
        int productoId = scanner.nextInt();
        Productos producto = productosJpaController.findProductos(productoId);

        DetalleVenta detalleVenta = new DetalleVenta();
        detalleVenta.setVentaId(venta);
        detalleVenta.setProductoId(producto);

        System.out.print("Ingrese la cantidad: ");
        int cantidad = scanner.nextInt();
        detalleVenta.setCantidad(cantidad);

        System.out.print("Ingrese el precio unitario: ");
        detalleVenta.setPrecioUnitario(BigDecimal.valueOf(scanner.nextDouble()));

        try {
            System.out.println("Cantidad actual en inventario: " + producto.getCantidadEnInventario());
            if (producto.getCantidadEnInventario() >= cantidad) {
                producto.setCantidadEnInventario(producto.getCantidadEnInventario() - cantidad);
                System.out.println("Nueva cantidad después de la venta: " + producto.getCantidadEnInventario());
                productosJpaController.edit(producto);
                detalleVentaJpaController.create(detalleVenta);
                System.out.println("Detalle de venta creado exitosamente.");
            } else {
                System.out.println("No hay suficiente cantidad en inventario para realizar la venta.");
            }
        } catch (Exception e) {
            System.out.println("Error al crear detalle de venta: " + e.getMessage());
        }
    }

    public void leerDetalleVentas() {
        try {
            List<DetalleVenta> detalleVentasList = detalleVentaJpaController.findDetalleVentaEntities();

            if (detalleVentasList.isEmpty()) {
                System.out.println("No hay detalles de ventas registrados.");
            } else {
                for (DetalleVenta detalleVenta : detalleVentasList) {
                    System.out.println("ID: " + detalleVenta.getDetalleVentaId()
                            + ", Venta ID: " + detalleVenta.getVentaId().getVentaId()
                            + ", Producto: " + (detalleVenta.getProductoId() != null ? detalleVenta.getProductoId().getNombre() : "No existe en el Sistema")
                            + ", Cantidad: " + detalleVenta.getCantidad()
                            + ", Precio Unitario: " + detalleVenta.getPrecioUnitario()
                            + ", Subtotal: " + detalleVenta.getSubtotal());
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer los detalles de ventas: " + e.getMessage());
        }
    }

    private void actualizarDetalleVenta(Scanner scanner) {
        System.out.print("Ingrese el ID del detalle de venta a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            DetalleVenta detalleVenta = detalleVentaJpaController.findDetalleVenta(id);
            if (detalleVenta != null) {
                System.out.print("Ingrese la nueva cantidad: ");
                int nuevaCantidad = scanner.nextInt();
                detalleVenta.setCantidad(nuevaCantidad);

                System.out.print("Ingrese el nuevo precio unitario: ");
                double nuevoPrecioUnitario = scanner.nextDouble();
                detalleVenta.setPrecioUnitario(BigDecimal.valueOf(nuevoPrecioUnitario));

                detalleVentaJpaController.edit(detalleVenta);
                System.out.println("Detalle de venta actualizado exitosamente.");
            } else {
                System.out.println("No se encontró el detalle de venta con ID: " + id);
            }
        } catch (NonexistentEntityException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al actualizar el detalle de venta: " + e.getMessage());
        }
    }

    private void eliminarDetalleVenta(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del detalle de venta a eliminar: ");
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                scanner.nextLine();

                DetalleVenta detalleVenta = detalleVentaJpaController.findDetalleVenta(id);
                if (detalleVenta != null) {
                    detalleVentaJpaController.destroy(id);
                    System.out.println("Detalle de venta eliminado exitosamente.");
                } else {
                    System.out.println("No se encontró el detalle de venta con ID: " + id);
                }
            } else {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine();
            }
        } catch (NonexistentEntityException e) {
            System.out.println("Error: El detalle de venta no existe o ya fue eliminado.");
        } catch (Exception e) {
            System.out.println("Error al eliminar el detalle de venta: " + e.getMessage());
        }
    }
}

