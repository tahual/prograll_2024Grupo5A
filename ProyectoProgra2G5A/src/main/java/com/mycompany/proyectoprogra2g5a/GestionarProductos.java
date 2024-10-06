/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprogra2g5a;

import gt.edu.umg.bd.Productos;
import gt.edu.umg.bd.ProductosJpaController;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;

public class GestionarProductos {

    private EntityManagerFactory emf;
    private ProductosJpaController productosController;

    public GestionarProductos(EntityManagerFactory emf) {
        this.emf = emf;
        this.productosController = new ProductosJpaController(emf);
    }

    public void gestionarProductos(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n*** Gestión de Productos ***");
            System.out.println("1. Crear Productos");
            System.out.println("2. Leer Productos");
            System.out.println("3. Actualizar Productos");
            System.out.println("4. Eliminar Productos");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearProductos(scanner);
                    break;
                case 2:
                    leerProductos();
                    break;
                case 3:
                    actualizarProductos(scanner);
                    break;
                case 4:
                    eliminarProductos(scanner);
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void crearProductos(Scanner scanner) {
        try {
            Productos producto = new Productos();

            System.out.print("Ingrese el nombre del producto: ");
            producto.setNombre(scanner.nextLine());

            System.out.print("Ingrese el precio del producto: ");
            double precioIngresado = scanner.nextDouble();
            producto.setPrecio(BigDecimal.valueOf(precioIngresado));

            System.out.print("Ingrese la cantidad en inventario: ");
            producto.setCantidadEnInventario(scanner.nextInt());
            scanner.nextLine();

            System.out.print("Ingrese la descripción del producto: ");
            producto.setDescripcion(scanner.nextLine());

            productosController.create(producto);
            System.out.println("Producto creado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al crear el producto: " + e.getMessage());
        }
    }

    private void leerProductos() {
        try {
            List<Productos> productosList = productosController.findProductosEntities();
            if (productosList.isEmpty()) {
                System.out.println("No hay productos registrados.");
            } else {
                for (Productos producto : productosList) {
                    System.out.println("ID: " + producto.getProductoId());
                    System.out.println("Nombre: " + producto.getNombre());
                    System.out.println("Precio: " + producto.getPrecio());
                    System.out.println("Cantidad en Inventario: " + producto.getCantidadEnInventario());
                    System.out.println("---------------------------");
                }
            }
        } catch (Exception e) {
            System.out.println("Error al leer productos: " + e.getMessage());
        }
    }

    private void actualizarProductos(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del producto que desea actualizar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Productos producto = productosController.findProductos(id);
            if (producto == null) {
                System.out.println("Producto no encontrado.");
                return;
            }

            System.out.print("Ingrese el nuevo nombre del producto (Producto Actual: " + producto.getNombre() + "): ");
            String nuevoNombre = scanner.nextLine();
            if (!nuevoNombre.isEmpty()) {
                producto.setNombre(nuevoNombre);
            }

            System.out.print("Ingrese el nuevo precio del producto (Precio actual: " + producto.getPrecio() + "): ");
            String nuevoPrecio = scanner.nextLine();
            if (!nuevoPrecio.isEmpty()) {
                producto.setPrecio(BigDecimal.valueOf(Double.parseDouble(nuevoPrecio)));
            }

            System.out.print("Ingrese la nueva cantidad en inventario (Cantidad actual: " + producto.getCantidadEnInventario() + "): ");
            String nuevaCantidad = scanner.nextLine();
            if (!nuevaCantidad.isEmpty()) {
                producto.setCantidadEnInventario(Integer.parseInt(nuevaCantidad));
            }

            System.out.print("Ingrese la nueva descripción del producto (Descripcion actual: " + producto.getDescripcion() + "): ");
            String nuevaDescripcion = scanner.nextLine();
            if (!nuevaDescripcion.isEmpty()) {
                producto.setDescripcion(nuevaDescripcion);
            }

            productosController.edit(producto);
            System.out.println("Producto actualizado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al actualizar el producto: " + e.getMessage());
        }
    }

    private void eliminarProductos(Scanner scanner) {
        try {
            System.out.print("Ingrese el ID del producto que desea eliminar: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            productosController.destroy(id);
            System.out.println("Producto eliminado exitosamente.");
        } catch (Exception e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
    }
}
