package com.trocas.dso2.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Pais.
 */
@Entity
@Table(name = "pais")
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_pais")
    private String nomePais;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePais() {
        return nomePais;
    }

    public Pais nomePais(String nomePais) {
        this.nomePais = nomePais;
        return this;
    }

    public void setNomePais(String nomePais) {
        this.nomePais = nomePais;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pais)) {
            return false;
        }
        return id != null && id.equals(((Pais) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pais{" +
            "id=" + getId() +
            ", nomePais='" + getNomePais() + "'" +
            "}";
    }
}
