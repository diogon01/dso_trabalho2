package com.trocas.dso2.domain;



import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.trocas.dso2.domain.enumeration.Categoria;

/**
 * A Anuncio.
 */
@Entity
@Table(name = "anuncio")
public class Anuncio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "valor_min")
    private Long valorMin;

    @Column(name = "valor_max")
    private Long valorMax;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Categoria categoria;

    @OneToOne
    @JoinColumn(unique = true)
    private Venda tituloVenda;

    @OneToOne
    @JoinColumn(unique = true)
    private Endereco endereco;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Anuncio titulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getValorMin() {
        return valorMin;
    }

    public Anuncio valorMin(Long valorMin) {
        this.valorMin = valorMin;
        return this;
    }

    public void setValorMin(Long valorMin) {
        this.valorMin = valorMin;
    }

    public Long getValorMax() {
        return valorMax;
    }

    public Anuncio valorMax(Long valorMax) {
        this.valorMax = valorMax;
        return this;
    }

    public void setValorMax(Long valorMax) {
        this.valorMax = valorMax;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Anuncio categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Venda getTituloVenda() {
        return tituloVenda;
    }

    public Anuncio tituloVenda(Venda venda) {
        this.tituloVenda = venda;
        return this;
    }

    public void setTituloVenda(Venda venda) {
        this.tituloVenda = venda;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public Anuncio endereco(Endereco endereco) {
        this.endereco = endereco;
        return this;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Anuncio)) {
            return false;
        }
        return id != null && id.equals(((Anuncio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", valorMin=" + getValorMin() +
            ", valorMax=" + getValorMax() +
            ", categoria='" + getCategoria() + "'" +
            "}";
    }
}
