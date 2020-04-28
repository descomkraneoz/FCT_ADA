package net.severo.fct.vistas;

import net.severo.fct.controladores.ControladorPrincipal;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);
        ControladorPrincipal controlador=new ControladorPrincipal();
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);
        controlador.iniciarAplicacion();
    }
}
