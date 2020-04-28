package net.severo.fct.vistas;

import net.severo.fct.POJO.Confinado;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VistaConfinado {

    public static boolean esEntero(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private static Date obtenerFecha(String cadena) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaNac = null;
        try {
            fechaNac = sdf.parse(cadena);
            return fechaNac;
        } catch (ParseException ex) {
            // si no es fecha devolvemos un null
            return null;
        }
    }

    public static boolean esDecimal(String numero) {
        try {
            Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }


    public int menuPrincipal() {
        Scanner sc = new Scanner(System.in);
        String menu = "  1. Nuevo Confinado. \n 2. Ver Confinados \n 3. Eliminar Confinado \n 0. Salir \n ¿Que quiere hacer?";

        int opcion = -1; //opcion -1 indica opcion incorrecta
        while (opcion == -1) {
            System.out.println(menu);
            String entrada = sc.nextLine();
            if (!esEntero(entrada)) {
                opcion = -1;
            } else {
                opcion = Integer.parseInt(entrada);
            }
            if (opcion > 4 || opcion < 0) {
                System.out.println("Opción no valida, elija una opción del 1-4 o 0 para Salir");
                opcion = -1;
            }
        }
        return opcion;
    }

    public Integer pedirIdConfinado() {
        String respuesta;
        Scanner sc = new Scanner(System.in);
        Integer id;
        do {
            System.out.println("Introduzca el código identificativo del confinado:");
            respuesta = sc.nextLine();
            if (respuesta.equals("0")) {
                return null;
            }
            if (!esDecimal(respuesta)) {
                System.err.println("El id ha de ser un número decimal.");
            } else {
                id = Integer.parseInt(respuesta);
                if (id < 0) {
                    System.err.println("El id ha de ser un numero positivo");
                } else {
                    return id;
                }
            }
        } while (true);
    }

    public String pedirNombre() {
        String respuesta;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("Introduzca un nombre para el Confinado");
            respuesta = sc.nextLine();
            if (respuesta.equals("0")) {
                //Salimos
                return null;
            }
            if (respuesta.length() < 3 || respuesta.length() > 50 || respuesta == null) {
                System.err.println("El nombre del confinado ha de tener entre 3 y 50 carácteres");
            }
        } while (respuesta.length() < 3 || respuesta.length() > 50 || respuesta == null);
        return respuesta;
    }

    public Integer pedirIdCasa() {
        String respuesta;
        Scanner sc = new Scanner(System.in);
        Integer id;
        do {
            System.out.println("Introduzca el código identificativo de la casa:");
            respuesta = sc.nextLine();
            if (respuesta.equals("0")) {
                return null;
            }
            if (!esDecimal(respuesta)) {
                System.err.println("El id ha de ser un número decimal.");
            } else {
                id = Integer.parseInt(respuesta);
                if (id < 0) {
                    System.err.println("El id ha de ser un numero positivo");
                } else {
                    return id;
                }
            }
        } while (true);
    }




    public void mostrarListaConfinados(List<Confinado> confinados) {
        System.out.println("------------------ LISTA DE CONFINADOS -----------------------");
        System.out.println("CÓDIGO     NOMBRE       CASA");
        System.out.println("------   ----------    ------");
        for (Confinado v : confinados) {
            System.out.printf("%-8s %-15s %-13s \n", v.getIdConfinado(), v.getNombre(), v.getIdCasa());
        }
        System.out.println("-------------------------------------------------------------");
    }

    public void mostrarError(String mensaje) {
        System.err.println(mensaje);
    }
}
