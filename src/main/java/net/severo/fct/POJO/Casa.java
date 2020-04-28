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
    @Column(name = "idCasa")
    private int idCasa;

    @Column(name = "tieneJardin")
    private boolean tieneJardin;

    @OneToMany(mappedBy = "casa", cascade = CascadeType.ALL)

    private Set<Confinado> confinados = new HashSet();

    public Casa(int idCasa, boolean tieneJardin, Set<Confinado> confinados) {
        this.idCasa = idCasa;
        this.tieneJardin = tieneJardin;
        this.confinados = confinados;
    }

    public Casa(int idCasa, boolean tieneJardin) {
        this.idCasa = idCasa;
        this.tieneJardin = tieneJardin;
    }
    public Casa(){

    }

    public int getIdCasa() {
        return idCasa;
    }

    public void setIdCasa(int idCasa) {
        this.idCasa = idCasa;
    }

    public boolean getTieneJardin() {
        return tieneJardin;
    }

    public void setTieneJardin(boolean tieneJardin) {
        this.tieneJardin = tieneJardin;
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
