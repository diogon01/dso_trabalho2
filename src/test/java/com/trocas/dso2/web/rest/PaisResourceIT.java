package com.trocas.dso2.web.rest;

import com.trocas.dso2.DsoTrabalho2App;
import com.trocas.dso2.domain.Pais;
import com.trocas.dso2.repository.PaisRepository;
import com.trocas.dso2.service.PaisService;
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

/**
 * Integration tests for the {@Link PaisResource} REST controller.
 */
@SpringBootTest(classes = DsoTrabalho2App.class)
public class PaisResourceIT {

    private static final String DEFAULT_NOME_PAIS = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PAIS = "BBBBBBBBBB";

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private PaisService paisService;

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

    private MockMvc restPaisMockMvc;

    private Pais pais;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaisResource paisResource = new PaisResource(paisService);
        this.restPaisMockMvc = MockMvcBuilders.standaloneSetup(paisResource)
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
    public static Pais createEntity(EntityManager em) {
        Pais pais = new Pais()
            .nomePais(DEFAULT_NOME_PAIS);
        return pais;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pais createUpdatedEntity(EntityManager em) {
        Pais pais = new Pais()
            .nomePais(UPDATED_NOME_PAIS);
        return pais;
    }

    @BeforeEach
    public void initTest() {
        pais = createEntity(em);
    }

    @Test
    @Transactional
    public void createPais() throws Exception {
        int databaseSizeBeforeCreate = paisRepository.findAll().size();

        // Create the Pais
        restPaisMockMvc.perform(post("/api/pais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pais)))
            .andExpect(status().isCreated());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeCreate + 1);
        Pais testPais = paisList.get(paisList.size() - 1);
        assertThat(testPais.getNomePais()).isEqualTo(DEFAULT_NOME_PAIS);
    }

    @Test
    @Transactional
    public void createPaisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paisRepository.findAll().size();

        // Create the Pais with an existing ID
        pais.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaisMockMvc.perform(post("/api/pais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pais)))
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPais() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get all the paisList
        restPaisMockMvc.perform(get("/api/pais?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pais.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomePais").value(hasItem(DEFAULT_NOME_PAIS.toString())));
    }
    
    @Test
    @Transactional
    public void getPais() throws Exception {
        // Initialize the database
        paisRepository.saveAndFlush(pais);

        // Get the pais
        restPaisMockMvc.perform(get("/api/pais/{id}", pais.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pais.getId().intValue()))
            .andExpect(jsonPath("$.nomePais").value(DEFAULT_NOME_PAIS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPais() throws Exception {
        // Get the pais
        restPaisMockMvc.perform(get("/api/pais/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePais() throws Exception {
        // Initialize the database
        paisService.save(pais);

        int databaseSizeBeforeUpdate = paisRepository.findAll().size();

        // Update the pais
        Pais updatedPais = paisRepository.findById(pais.getId()).get();
        // Disconnect from session so that the updates on updatedPais are not directly saved in db
        em.detach(updatedPais);
        updatedPais
            .nomePais(UPDATED_NOME_PAIS);

        restPaisMockMvc.perform(put("/api/pais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPais)))
            .andExpect(status().isOk());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
        Pais testPais = paisList.get(paisList.size() - 1);
        assertThat(testPais.getNomePais()).isEqualTo(UPDATED_NOME_PAIS);
    }

    @Test
    @Transactional
    public void updateNonExistingPais() throws Exception {
        int databaseSizeBeforeUpdate = paisRepository.findAll().size();

        // Create the Pais

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaisMockMvc.perform(put("/api/pais")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pais)))
            .andExpect(status().isBadRequest());

        // Validate the Pais in the database
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePais() throws Exception {
        // Initialize the database
        paisService.save(pais);

        int databaseSizeBeforeDelete = paisRepository.findAll().size();

        // Delete the pais
        restPaisMockMvc.perform(delete("/api/pais/{id}", pais.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Pais> paisList = paisRepository.findAll();
        assertThat(paisList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pais.class);
        Pais pais1 = new Pais();
        pais1.setId(1L);
        Pais pais2 = new Pais();
        pais2.setId(pais1.getId());
        assertThat(pais1).isEqualTo(pais2);
        pais2.setId(2L);
        assertThat(pais1).isNotEqualTo(pais2);
        pais1.setId(null);
        assertThat(pais1).isNotEqualTo(pais2);
    }
}
