package com.trocas.dso2.web.rest;

import com.trocas.dso2.domain.Anuncio;
import com.trocas.dso2.repository.AnuncioRepository;
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
 * REST controller for managing {@link com.trocas.dso2.domain.Anuncio}.
 */
@RestController
@RequestMapping("/api")
public class AnuncioResource {

    private final Logger log = LoggerFactory.getLogger(AnuncioResource.class);

    private static final String ENTITY_NAME = "anuncio";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnuncioRepository anuncioRepository;

    public AnuncioResource(AnuncioRepository anuncioRepository) {
        this.anuncioRepository = anuncioRepository;
    }

    /**
     * {@code POST  /anuncios} : Create a new anuncio.
     *
     * @param anuncio the anuncio to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new anuncio, or with status {@code 400 (Bad Request)} if the anuncio has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/anuncios")
    public ResponseEntity<Anuncio> createAnuncio(@RequestBody Anuncio anuncio) throws URISyntaxException {
        log.debug("REST request to save Anuncio : {}", anuncio);
        if (anuncio.getId() != null) {
            throw new BadRequestAlertException("A new anuncio cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Anuncio result = anuncioRepository.save(anuncio);
        return ResponseEntity.created(new URI("/api/anuncios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /anuncios} : Updates an existing anuncio.
     *
     * @param anuncio the anuncio to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated anuncio,
     * or with status {@code 400 (Bad Request)} if the anuncio is not valid,
     * or with status {@code 500 (Internal Server Error)} if the anuncio couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/anuncios")
    public ResponseEntity<Anuncio> updateAnuncio(@RequestBody Anuncio anuncio) throws URISyntaxException {
        log.debug("REST request to update Anuncio : {}", anuncio);
        if (anuncio.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Anuncio result = anuncioRepository.save(anuncio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, anuncio.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /anuncios} : get all the anuncios.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of anuncios in body.
     */
    @GetMapping("/anuncios")
    public ResponseEntity<List<Anuncio>> getAllAnuncios(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Anuncios");
        Page<Anuncio> page = anuncioRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /anuncios/:id} : get the "id" anuncio.
     *
     * @param id the id of the anuncio to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the anuncio, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/anuncios/{id}")
    public ResponseEntity<Anuncio> getAnuncio(@PathVariable Long id) {
        log.debug("REST request to get Anuncio : {}", id);
        Optional<Anuncio> anuncio = anuncioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(anuncio);
    }

    /**
     * {@code DELETE  /anuncios/:id} : delete the "id" anuncio.
     *
     * @param id the id of the anuncio to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/anuncios/{id}")
    public ResponseEntity<Void> deleteAnuncio(@PathVariable Long id) {
        log.debug("REST request to delete Anuncio : {}", id);
        anuncioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
