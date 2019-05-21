package com.trocas.dso2.web.rest;

import com.trocas.dso2.domain.AnuncioHistorico;
import com.trocas.dso2.service.AnuncioHistoricoService;
import com.trocas.dso2.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.trocas.dso2.domain.AnuncioHistorico}.
 */
@RestController
@RequestMapping("/api")
public class AnuncioHistoricoResource {

    private final Logger log = LoggerFactory.getLogger(AnuncioHistoricoResource.class);

    private static final String ENTITY_NAME = "anuncioHistorico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnuncioHistoricoService anuncioHistoricoService;

    public AnuncioHistoricoResource(AnuncioHistoricoService anuncioHistoricoService) {
        this.anuncioHistoricoService = anuncioHistoricoService;
    }

    /**
     * {@code POST  /anuncio-historicos} : Create a new anuncioHistorico.
     *
     * @param anuncioHistorico the anuncioHistorico to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anuncioHistorico, or with status {@code 400 (Bad Request)} if the anuncioHistorico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anuncio-historicos")
    public ResponseEntity<AnuncioHistorico> createAnuncioHistorico(@RequestBody AnuncioHistorico anuncioHistorico) throws URISyntaxException {
        log.debug("REST request to save AnuncioHistorico : {}", anuncioHistorico);
        if (anuncioHistorico.getId() != null) {
            throw new BadRequestAlertException("A new anuncioHistorico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnuncioHistorico result = anuncioHistoricoService.save(anuncioHistorico);
        return ResponseEntity.created(new URI("/api/anuncio-historicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /anuncio-historicos} : Updates an existing anuncioHistorico.
     *
     * @param anuncioHistorico the anuncioHistorico to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anuncioHistorico,
     * or with status {@code 400 (Bad Request)} if the anuncioHistorico is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anuncioHistorico couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anuncio-historicos")
    public ResponseEntity<AnuncioHistorico> updateAnuncioHistorico(@RequestBody AnuncioHistorico anuncioHistorico) throws URISyntaxException {
        log.debug("REST request to update AnuncioHistorico : {}", anuncioHistorico);
        if (anuncioHistorico.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnuncioHistorico result = anuncioHistoricoService.save(anuncioHistorico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anuncioHistorico.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /anuncio-historicos} : get all the anuncioHistoricos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anuncioHistoricos in body.
     */
    @GetMapping("/anuncio-historicos")
    public ResponseEntity<List<AnuncioHistorico>> getAllAnuncioHistoricos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of AnuncioHistoricos");
        Page<AnuncioHistorico> page = anuncioHistoricoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /anuncio-historicos/:id} : get the "id" anuncioHistorico.
     *
     * @param id the id of the anuncioHistorico to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anuncioHistorico, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anuncio-historicos/{id}")
    public ResponseEntity<AnuncioHistorico> getAnuncioHistorico(@PathVariable Long id) {
        log.debug("REST request to get AnuncioHistorico : {}", id);
        Optional<AnuncioHistorico> anuncioHistorico = anuncioHistoricoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(anuncioHistorico);
    }

    /**
     * {@code DELETE  /anuncio-historicos/:id} : delete the "id" anuncioHistorico.
     *
     * @param id the id of the anuncioHistorico to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anuncio-historicos/{id}")
    public ResponseEntity<Void> deleteAnuncioHistorico(@PathVariable Long id) {
        log.debug("REST request to delete AnuncioHistorico : {}", id);
        anuncioHistoricoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
