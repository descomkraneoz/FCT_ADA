package net.severo.fct.vistas;

import java.util.Scanner;

public class VistaPrincipal {

    public int elegirSistemaAlmacenamiento() {
        int opcion = -1; //opcio -1 indica opcion incorrecta
        while (opcion == -1) {
            System.out.println("Introduzca El tipo de sistema de almacenamiento");

            System.out.println("1.Base de datos relacional usando JDBC");
            System.out.println("2.Base de datos relacional usando Hibernate");

            System.out.println("0.Salir");
            Scanner sc = new Scanner(System.in);
            String entrada = sc.nextLine();
            if (!esEntero(entrada)) {
                opcion = -1;
            } else {
                opcion = Integer.parseInt(entrada);
            }
            //El número debe cambiar cuando vayas implementando más DAO
            if (opcion > 2 || opcion < 0) {
                System.out.println("Opcion No valida en el menu principal de elegir sistema de almacenamiento");
                opcion = -1;

            }

        }
        return opcion;
    }

    public int menuPrincipal(){
        int opcion = -1; //opcion -1 indica opcion incorrecta
        while (opcion == -1) {
            System.out.println("¿Qué quiere gestionar?");
            System.out.println("1. Gestionar Confinados");
            System.out.println("2. Gestionar Casas");
            //System.out.println("3. Informes");
            System.out.println("0.Salir");
            Scanner sc = new Scanner(System.in);
            String entrada = sc.nextLine();
            if (!esEntero(entrada)) {
                opcion = -1;
            } else {
                opcion = Integer.parseInt(entrada);
            }
            //El numero opcion>3 cuando implementes los informes pasara a ser 4
            if (opcion > 3 || opcion < 0) {
                System.out.println("Opcion No valida en el menu principal");
                opcion = -1;
            }
        }
        return opcion;
    }


    private static boolean esEntero(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public void mostrarError(String mensaje) {
        System.err.println(mensaje);
    }

}
