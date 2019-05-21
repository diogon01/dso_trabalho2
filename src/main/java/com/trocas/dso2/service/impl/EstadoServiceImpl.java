package com.trocas.dso2.service.impl;

import com.trocas.dso2.service.EstadoService;
import com.trocas.dso2.domain.Estado;
import com.trocas.dso2.repository.EstadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Estado}.
 */
@Service
@Transactional
public class EstadoServiceImpl implements EstadoService {

    private final Logger log = LoggerFactory.getLogger(EstadoServiceImpl.class);

    private final EstadoRepository estadoRepository;

    public EstadoServiceImpl(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    /**
     * Save a estado.
     *
     * @param estado the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Estado save(Estado estado) {
        log.debug("Request to save Estado : {}", estado);
        return estadoRepository.save(estado);
    }

    /**
     * Get all the estados.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Estado> findAll() {
        log.debug("Request to get all Estados");
        return estadoRepository.findAll();
    }


    /**
     * Get one estado by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Estado> findOne(Long id) {
        log.debug("Request to get Estado : {}", id);
        return estadoRepository.findById(id);
    }

    /**
     * Delete the estado by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estado : {}", id);
        estadoRepository.deleteById(id);
    }
}
