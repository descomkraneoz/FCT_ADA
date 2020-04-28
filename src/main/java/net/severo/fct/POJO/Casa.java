package net.severo.fct.POJO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "casa")
public class Casa implements Serializable{
    @Id
    @Column(name = "ID_CASA")
    private int idCasa;

    @Column(name = "TIENE_JARDIN")
    private boolean tiene_jardin;

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
            inverseJoinColumns = @JoinColumn(name = "IdConfinado",
                    nullable = false,
                    updatable = false),
            joinColumns = @JoinColumn(name = "IdCasa",
                    nullable = false,
                    updatable = false),
            foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT),
            inverseForeignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private Set<Confinado> confinados = new HashSet();

    public Casa(int idCasa, boolean tiene_jardin, Set<Confinado> confinados) {
        this.idCasa = idCasa;
        this.tiene_jardin = tiene_jardin;
        this.confinados = confinados;
    }

    public Casa(int idCasa, boolean tiene_jardin) {
        this.idCasa = idCasa;
        this.tiene_jardin = tiene_jardin;
    }
    public Casa(){

    }

    public int getIdCasa() {
        return idCasa;
    }

    public void setIdCasa(int idCasa) {
        this.idCasa = idCasa;
    }

    public boolean getTiene_jardin() {
        return tiene_jardin;
    }

    public void setTiene_jardin(boolean tiene_jardin) {
        this.tiene_jardin = tiene_jardin;
    }

    public Set<Confinado> getConfinados() {
        return confinados;
    }

    public void setConfinados(Set<Confinado> confinados) {
        this.confinados = confinados;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Casa casa = (Casa) o;
        return idCasa == casa.idCasa;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCasa);
    }
}
