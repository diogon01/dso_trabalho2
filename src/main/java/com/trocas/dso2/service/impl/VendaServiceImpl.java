package com.trocas.dso2.service.impl;

import com.trocas.dso2.service.VendaService;
import com.trocas.dso2.domain.Venda;
import com.trocas.dso2.repository.VendaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Venda}.
 */
@Service
@Transactional
public class VendaServiceImpl implements VendaService {

    private final Logger log = LoggerFactory.getLogger(VendaServiceImpl.class);

    private final VendaRepository vendaRepository;

    public VendaServiceImpl(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    /**
     * Save a venda.
     *
     * @param venda the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Venda save(Venda venda) {
        log.debug("Request to save Venda : {}", venda);
        return vendaRepository.save(venda);
    }

    /**
     * Get all the vendas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Venda> findAll() {
        log.debug("Request to get all Vendas");
        return vendaRepository.findAll();
    }



    /**
    *  Get all the vendas where Job is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<Venda> findAllWhereJobIsNull() {
        log.debug("Request to get all vendas where Job is null");
        return StreamSupport
            .stream(vendaRepository.findAll().spliterator(), false)
            .filter(venda -> venda.getJob() == null)
            .collect(Collectors.toList());
    }

    /**
     * Get one venda by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Venda> findOne(Long id) {
        log.debug("Request to get Venda : {}", id);
        return vendaRepository.findById(id);
    }

    /**
     * Delete the venda by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Venda : {}", id);
        vendaRepository.deleteById(id);
    }
}
