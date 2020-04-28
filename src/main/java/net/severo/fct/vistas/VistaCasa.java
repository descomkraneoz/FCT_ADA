package net.severo.fct.vistas;

import net.severo.fct.POJO.Casa;
import net.severo.fct.POJO.Confinado;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VistaCasa {

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
        String menu = "  1. Nueva Casa. \n 2. Ver Casas \n 3. Eliminar Casa \n 0. Salir \n ¿Que quiere hacer?";

        int opcion = -1; //opcion -1 indica opcion incorrecta
        while (opcion == -1) {
            System.out.println(menu);
            String entrada = sc.nextLine();
            if (!esEntero(entrada)) {
                opcion = -1;
            } else {
                opcion = Integer.parseInt(entrada);
            }
            if (opcion > 5 || opcion < 0) {
                System.out.println("Opción no valida, elija una opción del 1-5 o 0 para Salir");
                opcion = -1;
            }
        }
        return opcion;
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
                //No se permiten vuelos gratuitos
                id = Integer.parseInt(respuesta);
                if (id < 0) {
                    System.err.println("El id ha de ser un numero positivo");
                } else {
                    return id;
                }
            }
        } while (true);
    }

   public Boolean pedirJardin() {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("¿La casa tiene jardin? (S/N)");
            String respuesta = sc.nextLine();
            if ("0".equals(respuesta)) {
                return null;
            }
            if (("S".equals(respuesta.toUpperCase()))) {
                return true;
            }
            if (("N".equals(respuesta.toUpperCase()))) {
                return false;
            }
            System.out.println("Por favor,introduzca S para una respuesta afirmativa y N para una negativa");
        } while (true);
    }

    public void mostrarListaCasas(List<Casa> casas) {
        System.out.println();
        System.out.println("----------LISTA DE CASAS-----------");
        System.out.println();
        System.out.println("ID        TIENE JARDIN    ");
        System.out.println("---      ---------------  ");
        for (Casa m : casas) {
            System.out.printf("%-10d %-30s \n", m.getIdCasa(), m.getTieneJardin() ? "Sí" : "No");
        }
        System.out.println("---------------------------------------");
    }

    public void mostrarConfinadoYCasa(List<Casa> casas, List<Confinado> confinados) {
        System.out.println();
        System.out.println("----------LISTA DE CONFINADOS Y CASAS-----------");
        System.out.println();
        System.out.println("NOMBRE COMPLETO        CASA CON JARDIN");
        System.out.println("------------------    ------------------");
        for (Casa m : casas) {
            for (Confinado v : confinados) {
                System.out.printf("%-30s %-30s \n", v.getNombre(), m.getTieneJardin());
            }
        }
        System.out.println("---------------------------------------");
    }

    public void mostrarError(String mensaje) {
        System.err.println(mensaje);
    }


}
