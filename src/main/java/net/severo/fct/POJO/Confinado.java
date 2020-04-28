package net.severo.fct.POJO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "confinado")
public class Confinado implements Serializable {
    @Id
    @Column(name = "ID_CONFINADO")
    private int idConfinado;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CASA")
    private int idCasa;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH,
                            CascadeType.PERSIST
                    },
            targetEntity = Confinado.class)
    @JoinTable(name = "confinadocasa",
            inverseJoinColumns = @JoinColumn(name = "idConfinado",
                    nullable = false,
                    updatable = false),
            joinColumns = @JoinColumn(name = "IdCasa",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Set<Casa> casas = new HashSet();

    public Confinado(int idConfinado, String nombre, int idCasa, Set<Casa> casas) {
        this.idConfinado = idConfinado;
        this.nombre = nombre;
        this.idCasa = idCasa;
        this.casas = casas;
    }

    public Confinado(int idConfinado, String nombre, int idCasa) {
        this.idConfinado = idConfinado;
        this.nombre = nombre;
        this.idCasa = idCasa;
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

    public int getIdCasa() {
        return idCasa;
    }

    public void setIdCasa(int idCasa) {
        this.idCasa = idCasa;
    }

    public Set<Casa> getCasas() {
        return casas;
    }

    public void setCasas(Set<Casa> casas) {
        this.casas = casas;
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
