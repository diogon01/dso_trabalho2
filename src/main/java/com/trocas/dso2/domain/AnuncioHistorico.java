package com.trocas.dso2.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A AnuncioHistorico.
 */
@Entity
@Table(name = "anuncio_historico")
public class AnuncioHistorico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_inicio")
    private Instant dataInicio;

    @Column(name = "data_final")
    private Instant dataFinal;

    @ManyToOne
    @JsonIgnoreProperties("anuncioHistoricos")
    private Anuncio job;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataInicio() {
        return dataInicio;
    }

    public AnuncioHistorico dataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
        return this;
    }

    public void setDataInicio(Instant dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Instant getDataFinal() {
        return dataFinal;
    }

    public AnuncioHistorico dataFinal(Instant dataFinal) {
        this.dataFinal = dataFinal;
        return this;
    }

    public void setDataFinal(Instant dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Anuncio getJob() {
        return job;
    }

    public AnuncioHistorico job(Anuncio anuncio) {
        this.job = anuncio;
        return this;
    }

    public void setJob(Anuncio anuncio) {
        this.job = anuncio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnuncioHistorico)) {
            return false;
        }
        return id != null && id.equals(((AnuncioHistorico) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AnuncioHistorico{" +
            "id=" + getId() +
            ", dataInicio='" + getDataInicio() + "'" +
            ", dataFinal='" + getDataFinal() + "'" +
            "}";
    }
}
