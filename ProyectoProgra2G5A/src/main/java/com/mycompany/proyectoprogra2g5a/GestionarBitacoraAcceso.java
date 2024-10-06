/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprogra2g5a;

import gt.edu.umg.bd.BitacoraAcceso;
import gt.edu.umg.bd.BitacoraAccesoJpaController;
import gt.edu.umg.bd.Usuarios;
import gt.edu.umg.bd.UsuariosJpaController;
import gt.edu.umg.bd.exceptions.NonexistentEntityException;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class GestionarBitacoraAcceso {

    private EntityManagerFactory emf;
    private BitacoraAccesoJpaController bitacoraAccesoJpaController;

    public GestionarBitacoraAcceso(EntityManagerFactory emf) {
        this.emf = emf;
        this.bitacoraAccesoJpaController = new BitacoraAccesoJpaController(emf);
    }

    public void gestionarBitacoraAcceso(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n*** Gestión de Bitácora de Acceso ***");
            System.out.println("1. Crear BitacoraAcceso");
            System.out.println("2. Leer BitacoraAcceso");
            System.out.println("3. Actualizar BitacoraAcceso");
            System.out.println("4. Eliminar BitacoraAcceso");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearBitacoraAcceso(scanner);
                    break;
                case 2:
                    leerBitacoraAcceso();
                    break;
                case 3:
                    actualizarBitacoraAcceso(scanner);
                    break;
                case 4:
                    eliminarBitacoraAcceso(scanner);
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void crearBitacoraAcceso(Scanner scanner) {
        System.out.print("Ingrese la acción: ");
        String accion = scanner.nextLine();
        System.out.print("Ingrese el ID del usuario: ");
        int usuarioId = scanner.nextInt();
        Usuarios usuario = obtenerUsuarioPorId(usuarioId);

        if (usuario == null) {
            System.out.println("Usuario no encontrado. No se puede crear la bitácora.");
            return;
        }

        BitacoraAcceso bitacora = new BitacoraAcceso();
        bitacora.setFechaAcceso(new Date());
        bitacora.setAccion(accion);
        bitacora.setUsuarioId(usuario);

        bitacoraAccesoJpaController.create(bitacora);
        System.out.println("Bitácora de acceso creada exitosamente.");
    }

    private void leerBitacoraAcceso() {
        List<BitacoraAcceso> bitacoras = bitacoraAccesoJpaController.findBitacoraAccesoEntities();
        if (bitacoras.isEmpty()) {
            System.out.println("No hay entradas en la bitácora.");
        } else {
            System.out.println("\n*** Entradas de la Bitácora de Acceso ***");
            for (BitacoraAcceso b : bitacoras) {
                Usuarios usuario = b.getUsuarioId();
                String usuarioNombre = usuario != null ? usuario.getNombre() : "Desconocido";

                System.out.println("Fecha: " + b.getFechaAcceso() + ", Acción: " + b.getAccion() + ", Usuario: " + usuarioNombre);
            }
        }
    }

    private void actualizarBitacoraAcceso(Scanner scanner) {
        System.out.print("Ingrese el ID de la bitácora a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            BitacoraAcceso bitacora = bitacoraAccesoJpaController.findBitacoraAcceso(id);
            if (bitacora != null) {
                System.out.print("Ingrese la nueva acción (max 100 caracteres): ");
                String nuevaAccion = scanner.nextLine();
                bitacora.setAccion(nuevaAccion);
                bitacoraAccesoJpaController.edit(bitacora);
                System.out.println("Bitácora de acceso actualizada exitosamente.");
            } else {
                System.out.println("No se encontró la bitácora con ID: " + id);
            }
        } catch (NonexistentEntityException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al actualizar la bitácora: " + e.getMessage());
        }
    }

    private void eliminarBitacoraAcceso(Scanner scanner) {
        System.out.print("Ingrese el ID de la bitácora a eliminar: ");
        int id = scanner.nextInt();

        try {
            bitacoraAccesoJpaController.destroy(id);
            System.out.println("Bitácora de acceso eliminada exitosamente.");
        } catch (NonexistentEntityException e) {
            System.out.println("No se encontró la bitácora con ID: " + id);
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
