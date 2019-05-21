package com.trocas.dso2.web.rest;

import com.trocas.dso2.domain.Venda;
import com.trocas.dso2.service.VendaService;
import com.trocas.dso2.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.trocas.dso2.domain.Venda}.
 */
@RestController
@RequestMapping("/api")
public class VendaResource {

    private final Logger log = LoggerFactory.getLogger(VendaResource.class);

    private static final String ENTITY_NAME = "venda";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VendaService vendaService;

    public VendaResource(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    /**
     * {@code POST  /vendas} : Create a new venda.
     *
     * @param venda the venda to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new venda, or with status {@code 400 (Bad Request)} if the venda has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vendas")
    public ResponseEntity<Venda> createVenda(@RequestBody Venda venda) throws URISyntaxException {
        log.debug("REST request to save Venda : {}", venda);
        if (venda.getId() != null) {
            throw new BadRequestAlertException("A new venda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Venda result = vendaService.save(venda);
        return ResponseEntity.created(new URI("/api/vendas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vendas} : Updates an existing venda.
     *
     * @param venda the venda to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated venda,
     * or with status {@code 400 (Bad Request)} if the venda is not valid,
     * or with status {@code 500 (Internal Server Error)} if the venda couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vendas")
    public ResponseEntity<Venda> updateVenda(@RequestBody Venda venda) throws URISyntaxException {
        log.debug("REST request to update Venda : {}", venda);
        if (venda.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Venda result = vendaService.save(venda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, venda.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vendas} : get all the vendas.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vendas in body.
     */
    @GetMapping("/vendas")
    public List<Venda> getAllVendas(@RequestParam(required = false) String filter) {
        if ("job-is-null".equals(filter)) {
            log.debug("REST request to get all Vendas where job is null");
            return vendaService.findAllWhereJobIsNull();
        }
        log.debug("REST request to get all Vendas");
        return vendaService.findAll();
    }

    /**
     * {@code GET  /vendas/:id} : get the "id" venda.
     *
     * @param id the id of the venda to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the venda, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vendas/{id}")
    public ResponseEntity<Venda> getVenda(@PathVariable Long id) {
        log.debug("REST request to get Venda : {}", id);
        Optional<Venda> venda = vendaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(venda);
    }

    /**
     * {@code DELETE  /vendas/:id} : delete the "id" venda.
     *
     * @param id the id of the venda to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vendas/{id}")
    public ResponseEntity<Void> deleteVenda(@PathVariable Long id) {
        log.debug("REST request to delete Venda : {}", id);
        vendaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
