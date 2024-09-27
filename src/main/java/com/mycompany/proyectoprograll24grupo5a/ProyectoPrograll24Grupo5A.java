/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.proyectoprograll24grupo5a;

import java.util.Scanner;

public class ProyectoPrograll24Grupo5A {
    private String usuario;
    private String contraseña;

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Menú de Inicio de Sesión");
        
        System.out.print("Ingrese su usuario: ");
        usuario = scanner.nextLine();

        System.out.print("Ingrese su contraseña: ");
        contraseña = scanner.nextLine();
        
        System.out.println("Intentando iniciar sesión con el usuario: " + usuario);
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public static void main(String[] args) {
        ProyectoPrograll24Grupo5A menu = new ProyectoPrograll24Grupo5A();
        menu.mostrarMenu();
        
        String usuario = menu.getUsuario();
        String contraseña = menu.getContraseña();
        
        login milogin = new login();
        milogin.acceder(usuario, contraseña);
    }
}
