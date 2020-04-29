package net.severo.fct.POJO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "confinado")
public class Confinado implements Serializable {
    @Id
    @Column(name = "idConfinado")
    private int idConfinado;

    @Column(name = "nombre")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "IdCasa")
    private Casa casa;


    public Confinado(int idConfinado, String nombre, Casa casa) {
        this.idConfinado = idConfinado;
        this.nombre = nombre;
        this.casa = casa;
    }

    public Confinado(){

    }

    public int getIdConfinado() {
        return idConfinado;
    }

    public void setIdConfinado(int idConfinado) {
        this.idConfinado = idConfinado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Casa getCasa() {
        return casa;
    }

    public int getIdCasa() {
        return casa.getIdCasa();
    }

    public void setCasa(Casa casa) {
        this.casa = casa;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Confinado confinado = (Confinado) o;
        return idConfinado == confinado.idConfinado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idConfinado);
    }
}
