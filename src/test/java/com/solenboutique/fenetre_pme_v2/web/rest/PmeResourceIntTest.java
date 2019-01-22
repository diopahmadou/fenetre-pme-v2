package com.solenboutique.fenetre_pme_v2.web.rest;

import com.solenboutique.fenetre_pme_v2.FenetrePmEv2App;

import com.solenboutique.fenetre_pme_v2.domain.Pme;
import com.solenboutique.fenetre_pme_v2.repository.PmeRepository;
import com.solenboutique.fenetre_pme_v2.service.PmeService;
import com.solenboutique.fenetre_pme_v2.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static com.solenboutique.fenetre_pme_v2.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.solenboutique.fenetre_pme_v2.domain.enumeration.FormeJuridique;
/**
 * Test class for the PmeResource REST controller.
 *
 * @see PmeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FenetrePmEv2App.class)
public class PmeResourceIntTest {

    private static final Integer DEFAULT_NUMERO_DOSSIER = 1;
    private static final Integer UPDATED_NUMERO_DOSSIER = 2;

    private static final String DEFAULT_PME_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PME_NAME = "BBBBBBBBBB";

    private static final FormeJuridique DEFAULT_FORME_JURIDIQUE = FormeJuridique.SARL;
    private static final FormeJuridique UPDATED_FORME_JURIDIQUE = FormeJuridique.SURL;

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_ENREGISTREMENT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_ENREGISTREMENT = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITE = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENTEL = "AAAAAAAAAA";
    private static final String UPDATED_CLIENTEL = "BBBBBBBBBB";

    private static final Double DEFAULT_CA_PASSE = 1D;
    private static final Double UPDATED_CA_PASSE = 2D;

    private static final Double DEFAULT_CA_PREVU = 1D;
    private static final Double UPDATED_CA_PREVU = 2D;

    @Autowired
    private PmeRepository pmeRepository;

    @Autowired
    private PmeService pmeService;

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

    private MockMvc restPmeMockMvc;

    private Pme pme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PmeResource pmeResource = new PmeResource(pmeService);
        this.restPmeMockMvc = MockMvcBuilders.standaloneSetup(pmeResource)
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
    public static Pme createEntity(EntityManager em) {
        Pme pme = new Pme()
            .numeroDossier(DEFAULT_NUMERO_DOSSIER)
            .pmeName(DEFAULT_PME_NAME)
            .formeJuridique(DEFAULT_FORME_JURIDIQUE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .dateEnregistrement(DEFAULT_DATE_ENREGISTREMENT)
            .type(DEFAULT_TYPE)
            .activite(DEFAULT_ACTIVITE)
            .clientel(DEFAULT_CLIENTEL)
            .caPasse(DEFAULT_CA_PASSE)
            .caPrevu(DEFAULT_CA_PREVU);
        return pme;
    }

    @Before
    public void initTest() {
        pme = createEntity(em);
    }

    @Test
    @Transactional
    public void createPme() throws Exception {
        int databaseSizeBeforeCreate = pmeRepository.findAll().size();

        // Create the Pme
        restPmeMockMvc.perform(post("/api/pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pme)))
            .andExpect(status().isCreated());

        // Validate the Pme in the database
        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).hasSize(databaseSizeBeforeCreate + 1);
        Pme testPme = pmeList.get(pmeList.size() - 1);
        assertThat(testPme.getNumeroDossier()).isEqualTo(DEFAULT_NUMERO_DOSSIER);
        assertThat(testPme.getPmeName()).isEqualTo(DEFAULT_PME_NAME);
        assertThat(testPme.getFormeJuridique()).isEqualTo(DEFAULT_FORME_JURIDIQUE);
        assertThat(testPme.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testPme.getDateEnregistrement()).isEqualTo(DEFAULT_DATE_ENREGISTREMENT);
        assertThat(testPme.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPme.getActivite()).isEqualTo(DEFAULT_ACTIVITE);
        assertThat(testPme.getClientel()).isEqualTo(DEFAULT_CLIENTEL);
        assertThat(testPme.getCaPasse()).isEqualTo(DEFAULT_CA_PASSE);
        assertThat(testPme.getCaPrevu()).isEqualTo(DEFAULT_CA_PREVU);
    }

    @Test
    @Transactional
    public void createPmeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pmeRepository.findAll().size();

        // Create the Pme with an existing ID
        pme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPmeMockMvc.perform(post("/api/pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pme)))
            .andExpect(status().isBadRequest());

        // Validate the Pme in the database
        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroDossierIsRequired() throws Exception {
        int databaseSizeBeforeTest = pmeRepository.findAll().size();
        // set the field null
        pme.setNumeroDossier(null);

        // Create the Pme, which fails.

        restPmeMockMvc.perform(post("/api/pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pme)))
            .andExpect(status().isBadRequest());

        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPmeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = pmeRepository.findAll().size();
        // set the field null
        pme.setPmeName(null);

        // Create the Pme, which fails.

        restPmeMockMvc.perform(post("/api/pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pme)))
            .andExpect(status().isBadRequest());

        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFormeJuridiqueIsRequired() throws Exception {
        int databaseSizeBeforeTest = pmeRepository.findAll().size();
        // set the field null
        pme.setFormeJuridique(null);

        // Create the Pme, which fails.

        restPmeMockMvc.perform(post("/api/pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pme)))
            .andExpect(status().isBadRequest());

        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreationIsRequired() throws Exception {
        int databaseSizeBeforeTest = pmeRepository.findAll().size();
        // set the field null
        pme.setDateCreation(null);

        // Create the Pme, which fails.

        restPmeMockMvc.perform(post("/api/pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pme)))
            .andExpect(status().isBadRequest());

        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = pmeRepository.findAll().size();
        // set the field null
        pme.setType(null);

        // Create the Pme, which fails.

        restPmeMockMvc.perform(post("/api/pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pme)))
            .andExpect(status().isBadRequest());

        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActiviteIsRequired() throws Exception {
        int databaseSizeBeforeTest = pmeRepository.findAll().size();
        // set the field null
        pme.setActivite(null);

        // Create the Pme, which fails.

        restPmeMockMvc.perform(post("/api/pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pme)))
            .andExpect(status().isBadRequest());

        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientelIsRequired() throws Exception {
        int databaseSizeBeforeTest = pmeRepository.findAll().size();
        // set the field null
        pme.setClientel(null);

        // Create the Pme, which fails.

        restPmeMockMvc.perform(post("/api/pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pme)))
            .andExpect(status().isBadRequest());

        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPmes() throws Exception {
        // Initialize the database
        pmeRepository.saveAndFlush(pme);

        // Get all the pmeList
        restPmeMockMvc.perform(get("/api/pmes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pme.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDossier").value(hasItem(DEFAULT_NUMERO_DOSSIER)))
            .andExpect(jsonPath("$.[*].pmeName").value(hasItem(DEFAULT_PME_NAME.toString())))
            .andExpect(jsonPath("$.[*].formeJuridique").value(hasItem(DEFAULT_FORME_JURIDIQUE.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].dateEnregistrement").value(hasItem(DEFAULT_DATE_ENREGISTREMENT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].activite").value(hasItem(DEFAULT_ACTIVITE.toString())))
            .andExpect(jsonPath("$.[*].clientel").value(hasItem(DEFAULT_CLIENTEL.toString())))
            .andExpect(jsonPath("$.[*].caPasse").value(hasItem(DEFAULT_CA_PASSE.doubleValue())))
            .andExpect(jsonPath("$.[*].caPrevu").value(hasItem(DEFAULT_CA_PREVU.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPme() throws Exception {
        // Initialize the database
        pmeRepository.saveAndFlush(pme);

        // Get the pme
        restPmeMockMvc.perform(get("/api/pmes/{id}", pme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pme.getId().intValue()))
            .andExpect(jsonPath("$.numeroDossier").value(DEFAULT_NUMERO_DOSSIER))
            .andExpect(jsonPath("$.pmeName").value(DEFAULT_PME_NAME.toString()))
            .andExpect(jsonPath("$.formeJuridique").value(DEFAULT_FORME_JURIDIQUE.toString()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.dateEnregistrement").value(DEFAULT_DATE_ENREGISTREMENT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.activite").value(DEFAULT_ACTIVITE.toString()))
            .andExpect(jsonPath("$.clientel").value(DEFAULT_CLIENTEL.toString()))
            .andExpect(jsonPath("$.caPasse").value(DEFAULT_CA_PASSE.doubleValue()))
            .andExpect(jsonPath("$.caPrevu").value(DEFAULT_CA_PREVU.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPme() throws Exception {
        // Get the pme
        restPmeMockMvc.perform(get("/api/pmes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePme() throws Exception {
        // Initialize the database
        pmeService.save(pme);

        int databaseSizeBeforeUpdate = pmeRepository.findAll().size();

        // Update the pme
        Pme updatedPme = pmeRepository.findById(pme.getId()).get();
        // Disconnect from session so that the updates on updatedPme are not directly saved in db
        em.detach(updatedPme);
        updatedPme
            .numeroDossier(UPDATED_NUMERO_DOSSIER)
            .pmeName(UPDATED_PME_NAME)
            .formeJuridique(UPDATED_FORME_JURIDIQUE)
            .dateCreation(UPDATED_DATE_CREATION)
            .dateEnregistrement(UPDATED_DATE_ENREGISTREMENT)
            .type(UPDATED_TYPE)
            .activite(UPDATED_ACTIVITE)
            .clientel(UPDATED_CLIENTEL)
            .caPasse(UPDATED_CA_PASSE)
            .caPrevu(UPDATED_CA_PREVU);

        restPmeMockMvc.perform(put("/api/pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPme)))
            .andExpect(status().isOk());

        // Validate the Pme in the database
        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).hasSize(databaseSizeBeforeUpdate);
        Pme testPme = pmeList.get(pmeList.size() - 1);
        assertThat(testPme.getNumeroDossier()).isEqualTo(UPDATED_NUMERO_DOSSIER);
        assertThat(testPme.getPmeName()).isEqualTo(UPDATED_PME_NAME);
        assertThat(testPme.getFormeJuridique()).isEqualTo(UPDATED_FORME_JURIDIQUE);
        assertThat(testPme.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testPme.getDateEnregistrement()).isEqualTo(UPDATED_DATE_ENREGISTREMENT);
        assertThat(testPme.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPme.getActivite()).isEqualTo(UPDATED_ACTIVITE);
        assertThat(testPme.getClientel()).isEqualTo(UPDATED_CLIENTEL);
        assertThat(testPme.getCaPasse()).isEqualTo(UPDATED_CA_PASSE);
        assertThat(testPme.getCaPrevu()).isEqualTo(UPDATED_CA_PREVU);
    }

    @Test
    @Transactional
    public void updateNonExistingPme() throws Exception {
        int databaseSizeBeforeUpdate = pmeRepository.findAll().size();

        // Create the Pme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPmeMockMvc.perform(put("/api/pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pme)))
            .andExpect(status().isBadRequest());

        // Validate the Pme in the database
        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePme() throws Exception {
        // Initialize the database
        pmeService.save(pme);

        int databaseSizeBeforeDelete = pmeRepository.findAll().size();

        // Get the pme
        restPmeMockMvc.perform(delete("/api/pmes/{id}", pme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pme> pmeList = pmeRepository.findAll();
        assertThat(pmeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pme.class);
        Pme pme1 = new Pme();
        pme1.setId(1L);
        Pme pme2 = new Pme();
        pme2.setId(pme1.getId());
        assertThat(pme1).isEqualTo(pme2);
        pme2.setId(2L);
        assertThat(pme1).isNotEqualTo(pme2);
        pme1.setId(null);
        assertThat(pme1).isNotEqualTo(pme2);
    }
}
