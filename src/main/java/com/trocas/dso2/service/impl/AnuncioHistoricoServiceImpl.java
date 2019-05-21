package com.trocas.dso2.service.impl;

import com.trocas.dso2.service.AnuncioHistoricoService;
import com.trocas.dso2.domain.AnuncioHistorico;
import com.trocas.dso2.repository.AnuncioHistoricoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnuncioHistorico}.
 */
@Service
@Transactional
public class AnuncioHistoricoServiceImpl implements AnuncioHistoricoService {

    private final Logger log = LoggerFactory.getLogger(AnuncioHistoricoServiceImpl.class);

    private final AnuncioHistoricoRepository anuncioHistoricoRepository;

    public AnuncioHistoricoServiceImpl(AnuncioHistoricoRepository anuncioHistoricoRepository) {
        this.anuncioHistoricoRepository = anuncioHistoricoRepository;
    }

    /**
     * Save a anuncioHistorico.
     *
     * @param anuncioHistorico the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnuncioHistorico save(AnuncioHistorico anuncioHistorico) {
        log.debug("Request to save AnuncioHistorico : {}", anuncioHistorico);
        return anuncioHistoricoRepository.save(anuncioHistorico);
    }

    /**
     * Get all the anuncioHistoricos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AnuncioHistorico> findAll(Pageable pageable) {
        log.debug("Request to get all AnuncioHistoricos");
        return anuncioHistoricoRepository.findAll(pageable);
    }


    /**
     * Get one anuncioHistorico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AnuncioHistorico> findOne(Long id) {
        log.debug("Request to get AnuncioHistorico : {}", id);
        return anuncioHistoricoRepository.findById(id);
    }

    /**
     * Delete the anuncioHistorico by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnuncioHistorico : {}", id);
        anuncioHistoricoRepository.deleteById(id);
    }
}
