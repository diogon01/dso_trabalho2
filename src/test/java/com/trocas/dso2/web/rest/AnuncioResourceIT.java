package com.trocas.dso2.web.rest;

import com.trocas.dso2.DsoTrabalho2App;
import com.trocas.dso2.domain.Anuncio;
import com.trocas.dso2.repository.AnuncioRepository;
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
import java.util.List;

import static com.trocas.dso2.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.trocas.dso2.domain.enumeration.Categoria;
/**
 * Integration tests for the {@Link AnuncioResource} REST controller.
 */
@SpringBootTest(classes = DsoTrabalho2App.class)
public class AnuncioResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final Long DEFAULT_VALOR_MIN = 1L;
    private static final Long UPDATED_VALOR_MIN = 2L;

    private static final Long DEFAULT_VALOR_MAX = 1L;
    private static final Long UPDATED_VALOR_MAX = 2L;

    private static final Categoria DEFAULT_CATEGORIA = Categoria.LIVRO;
    private static final Categoria UPDATED_CATEGORIA = Categoria.INFORMATICA;

    @Autowired
    private AnuncioRepository anuncioRepository;

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

    private MockMvc restAnuncioMockMvc;

    private Anuncio anuncio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnuncioResource anuncioResource = new AnuncioResource(anuncioRepository);
        this.restAnuncioMockMvc = MockMvcBuilders.standaloneSetup(anuncioResource)
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
    public static Anuncio createEntity(EntityManager em) {
        Anuncio anuncio = new Anuncio()
            .titulo(DEFAULT_TITULO)
            .valorMin(DEFAULT_VALOR_MIN)
            .valorMax(DEFAULT_VALOR_MAX)
            .categoria(DEFAULT_CATEGORIA);
        return anuncio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anuncio createUpdatedEntity(EntityManager em) {
        Anuncio anuncio = new Anuncio()
            .titulo(UPDATED_TITULO)
            .valorMin(UPDATED_VALOR_MIN)
            .valorMax(UPDATED_VALOR_MAX)
            .categoria(UPDATED_CATEGORIA);
        return anuncio;
    }

    @BeforeEach
    public void initTest() {
        anuncio = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnuncio() throws Exception {
        int databaseSizeBeforeCreate = anuncioRepository.findAll().size();

        // Create the Anuncio
        restAnuncioMockMvc.perform(post("/api/anuncios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anuncio)))
            .andExpect(status().isCreated());

        // Validate the Anuncio in the database
        List<Anuncio> anuncioList = anuncioRepository.findAll();
        assertThat(anuncioList).hasSize(databaseSizeBeforeCreate + 1);
        Anuncio testAnuncio = anuncioList.get(anuncioList.size() - 1);
        assertThat(testAnuncio.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testAnuncio.getValorMin()).isEqualTo(DEFAULT_VALOR_MIN);
        assertThat(testAnuncio.getValorMax()).isEqualTo(DEFAULT_VALOR_MAX);
        assertThat(testAnuncio.getCategoria()).isEqualTo(DEFAULT_CATEGORIA);
    }

    @Test
    @Transactional
    public void createAnuncioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anuncioRepository.findAll().size();

        // Create the Anuncio with an existing ID
        anuncio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnuncioMockMvc.perform(post("/api/anuncios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anuncio)))
            .andExpect(status().isBadRequest());

        // Validate the Anuncio in the database
        List<Anuncio> anuncioList = anuncioRepository.findAll();
        assertThat(anuncioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAnuncios() throws Exception {
        // Initialize the database
        anuncioRepository.saveAndFlush(anuncio);

        // Get all the anuncioList
        restAnuncioMockMvc.perform(get("/api/anuncios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anuncio.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].valorMin").value(hasItem(DEFAULT_VALOR_MIN.intValue())))
            .andExpect(jsonPath("$.[*].valorMax").value(hasItem(DEFAULT_VALOR_MAX.intValue())))
            .andExpect(jsonPath("$.[*].categoria").value(hasItem(DEFAULT_CATEGORIA.toString())));
    }
    
    @Test
    @Transactional
    public void getAnuncio() throws Exception {
        // Initialize the database
        anuncioRepository.saveAndFlush(anuncio);

        // Get the anuncio
        restAnuncioMockMvc.perform(get("/api/anuncios/{id}", anuncio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anuncio.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.valorMin").value(DEFAULT_VALOR_MIN.intValue()))
            .andExpect(jsonPath("$.valorMax").value(DEFAULT_VALOR_MAX.intValue()))
            .andExpect(jsonPath("$.categoria").value(DEFAULT_CATEGORIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnuncio() throws Exception {
        // Get the anuncio
        restAnuncioMockMvc.perform(get("/api/anuncios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnuncio() throws Exception {
        // Initialize the database
        anuncioRepository.saveAndFlush(anuncio);

        int databaseSizeBeforeUpdate = anuncioRepository.findAll().size();

        // Update the anuncio
        Anuncio updatedAnuncio = anuncioRepository.findById(anuncio.getId()).get();
        // Disconnect from session so that the updates on updatedAnuncio are not directly saved in db
        em.detach(updatedAnuncio);
        updatedAnuncio
            .titulo(UPDATED_TITULO)
            .valorMin(UPDATED_VALOR_MIN)
            .valorMax(UPDATED_VALOR_MAX)
            .categoria(UPDATED_CATEGORIA);

        restAnuncioMockMvc.perform(put("/api/anuncios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnuncio)))
            .andExpect(status().isOk());

        // Validate the Anuncio in the database
        List<Anuncio> anuncioList = anuncioRepository.findAll();
        assertThat(anuncioList).hasSize(databaseSizeBeforeUpdate);
        Anuncio testAnuncio = anuncioList.get(anuncioList.size() - 1);
        assertThat(testAnuncio.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testAnuncio.getValorMin()).isEqualTo(UPDATED_VALOR_MIN);
        assertThat(testAnuncio.getValorMax()).isEqualTo(UPDATED_VALOR_MAX);
        assertThat(testAnuncio.getCategoria()).isEqualTo(UPDATED_CATEGORIA);
    }

    @Test
    @Transactional
    public void updateNonExistingAnuncio() throws Exception {
        int databaseSizeBeforeUpdate = anuncioRepository.findAll().size();

        // Create the Anuncio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnuncioMockMvc.perform(put("/api/anuncios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anuncio)))
            .andExpect(status().isBadRequest());

        // Validate the Anuncio in the database
        List<Anuncio> anuncioList = anuncioRepository.findAll();
        assertThat(anuncioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnuncio() throws Exception {
        // Initialize the database
        anuncioRepository.saveAndFlush(anuncio);

        int databaseSizeBeforeDelete = anuncioRepository.findAll().size();

        // Delete the anuncio
        restAnuncioMockMvc.perform(delete("/api/anuncios/{id}", anuncio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Anuncio> anuncioList = anuncioRepository.findAll();
        assertThat(anuncioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anuncio.class);
        Anuncio anuncio1 = new Anuncio();
        anuncio1.setId(1L);
        Anuncio anuncio2 = new Anuncio();
        anuncio2.setId(anuncio1.getId());
        assertThat(anuncio1).isEqualTo(anuncio2);
        anuncio2.setId(2L);
        assertThat(anuncio1).isNotEqualTo(anuncio2);
        anuncio1.setId(null);
        assertThat(anuncio1).isNotEqualTo(anuncio2);
    }
}
