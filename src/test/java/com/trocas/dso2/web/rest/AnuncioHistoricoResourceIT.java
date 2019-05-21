package com.trocas.dso2.web.rest;

import com.trocas.dso2.DsoTrabalho2App;
import com.trocas.dso2.domain.AnuncioHistorico;
import com.trocas.dso2.repository.AnuncioHistoricoRepository;
import com.trocas.dso2.service.AnuncioHistoricoService;
import com.trocas.dso2.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.trocas.dso2.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link AnuncioHistoricoResource} REST controller.
 */
@SpringBootTest(classes = DsoTrabalho2App.class)
public class AnuncioHistoricoResourceIT {

    private static final Instant DEFAULT_DATA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA_FINAL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_FINAL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AnuncioHistoricoRepository anuncioHistoricoRepository;

    @Autowired
    private AnuncioHistoricoService anuncioHistoricoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAnuncioHistoricoMockMvc;

    private AnuncioHistorico anuncioHistorico;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnuncioHistoricoResource anuncioHistoricoResource = new AnuncioHistoricoResource(anuncioHistoricoService);
        this.restAnuncioHistoricoMockMvc = MockMvcBuilders.standaloneSetup(anuncioHistoricoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnuncioHistorico createEntity(EntityManager em) {
        AnuncioHistorico anuncioHistorico = new AnuncioHistorico()
            .dataInicio(DEFAULT_DATA_INICIO)
            .dataFinal(DEFAULT_DATA_FINAL);
        return anuncioHistorico;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnuncioHistorico createUpdatedEntity(EntityManager em) {
        AnuncioHistorico anuncioHistorico = new AnuncioHistorico()
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFinal(UPDATED_DATA_FINAL);
        return anuncioHistorico;
    }

    @BeforeEach
    public void initTest() {
        anuncioHistorico = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnuncioHistorico() throws Exception {
        int databaseSizeBeforeCreate = anuncioHistoricoRepository.findAll().size();

        // Create the AnuncioHistorico
        restAnuncioHistoricoMockMvc.perform(post("/api/anuncio-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anuncioHistorico)))
            .andExpect(status().isCreated());

        // Validate the AnuncioHistorico in the database
        List<AnuncioHistorico> anuncioHistoricoList = anuncioHistoricoRepository.findAll();
        assertThat(anuncioHistoricoList).hasSize(databaseSizeBeforeCreate + 1);
        AnuncioHistorico testAnuncioHistorico = anuncioHistoricoList.get(anuncioHistoricoList.size() - 1);
        assertThat(testAnuncioHistorico.getDataInicio()).isEqualTo(DEFAULT_DATA_INICIO);
        assertThat(testAnuncioHistorico.getDataFinal()).isEqualTo(DEFAULT_DATA_FINAL);
    }

    @Test
    @Transactional
    public void createAnuncioHistoricoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anuncioHistoricoRepository.findAll().size();

        // Create the AnuncioHistorico with an existing ID
        anuncioHistorico.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnuncioHistoricoMockMvc.perform(post("/api/anuncio-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anuncioHistorico)))
            .andExpect(status().isBadRequest());

        // Validate the AnuncioHistorico in the database
        List<AnuncioHistorico> anuncioHistoricoList = anuncioHistoricoRepository.findAll();
        assertThat(anuncioHistoricoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnuncioHistoricos() throws Exception {
        // Initialize the database
        anuncioHistoricoRepository.saveAndFlush(anuncioHistorico);

        // Get all the anuncioHistoricoList
        restAnuncioHistoricoMockMvc.perform(get("/api/anuncio-historicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anuncioHistorico.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataInicio").value(hasItem(DEFAULT_DATA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].dataFinal").value(hasItem(DEFAULT_DATA_FINAL.toString())));
    }
    
    @Test
    @Transactional
    public void getAnuncioHistorico() throws Exception {
        // Initialize the database
        anuncioHistoricoRepository.saveAndFlush(anuncioHistorico);

        // Get the anuncioHistorico
        restAnuncioHistoricoMockMvc.perform(get("/api/anuncio-historicos/{id}", anuncioHistorico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anuncioHistorico.getId().intValue()))
            .andExpect(jsonPath("$.dataInicio").value(DEFAULT_DATA_INICIO.toString()))
            .andExpect(jsonPath("$.dataFinal").value(DEFAULT_DATA_FINAL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnuncioHistorico() throws Exception {
        // Get the anuncioHistorico
        restAnuncioHistoricoMockMvc.perform(get("/api/anuncio-historicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnuncioHistorico() throws Exception {
        // Initialize the database
        anuncioHistoricoService.save(anuncioHistorico);

        int databaseSizeBeforeUpdate = anuncioHistoricoRepository.findAll().size();

        // Update the anuncioHistorico
        AnuncioHistorico updatedAnuncioHistorico = anuncioHistoricoRepository.findById(anuncioHistorico.getId()).get();
        // Disconnect from session so that the updates on updatedAnuncioHistorico are not directly saved in db
        em.detach(updatedAnuncioHistorico);
        updatedAnuncioHistorico
            .dataInicio(UPDATED_DATA_INICIO)
            .dataFinal(UPDATED_DATA_FINAL);

        restAnuncioHistoricoMockMvc.perform(put("/api/anuncio-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnuncioHistorico)))
            .andExpect(status().isOk());

        // Validate the AnuncioHistorico in the database
        List<AnuncioHistorico> anuncioHistoricoList = anuncioHistoricoRepository.findAll();
        assertThat(anuncioHistoricoList).hasSize(databaseSizeBeforeUpdate);
        AnuncioHistorico testAnuncioHistorico = anuncioHistoricoList.get(anuncioHistoricoList.size() - 1);
        assertThat(testAnuncioHistorico.getDataInicio()).isEqualTo(UPDATED_DATA_INICIO);
        assertThat(testAnuncioHistorico.getDataFinal()).isEqualTo(UPDATED_DATA_FINAL);
    }

    @Test
    @Transactional
    public void updateNonExistingAnuncioHistorico() throws Exception {
        int databaseSizeBeforeUpdate = anuncioHistoricoRepository.findAll().size();

        // Create the AnuncioHistorico

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnuncioHistoricoMockMvc.perform(put("/api/anuncio-historicos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anuncioHistorico)))
            .andExpect(status().isBadRequest());

        // Validate the AnuncioHistorico in the database
        List<AnuncioHistorico> anuncioHistoricoList = anuncioHistoricoRepository.findAll();
        assertThat(anuncioHistoricoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnuncioHistorico() throws Exception {
        // Initialize the database
        anuncioHistoricoService.save(anuncioHistorico);

        int databaseSizeBeforeDelete = anuncioHistoricoRepository.findAll().size();

        // Delete the anuncioHistorico
        restAnuncioHistoricoMockMvc.perform(delete("/api/anuncio-historicos/{id}", anuncioHistorico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AnuncioHistorico> anuncioHistoricoList = anuncioHistoricoRepository.findAll();
        assertThat(anuncioHistoricoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnuncioHistorico.class);
        AnuncioHistorico anuncioHistorico1 = new AnuncioHistorico();
        anuncioHistorico1.setId(1L);
        AnuncioHistorico anuncioHistorico2 = new AnuncioHistorico();
        anuncioHistorico2.setId(anuncioHistorico1.getId());
        assertThat(anuncioHistorico1).isEqualTo(anuncioHistorico2);
        anuncioHistorico2.setId(2L);
        assertThat(anuncioHistorico1).isNotEqualTo(anuncioHistorico2);
        anuncioHistorico1.setId(null);
        assertThat(anuncioHistorico1).isNotEqualTo(anuncioHistorico2);
    }
}
