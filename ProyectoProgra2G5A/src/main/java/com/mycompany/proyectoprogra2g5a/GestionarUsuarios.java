/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyectoprogra2g5a;

import gt.edu.umg.bd.Usuarios;
import gt.edu.umg.bd.Roles;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Scanner;

public class GestionarUsuarios {

    private EntityManagerFactory emf;

    public GestionarUsuarios(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void gestionarUsuarios(Scanner scanner) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\n*** Gestión de Usuarios ***");
            System.out.println("1. Crear Usuario");
            System.out.println("2. Leer Usuarios");
            System.out.println("3. Actualizar Usuario");
            System.out.println("4. Eliminar Usuario");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    crearUsuario(scanner);
                    break;
                case 2:
                    leerUsuarios();
                    break;
                case 3:
                    actualizarUsuario(scanner);
                    break;
                case 4:
                    eliminarUsuario(scanner);
                    break;
                case 5:
                    salir = true;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private void crearUsuario(Scanner scanner) {
        System.out.print("Ingrese el nombre del usuario: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String contraseña = scanner.nextLine();
        System.out.print("Ingrese el ID del rol: ");
        int rolId = scanner.nextInt();
        scanner.nextLine();

        byte[] contraseñaEncriptada = ProyectoProgra2G5A.encriptarContrasena(contraseña);

        Usuarios nuevoUsuario = new Usuarios();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setContraseña(contraseñaEncriptada);

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            Roles rol = em.find(Roles.class, rolId);
            if (rol != null) {
                nuevoUsuario.setRolId(rol);
            } else {
                System.out.println("Error: Rol no encontrado con el ID " + rolId);
                em.getTransaction().rollback();
                em.close();
                return;
            }

            em.persist(nuevoUsuario);
            em.getTransaction().commit();
            System.out.println("Usuario creado con éxito.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al crear el usuario: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void leerUsuarios() {
        EntityManager em = emf.createEntityManager();
        try {
            List<Usuarios> usuarios = em.createNamedQuery("Usuarios.findAll", Usuarios.class).getResultList();
            for (Usuarios usuario : usuarios) {
                System.out.println("ID: " + usuario.getUsuarioId() + ", Nombre: " + usuario.getNombre() + ", Correo: " + usuario.getCorreo());
            }
        } finally {
            em.close();
        }
    }

    private void actualizarUsuario(Scanner scanner) {
        System.out.print("Ingrese el ID del usuario que desea actualizar: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Usuarios usuario = em.find(Usuarios.class, idUsuario);

            if (usuario != null) {
                System.out.println("Usuario encontrado:");
                System.out.println("ID: " + usuario.getUsuarioId() + ", Correo: " + usuario.getCorreo() + ", Nombre: " + usuario.getNombre());

                System.out.print("Ingrese el nuevo nombre (dejar vacío para no cambiar): ");
                String nuevoNombre = scanner.nextLine();
                System.out.print("Ingrese el nuevo correo (dejar vacío para no cambiar): ");
                String nuevoCorreo = scanner.nextLine();
                System.out.print("Ingrese la nueva contraseña (dejar vacío para no cambiar): ");
                String nuevaContraseña = scanner.nextLine();

                if (!nuevoNombre.isEmpty()) {
                    usuario.setNombre(nuevoNombre);
                }
                if (!nuevoCorreo.isEmpty()) {
                    usuario.setCorreo(nuevoCorreo);
                }
                if (!nuevaContraseña.isEmpty()) {
                    byte[] nuevaContraseñaEncriptada = ProyectoProgra2G5A.encriptarContrasena(nuevaContraseña);
                    usuario.setContraseña(nuevaContraseñaEncriptada);
                }

                em.merge(usuario);
                em.getTransaction().commit();
                System.out.println("Usuario actualizado con éxito.");
            } else {
                System.out.println("Error: Usuario no encontrado con el ID " + idUsuario);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al actualizar el usuario: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private void eliminarUsuario(Scanner scanner) {
        System.out.print("Ingrese el ID del usuario que desea eliminar: ");
        int idUsuario = scanner.nextInt();
        scanner.nextLine();

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        try {
            Usuarios usuario = em.find(Usuarios.class, idUsuario);

            if (usuario != null) {
                em.remove(usuario);
                em.getTransaction().commit();
                System.out.println("Usuario eliminado con éxito.");
            } else {
                System.out.println("Error: Usuario no encontrado con el ID " + idUsuario);
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
