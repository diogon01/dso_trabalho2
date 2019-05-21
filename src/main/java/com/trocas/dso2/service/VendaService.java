package com.trocas.dso2.service;

import com.trocas.dso2.domain.Venda;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Venda}.
 */
public interface VendaService {

    /**
     * Save a venda.
     *
     * @param venda the entity to save.
     * @return the persisted entity.
     */
    Venda save(Venda venda);

    /**
     * Get all the vendas.
     *
     * @return the list of entities.
     */
    List<Venda> findAll();
    /**
     * Get all the VendaDTO where Job is {@code null}.
     *
     * @return the list of entities.
     */
    List<Venda> findAllWhereJobIsNull();


    /**
     * Get the "id" venda.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Venda> findOne(Long id);

    /**
     * Delete the "id" venda.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
