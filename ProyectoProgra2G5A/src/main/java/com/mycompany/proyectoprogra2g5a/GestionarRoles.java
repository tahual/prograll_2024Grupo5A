/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprogra2g5a;

import gt.edu.umg.bd.Roles;
import gt.edu.umg.bd.RolesJpaController;
import gt.edu.umg.bd.exceptions.NonexistentEntityException;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Scanner;

public class GestionarRoles {

    private EntityManagerFactory emf;
    private RolesJpaController rolesJpaController;

    public GestionarRoles(EntityManagerFactory emf) {
        this.emf = emf;
        this.rolesJpaController = new RolesJpaController(emf);
    }

    public void gestionarRoles(Scanner scanner) {
        int opcion;

        do {
            System.out.println("\n*** Gestión de Roles ***");
            System.out.println("1. Crear Rol");
            System.out.println("2. Leer Rol");
            System.out.println("3. Actualizar Rol");
            System.out.println("4. Eliminar Rol");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearRol(scanner);
                    break;
                case 2:
                    leerRol(scanner);
                    break;
                case 3:
                    actualizarRol(scanner);
                    break;
                case 4:
                    eliminarRol(scanner);
                    break;
                case 5:
                    System.out.println("Saliendo de la gestión de roles.");
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        } while (opcion != 0);
    }

    private void crearRol(Scanner scanner) {
        System.out.print("Introduce el nombre del rol: ");
        String nombreRol = scanner.nextLine();
        Roles nuevoRol = new Roles();
        nuevoRol.setNombre(nombreRol);
        rolesJpaController.create(nuevoRol);
        System.out.println("Rol creado con éxito.");
    }

    private void leerRol(Scanner scanner) {
        System.out.print("Introduce el ID del rol a leer: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Roles rol = rolesJpaController.findRoles(id);
        if (rol != null) {
            System.out.println("ID: " + rol.getRolId());
            System.out.println("Nombre: " + rol.getNombre());
        } else {
            System.out.println("El rol con ID " + id + " no existe.");
        }
    }

    private void actualizarRol(Scanner scanner) {
        System.out.print("Introduce el ID del rol a actualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Roles rol = rolesJpaController.findRoles(id);
        if (rol != null) {
            System.out.print("Introduce el nuevo nombre del rol: ");
            String nuevoNombre = scanner.nextLine();
            rol.setNombre(nuevoNombre);
            try {
                rolesJpaController.edit(rol);
                System.out.println("Rol actualizado con éxito.");
            } catch (Exception e) {
                System.out.println("Error al actualizar el rol: " + e.getMessage());
            }
        } else {
            System.out.println("El rol con ID " + id + " no existe.");
        }
    }

    private void eliminarRol(Scanner scanner) {
        System.out.print("Introduce el ID del rol a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        try {
            rolesJpaController.destroy(id);
            System.out.println("Rol eliminado con éxito.");
        } catch (NonexistentEntityException e) {
            System.out.println("El rol con ID " + id + " no existe.");
        }
    }

}
