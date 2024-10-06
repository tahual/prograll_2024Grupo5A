/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprogra2g5a;

/**
 *
 * @author German Hernández
 */
import gt.edu.umg.bd.DetalleCompra;
import gt.edu.umg.bd.DetalleCompraJpaController;
import gt.edu.umg.bd.Productos;
import gt.edu.umg.bd.ProductosJpaController;
import gt.edu.umg.bd.Compras;
import gt.edu.umg.bd.ComprasJpaController;
import gt.edu.umg.bd.exceptions.NonexistentEntityException;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

class GestionarDetalleCompra {

    private EntityManagerFactory emf;
    private DetalleCompraJpaController detalleCompraJpaController;
    private ComprasJpaController comprasJpaController;
    private ProductosJpaController productosJpaController;

    public GestionarDetalleCompra(EntityManagerFactory emf) {
        this.emf = emf;
        this.detalleCompraJpaController = new DetalleCompraJpaController(emf);
        this.comprasJpaController = new ComprasJpaController(emf);
        this.productosJpaController = new ProductosJpaController(emf);
    }

    public void gestionarDetalleCompra(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n*** Gestión de Detalle de Compras ***");
            System.out.println("1. Crear Detalle de Compra");
            System.out.println("2. Leer Detalles de Compras");
            System.out.println("3. Actualizar Detalle de Compra");
            System.out.println("4. Eliminar Detalle de Compra");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearDetalleCompra(scanner);
                    break;
                case 2:
                    leerDetalleCompras();
                    break;
                case 3:
                    actualizarDetalleCompra(scanner);
                    break;
                case 4:
                    eliminarDetalleCompra(scanner);
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void crearDetalleCompra(Scanner scanner) {
        System.out.print("Ingrese el ID de la compra: ");
        int compraId = scanner.nextInt();
        Compras compra = comprasJpaController.findCompras(compraId);

        System.out.print("Ingrese el ID del producto: ");
        int productoId = scanner.nextInt();
        Productos producto = productosJpaController.findProductos(productoId);

        DetalleCompra detalleCompra = new DetalleCompra();
        detalleCompra.setCompraId(compra);
        detalleCompra.setProductoId(producto);

        System.out.print("Ingrese la cantidad: ");
        int cantidad = scanner.nextInt();
        detalleCompra.setCantidad(cantidad);

        System.out.print("Ingrese el precio unitario: ");
        detalleCompra.setPrecioUnitario(BigDecimal.valueOf(scanner.nextDouble()));

        try {
            detalleCompraJpaController.create(detalleCompra);
            System.out.println("Detalle de compra creado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al crear detalle de compra: " + e.getMessage());
        }
    }

    public void leerDetalleCompras() {
        try {
            List<DetalleCompra> detalleComprasList = detalleCompraJpaController.findDetalleCompraEntities();

            if (detalleComprasList.isEmpty()) {
                System.out.println("No hay detalles de compras registrados.");
            } else {
                for (DetalleCompra detalleCompra : detalleComprasList) {
                    BigDecimal subtotal = detalleCompra.getPrecioUnitario().multiply(BigDecimal.valueOf(detalleCompra.getCantidad()));
                    System.out.println("ID: " + detalleCompra.getDetalleCompraId()
                            + ", Compra ID: " + detalleCompra.getCompraId().getCompraId()
                            + ", Producto: " + (detalleCompra.getProductoId() != null ? detalleCompra.getProductoId().getNombre() : "No existe en el Sistema")
                            + ", Cantidad: " + detalleCompra.getCantidad()
                            + ", Precio Unitario: " + detalleCompra.getPrecioUnitario()
                            + ", Subtotal: " + subtotal);
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer los detalles de compras: " + e.getMessage());
        }
    }

    private void actualizarDetalleCompra(Scanner scanner) {
        System.out.print("Ingrese el ID del detalle de compra a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            DetalleCompra detalleCompra = detalleCompraJpaController.findDetalleCompra(id);
            if (detalleCompra != null) {
                System.out.print("Ingrese la nueva cantidad: ");
                int nuevaCantidad = scanner.nextInt();
                detalleCompra.setCantidad(nuevaCantidad);

                System.out.print("Ingrese el nuevo precio unitario: ");
                double nuevoPrecioUnitario = scanner.nextDouble();
                detalleCompra.setPrecioUnitario(BigDecimal.valueOf(nuevoPrecioUnitario));

                detalleCompraJpaController.edit(detalleCompra);
                System.out.println("Detalle de compra actualizado exitosamente.");
            } else {
                System.out.println("No se encontró el detalle de compra con ID: " + id);
            }
        } catch (NonexistentEntityException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al actualizar el detalle de compra: " + e.getMessage());
        }
    }

    private void eliminarDetalleCompra(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del detalle de compra a eliminar: ");
            if (scanner.hasNextInt()) {
                int id = scanner.nextInt();
                scanner.nextLine();

                DetalleCompra detalleCompra = detalleCompraJpaController.findDetalleCompra(id);
                if (detalleCompra != null) {
                    detalleCompraJpaController.destroy(id);
                    System.out.println("Detalle de compra eliminado exitosamente.");
                } else {
                    System.out.println("No se encontró el detalle de compra con ID: " + id);
                }
            } else {
                System.out.println("Error: Debe ingresar un número válido.");
                scanner.nextLine();
            }
        } catch (NonexistentEntityException e) {
            System.out.println("Error: El detalle de compra no existe o ya fue eliminado.");
        } catch (Exception e) {
            System.out.println("Error al eliminar el detalle de compra: " + e.getMessage());
        }
    }
}
 