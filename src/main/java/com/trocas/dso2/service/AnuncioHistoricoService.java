package com.trocas.dso2.service;

import com.trocas.dso2.domain.AnuncioHistorico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link AnuncioHistorico}.
 */
public interface AnuncioHistoricoService {

    /**
     * Save a anuncioHistorico.
     *
     * @param anuncioHistorico the entity to save.
     * @return the persisted entity.
     */
    AnuncioHistorico save(AnuncioHistorico anuncioHistorico);

    /**
     * Get all the anuncioHistoricos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnuncioHistorico> findAll(Pageable pageable);


    /**
     * Get the "id" anuncioHistorico.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnuncioHistorico> findOne(Long id);

    /**
     * Delete the "id" anuncioHistorico.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
