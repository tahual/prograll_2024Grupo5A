/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprogra2g5a;

/**
 *
 * @author German Hernández
 */
import gt.edu.umg.bd.Compras;
import gt.edu.umg.bd.ComprasJpaController;
import gt.edu.umg.bd.Usuarios;
import gt.edu.umg.bd.UsuariosJpaController;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class GestionarCompras {

    private EntityManagerFactory emf;
    private ComprasJpaController comprasJpaController;

    public GestionarCompras(EntityManagerFactory emf) {
        this.emf = emf;
        this.comprasJpaController = new ComprasJpaController(emf);
    }

    public void gestionarCompras(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n** Gestión de Compras **");
            System.out.println("1. Crear Compra");
            System.out.println("2. Leer Compras");
            System.out.println("3. Actualizar Compra");
            System.out.println("4. Eliminar Compra");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearCompra(scanner);
                    break;
                case 2:
                    leerCompras();
                    break;
                case 3:
                    actualizarCompra(scanner);
                    break;
                case 4:
                    eliminarCompra(scanner);
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void crearCompra(Scanner scanner) {
        System.out.print("Ingrese el ID del usuario: ");
        int usuarioId = scanner.nextInt();
        Usuarios usuario = obtenerUsuarioPorId(usuarioId);
        scanner.nextLine();

        if (usuario == null) {
            System.out.println("Usuario no encontrado. No se puede crear la compra.");
            return;
        }

        Compras nuevaCompra = new Compras();
        nuevaCompra.setUsuarioId(usuario);

        System.out.print("Ingrese la fecha de compra (año-mes-dia): ");
        String fechaInput = scanner.nextLine();

        Date fechaCompra;
        try {
            fechaCompra = java.sql.Date.valueOf(fechaInput);
            nuevaCompra.setFechaCompra(fechaCompra);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: Fecha inválida. Asegúrese de usar el formato YYYY-MM-DD.");
            return;
        }

        System.out.print("Ingrese el monto total: ");
        BigDecimal total;
        try {
            total = scanner.nextBigDecimal();
            nuevaCompra.setTotal(total);
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Error: Monto inválido. Asegúrese de ingresar un número válido.");
            return;
        }

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            em.persist(nuevaCompra);
            em.getTransaction().commit();
            System.out.println("Compra creada con éxito. ID de la compra: " + nuevaCompra.getCompraId());
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al crear la compra: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void leerCompras() {
        List<Compras> compras = comprasJpaController.findComprasEntities();
        for (Compras compra : compras) {
            System.out.println("ID: " + compra.getCompraId()
                    + ", Fecha: " + compra.getFechaCompra()
                    + ", Monto Total: " + compra.getTotal()
                    + ", Usuario ID: " + (compra.getUsuarioId() != null ? compra.getUsuarioId().getUsuarioId() : "No existe en el sistema"));
        }
    }

    private void actualizarCompra(Scanner scanner) {
        System.out.print("Ingrese el ID de la compra que desea actualizar: ");
        int idCompra = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Compras compra = em.find(Compras.class, idCompra);

            if (compra != null) {
                System.out.println("Compra encontrada:");
                System.out.println("ID: " + compra.getCompraId()
                        + ", Fecha: " + compra.getFechaCompra()
                        + ", Monto Total: " + compra.getTotal());

                System.out.print("Ingrese la nueva fecha de compra (dejar vacío si no desea modificar): ");
                String nuevaFechaInput = scanner.nextLine();

                if (!nuevaFechaInput.isEmpty()) {
                    Date nuevaFechaCompra = java.sql.Date.valueOf(nuevaFechaInput);
                    compra.setFechaCompra(nuevaFechaCompra);
                }

                System.out.print("Ingrese el nuevo monto total (dejar vacío si no desea modificar): ");
                if (scanner.hasNextBigDecimal()) {
                    BigDecimal nuevoMontoTotal = scanner.nextBigDecimal();
                    compra.setTotal(nuevoMontoTotal);
                }

                em.merge(compra);
                em.getTransaction().commit();
                System.out.println("Compra actualizada con éxito.");
            } else {
                System.out.println("Error: Compra no encontrada con el ID " + idCompra);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al actualizar la compra: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void eliminarCompra(Scanner scanner) {
        System.out.print("Ingrese el ID de la compra que desea eliminar: ");
        int idCompra = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Compras compra = em.find(Compras.class, idCompra);

            if (compra != null) {
                em.remove(compra);
                em.getTransaction().commit();
                System.out.println("Compra eliminada con éxito.");
            } else {
                System.out.println("Error: Compra no encontrada con el ID " + idCompra);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al eliminar la compra: " + e.getMessage());
        } finally {
            em.close();
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
