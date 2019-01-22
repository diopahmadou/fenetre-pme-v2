package com.solenboutique.fenetre_pme_v2.web.rest;

import com.solenboutique.fenetre_pme_v2.FenetrePmEv2App;

import com.solenboutique.fenetre_pme_v2.domain.Pret;
import com.solenboutique.fenetre_pme_v2.repository.PretRepository;
import com.solenboutique.fenetre_pme_v2.service.PretService;
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
import java.util.List;


import static com.solenboutique.fenetre_pme_v2.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.solenboutique.fenetre_pme_v2.domain.enumeration.EtatPret;
/**
 * Test class for the PretResource REST controller.
 *
 * @see PretResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FenetrePmEv2App.class)
public class PretResourceIntTest {

    private static final Long DEFAULT_MONTANT = 1L;
    private static final Long UPDATED_MONTANT = 2L;

    private static final EtatPret DEFAULT_ETAT = EtatPret.ACCORD;
    private static final EtatPret UPDATED_ETAT = EtatPret.REFUS;

    private static final Float DEFAULT_TAUX = 1F;
    private static final Float UPDATED_TAUX = 2F;

    private static final Integer DEFAULT_ECHEANCIER = 1;
    private static final Integer UPDATED_ECHEANCIER = 2;

    private static final String DEFAULT_GARANTIES = "AAAAAAAAAA";
    private static final String UPDATED_GARANTIES = "BBBBBBBBBB";

    @Autowired
    private PretRepository pretRepository;

    @Autowired
    private PretService pretService;

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

    private MockMvc restPretMockMvc;

    private Pret pret;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PretResource pretResource = new PretResource(pretService);
        this.restPretMockMvc = MockMvcBuilders.standaloneSetup(pretResource)
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
    public static Pret createEntity(EntityManager em) {
        Pret pret = new Pret()
            .montant(DEFAULT_MONTANT)
            .etat(DEFAULT_ETAT)
            .taux(DEFAULT_TAUX)
            .echeancier(DEFAULT_ECHEANCIER)
            .garanties(DEFAULT_GARANTIES);
        return pret;
    }

    @Before
    public void initTest() {
        pret = createEntity(em);
    }

    @Test
    @Transactional
    public void createPret() throws Exception {
        int databaseSizeBeforeCreate = pretRepository.findAll().size();

        // Create the Pret
        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isCreated());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeCreate + 1);
        Pret testPret = pretList.get(pretList.size() - 1);
        assertThat(testPret.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testPret.getEtat()).isEqualTo(DEFAULT_ETAT);
        assertThat(testPret.getTaux()).isEqualTo(DEFAULT_TAUX);
        assertThat(testPret.getEcheancier()).isEqualTo(DEFAULT_ECHEANCIER);
        assertThat(testPret.getGaranties()).isEqualTo(DEFAULT_GARANTIES);
    }

    @Test
    @Transactional
    public void createPretWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pretRepository.findAll().size();

        // Create the Pret with an existing ID
        pret.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isBadRequest());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = pretRepository.findAll().size();
        // set the field null
        pret.setMontant(null);

        // Create the Pret, which fails.

        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isBadRequest());

        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEtatIsRequired() throws Exception {
        int databaseSizeBeforeTest = pretRepository.findAll().size();
        // set the field null
        pret.setEtat(null);

        // Create the Pret, which fails.

        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isBadRequest());

        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTauxIsRequired() throws Exception {
        int databaseSizeBeforeTest = pretRepository.findAll().size();
        // set the field null
        pret.setTaux(null);

        // Create the Pret, which fails.

        restPretMockMvc.perform(post("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isBadRequest());

        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrets() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get all the pretList
        restPretMockMvc.perform(get("/api/prets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pret.getId().intValue())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.intValue())))
            .andExpect(jsonPath("$.[*].etat").value(hasItem(DEFAULT_ETAT.toString())))
            .andExpect(jsonPath("$.[*].taux").value(hasItem(DEFAULT_TAUX.doubleValue())))
            .andExpect(jsonPath("$.[*].echeancier").value(hasItem(DEFAULT_ECHEANCIER)))
            .andExpect(jsonPath("$.[*].garanties").value(hasItem(DEFAULT_GARANTIES.toString())));
    }
    
    @Test
    @Transactional
    public void getPret() throws Exception {
        // Initialize the database
        pretRepository.saveAndFlush(pret);

        // Get the pret
        restPretMockMvc.perform(get("/api/prets/{id}", pret.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pret.getId().intValue()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.intValue()))
            .andExpect(jsonPath("$.etat").value(DEFAULT_ETAT.toString()))
            .andExpect(jsonPath("$.taux").value(DEFAULT_TAUX.doubleValue()))
            .andExpect(jsonPath("$.echeancier").value(DEFAULT_ECHEANCIER))
            .andExpect(jsonPath("$.garanties").value(DEFAULT_GARANTIES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPret() throws Exception {
        // Get the pret
        restPretMockMvc.perform(get("/api/prets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePret() throws Exception {
        // Initialize the database
        pretService.save(pret);

        int databaseSizeBeforeUpdate = pretRepository.findAll().size();

        // Update the pret
        Pret updatedPret = pretRepository.findById(pret.getId()).get();
        // Disconnect from session so that the updates on updatedPret are not directly saved in db
        em.detach(updatedPret);
        updatedPret
            .montant(UPDATED_MONTANT)
            .etat(UPDATED_ETAT)
            .taux(UPDATED_TAUX)
            .echeancier(UPDATED_ECHEANCIER)
            .garanties(UPDATED_GARANTIES);

        restPretMockMvc.perform(put("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPret)))
            .andExpect(status().isOk());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeUpdate);
        Pret testPret = pretList.get(pretList.size() - 1);
        assertThat(testPret.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testPret.getEtat()).isEqualTo(UPDATED_ETAT);
        assertThat(testPret.getTaux()).isEqualTo(UPDATED_TAUX);
        assertThat(testPret.getEcheancier()).isEqualTo(UPDATED_ECHEANCIER);
        assertThat(testPret.getGaranties()).isEqualTo(UPDATED_GARANTIES);
    }

    @Test
    @Transactional
    public void updateNonExistingPret() throws Exception {
        int databaseSizeBeforeUpdate = pretRepository.findAll().size();

        // Create the Pret

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPretMockMvc.perform(put("/api/prets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pret)))
            .andExpect(status().isBadRequest());

        // Validate the Pret in the database
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePret() throws Exception {
        // Initialize the database
        pretService.save(pret);

        int databaseSizeBeforeDelete = pretRepository.findAll().size();

        // Get the pret
        restPretMockMvc.perform(delete("/api/prets/{id}", pret.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pret> pretList = pretRepository.findAll();
        assertThat(pretList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pret.class);
        Pret pret1 = new Pret();
        pret1.setId(1L);
        Pret pret2 = new Pret();
        pret2.setId(pret1.getId());
        assertThat(pret1).isEqualTo(pret2);
        pret2.setId(2L);
        assertThat(pret1).isNotEqualTo(pret2);
        pret1.setId(null);
        assertThat(pret1).isNotEqualTo(pret2);
    }
}
