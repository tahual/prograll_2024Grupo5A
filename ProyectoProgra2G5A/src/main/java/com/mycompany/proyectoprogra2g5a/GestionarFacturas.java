/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprogra2g5a;

/**
 *
 * @author Dany Alexsis Tahual
 */
import gt.edu.umg.bd.Facturas;
import gt.edu.umg.bd.Ventas;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class GestionarFacturas {

    private EntityManagerFactory emf;

    public GestionarFacturas(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void gestionarFacturas(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n** Gestión de Facturas **");
            System.out.println("1. Crear Factura");
            System.out.println("2. Leer Facturas");
            System.out.println("3. Actualizar Factura");
            System.out.println("4. Eliminar Factura");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearFactura(scanner);
                    break;
                case 2:
                    leerFacturas();
                    break;
                case 3:
                    actualizarFactura(scanner);
                    break;
                case 4:
                    eliminarFactura(scanner);
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void crearFactura(Scanner scanner) {
        System.out.print("Ingrese el ID de la venta: ");
        int ventaId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ingrese la fecha de emisión (año-mes-dia): ");
        String fechaInput = scanner.nextLine();

        Date fechaEmision;
        try {
            fechaEmision = java.sql.Date.valueOf(fechaInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Fecha inválida. Asegúrese de usar el formato año-mes-dia.");
            return;
        }

        System.out.print("Ingrese el monto total: ");
        BigDecimal montoTotal;
        try {
            montoTotal = scanner.nextBigDecimal();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error: Monto inválido. Asegúrese de ingresar un número válido.");
            return;
        }

        Facturas nuevaFactura = new Facturas();
        nuevaFactura.setFechaEmision(fechaEmision);
        nuevaFactura.setMontoTotal(montoTotal);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Ventas venta = em.find(Ventas.class, ventaId);
            if (venta != null) {
                nuevaFactura.setVentaId(venta);
                em.persist(nuevaFactura);
                em.getTransaction().commit();
                System.out.println("Factura creada con éxito. ID de la factura: " + nuevaFactura.getFacturaId());
            } else {
                System.out.println("Error: Venta no encontrada con ID " + ventaId);
                em.getTransaction().rollback();
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al crear la factura: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void leerFacturas() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Facturas> facturas = em.createNamedQuery("Facturas.findAll", Facturas.class).getResultList();
            for (Facturas factura : facturas) {
                System.out.println("ID: " + factura.getFacturaId()
                        + ", Fecha: " + factura.getFechaEmision()
                        + ", Monto Total: " + factura.getMontoTotal()
                        + ", Venta ID: " + (factura.getVentaId() != null ? factura.getVentaId().getVentaId() : "No existe en el Sistema"));
            }
        } finally {
            em.close();
        }
    }

    private void actualizarFactura(Scanner scanner) {
        System.out.print("Ingrese el ID de la factura que desea actualizar: ");
        int idFactura = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Facturas factura = em.find(Facturas.class, idFactura);

            if (factura != null) {
                System.out.println("Factura encontrada:");
                System.out.println("ID: " + factura.getFacturaId()
                        + ", Fecha: " + factura.getFechaEmision()
                        + ", Monto Total: " + factura.getMontoTotal());

                System.out.print("Ingrese la nueva fecha de emisión (dejar vacío para no cambiar): ");
                String nuevaFechaInput = scanner.nextLine();

                if (!nuevaFechaInput.isEmpty()) {
                    Date nuevaFechaEmision = java.sql.Date.valueOf(nuevaFechaInput);
                    factura.setFechaEmision(nuevaFechaEmision);
                }

                System.out.print("Ingrese el nuevo monto total (dejar vacío para no cambiar): ");
                if (scanner.hasNextBigDecimal()) {
                    BigDecimal nuevoMontoTotal = scanner.nextBigDecimal();
                    factura.setMontoTotal(nuevoMontoTotal);
                }

                em.merge(factura);
                em.getTransaction().commit();
                System.out.println("Factura actualizada con éxito.");
            } else {
                System.out.println("Error: Factura no encontrada con el ID " + idFactura);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al actualizar la factura: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void eliminarFactura(Scanner scanner) {
        System.out.print("Ingrese el ID de la factura que desea eliminar: ");
        int idFactura = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Facturas factura = em.find(Facturas.class, idFactura);

            if (factura != null) {
                em.remove(factura);
                em.getTransaction().commit();
                System.out.println("Factura eliminada con éxito.");
            } else {
                System.out.println("Error: Factura no encontrada con el ID " + idFactura);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al eliminar la factura: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}

