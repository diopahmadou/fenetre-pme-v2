package com.solenboutique.fenetre_pme_v2.web.rest;

import com.solenboutique.fenetre_pme_v2.FenetrePmEv2App;

import com.solenboutique.fenetre_pme_v2.domain.Imf;
import com.solenboutique.fenetre_pme_v2.repository.ImfRepository;
import com.solenboutique.fenetre_pme_v2.service.ImfService;
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

/**
 * Test class for the ImfResource REST controller.
 *
 * @see ImfResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FenetrePmEv2App.class)
public class ImfResourceIntTest {

    private static final String DEFAULT_AGREMENT = "AAAAAAAAAA";
    private static final String UPDATED_AGREMENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_SIEGE_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_SIEGE_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRE_COMMERCE = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRE_COMMERCE = "BBBBBBBBBB";

    private static final String DEFAULT_NINEA = "AAAAAAAAAA";
    private static final String UPDATED_NINEA = "BBBBBBBBBB";

    private static final String DEFAULT_SECTEUR_ACTIVITE = "AAAAAAAAAA";
    private static final String UPDATED_SECTEUR_ACTIVITE = "BBBBBBBBBB";

    private static final String DEFAULT_FORME_JURIDIQUE = "AAAAAAAAAA";
    private static final String UPDATED_FORME_JURIDIQUE = "BBBBBBBBBB";

    private static final Long DEFAULT_CAPITAL = 1L;
    private static final Long UPDATED_CAPITAL = 2L;

    private static final String DEFAULT_ADRESSE_AGENCE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE_AGENCE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "u@R.j";
    private static final String UPDATED_EMAIL = "Yt@|.\\";

    @Autowired
    private ImfRepository imfRepository;

    @Autowired
    private ImfService imfService;

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

    private MockMvc restImfMockMvc;

    private Imf imf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ImfResource imfResource = new ImfResource(imfService);
        this.restImfMockMvc = MockMvcBuilders.standaloneSetup(imfResource)
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
    public static Imf createEntity(EntityManager em) {
        Imf imf = new Imf()
            .agrement(DEFAULT_AGREMENT)
            .nom(DEFAULT_NOM)
            .siegeSocial(DEFAULT_SIEGE_SOCIAL)
            .registreCommerce(DEFAULT_REGISTRE_COMMERCE)
            .ninea(DEFAULT_NINEA)
            .secteurActivite(DEFAULT_SECTEUR_ACTIVITE)
            .formeJuridique(DEFAULT_FORME_JURIDIQUE)
            .capital(DEFAULT_CAPITAL)
            .adresseAgence(DEFAULT_ADRESSE_AGENCE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL);
        return imf;
    }

    @Before
    public void initTest() {
        imf = createEntity(em);
    }

    @Test
    @Transactional
    public void createImf() throws Exception {
        int databaseSizeBeforeCreate = imfRepository.findAll().size();

        // Create the Imf
        restImfMockMvc.perform(post("/api/imfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imf)))
            .andExpect(status().isCreated());

        // Validate the Imf in the database
        List<Imf> imfList = imfRepository.findAll();
        assertThat(imfList).hasSize(databaseSizeBeforeCreate + 1);
        Imf testImf = imfList.get(imfList.size() - 1);
        assertThat(testImf.getAgrement()).isEqualTo(DEFAULT_AGREMENT);
        assertThat(testImf.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testImf.getSiegeSocial()).isEqualTo(DEFAULT_SIEGE_SOCIAL);
        assertThat(testImf.getRegistreCommerce()).isEqualTo(DEFAULT_REGISTRE_COMMERCE);
        assertThat(testImf.getNinea()).isEqualTo(DEFAULT_NINEA);
        assertThat(testImf.getSecteurActivite()).isEqualTo(DEFAULT_SECTEUR_ACTIVITE);
        assertThat(testImf.getFormeJuridique()).isEqualTo(DEFAULT_FORME_JURIDIQUE);
        assertThat(testImf.getCapital()).isEqualTo(DEFAULT_CAPITAL);
        assertThat(testImf.getAdresseAgence()).isEqualTo(DEFAULT_ADRESSE_AGENCE);
        assertThat(testImf.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testImf.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createImfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = imfRepository.findAll().size();

        // Create the Imf with an existing ID
        imf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restImfMockMvc.perform(post("/api/imfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imf)))
            .andExpect(status().isBadRequest());

        // Validate the Imf in the database
        List<Imf> imfList = imfRepository.findAll();
        assertThat(imfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAgrementIsRequired() throws Exception {
        int databaseSizeBeforeTest = imfRepository.findAll().size();
        // set the field null
        imf.setAgrement(null);

        // Create the Imf, which fails.

        restImfMockMvc.perform(post("/api/imfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imf)))
            .andExpect(status().isBadRequest());

        List<Imf> imfList = imfRepository.findAll();
        assertThat(imfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = imfRepository.findAll().size();
        // set the field null
        imf.setNom(null);

        // Create the Imf, which fails.

        restImfMockMvc.perform(post("/api/imfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imf)))
            .andExpect(status().isBadRequest());

        List<Imf> imfList = imfRepository.findAll();
        assertThat(imfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = imfRepository.findAll().size();
        // set the field null
        imf.setTelephone(null);

        // Create the Imf, which fails.

        restImfMockMvc.perform(post("/api/imfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imf)))
            .andExpect(status().isBadRequest());

        List<Imf> imfList = imfRepository.findAll();
        assertThat(imfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = imfRepository.findAll().size();
        // set the field null
        imf.setEmail(null);

        // Create the Imf, which fails.

        restImfMockMvc.perform(post("/api/imfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imf)))
            .andExpect(status().isBadRequest());

        List<Imf> imfList = imfRepository.findAll();
        assertThat(imfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllImfs() throws Exception {
        // Initialize the database
        imfRepository.saveAndFlush(imf);

        // Get all the imfList
        restImfMockMvc.perform(get("/api/imfs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imf.getId().intValue())))
            .andExpect(jsonPath("$.[*].agrement").value(hasItem(DEFAULT_AGREMENT.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].siegeSocial").value(hasItem(DEFAULT_SIEGE_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].registreCommerce").value(hasItem(DEFAULT_REGISTRE_COMMERCE.toString())))
            .andExpect(jsonPath("$.[*].ninea").value(hasItem(DEFAULT_NINEA.toString())))
            .andExpect(jsonPath("$.[*].secteurActivite").value(hasItem(DEFAULT_SECTEUR_ACTIVITE.toString())))
            .andExpect(jsonPath("$.[*].formeJuridique").value(hasItem(DEFAULT_FORME_JURIDIQUE.toString())))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL.intValue())))
            .andExpect(jsonPath("$.[*].adresseAgence").value(hasItem(DEFAULT_ADRESSE_AGENCE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getImf() throws Exception {
        // Initialize the database
        imfRepository.saveAndFlush(imf);

        // Get the imf
        restImfMockMvc.perform(get("/api/imfs/{id}", imf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(imf.getId().intValue()))
            .andExpect(jsonPath("$.agrement").value(DEFAULT_AGREMENT.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.siegeSocial").value(DEFAULT_SIEGE_SOCIAL.toString()))
            .andExpect(jsonPath("$.registreCommerce").value(DEFAULT_REGISTRE_COMMERCE.toString()))
            .andExpect(jsonPath("$.ninea").value(DEFAULT_NINEA.toString()))
            .andExpect(jsonPath("$.secteurActivite").value(DEFAULT_SECTEUR_ACTIVITE.toString()))
            .andExpect(jsonPath("$.formeJuridique").value(DEFAULT_FORME_JURIDIQUE.toString()))
            .andExpect(jsonPath("$.capital").value(DEFAULT_CAPITAL.intValue()))
            .andExpect(jsonPath("$.adresseAgence").value(DEFAULT_ADRESSE_AGENCE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingImf() throws Exception {
        // Get the imf
        restImfMockMvc.perform(get("/api/imfs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateImf() throws Exception {
        // Initialize the database
        imfService.save(imf);

        int databaseSizeBeforeUpdate = imfRepository.findAll().size();

        // Update the imf
        Imf updatedImf = imfRepository.findById(imf.getId()).get();
        // Disconnect from session so that the updates on updatedImf are not directly saved in db
        em.detach(updatedImf);
        updatedImf
            .agrement(UPDATED_AGREMENT)
            .nom(UPDATED_NOM)
            .siegeSocial(UPDATED_SIEGE_SOCIAL)
            .registreCommerce(UPDATED_REGISTRE_COMMERCE)
            .ninea(UPDATED_NINEA)
            .secteurActivite(UPDATED_SECTEUR_ACTIVITE)
            .formeJuridique(UPDATED_FORME_JURIDIQUE)
            .capital(UPDATED_CAPITAL)
            .adresseAgence(UPDATED_ADRESSE_AGENCE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL);

        restImfMockMvc.perform(put("/api/imfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedImf)))
            .andExpect(status().isOk());

        // Validate the Imf in the database
        List<Imf> imfList = imfRepository.findAll();
        assertThat(imfList).hasSize(databaseSizeBeforeUpdate);
        Imf testImf = imfList.get(imfList.size() - 1);
        assertThat(testImf.getAgrement()).isEqualTo(UPDATED_AGREMENT);
        assertThat(testImf.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testImf.getSiegeSocial()).isEqualTo(UPDATED_SIEGE_SOCIAL);
        assertThat(testImf.getRegistreCommerce()).isEqualTo(UPDATED_REGISTRE_COMMERCE);
        assertThat(testImf.getNinea()).isEqualTo(UPDATED_NINEA);
        assertThat(testImf.getSecteurActivite()).isEqualTo(UPDATED_SECTEUR_ACTIVITE);
        assertThat(testImf.getFormeJuridique()).isEqualTo(UPDATED_FORME_JURIDIQUE);
        assertThat(testImf.getCapital()).isEqualTo(UPDATED_CAPITAL);
        assertThat(testImf.getAdresseAgence()).isEqualTo(UPDATED_ADRESSE_AGENCE);
        assertThat(testImf.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testImf.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingImf() throws Exception {
        int databaseSizeBeforeUpdate = imfRepository.findAll().size();

        // Create the Imf

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImfMockMvc.perform(put("/api/imfs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(imf)))
            .andExpect(status().isBadRequest());

        // Validate the Imf in the database
        List<Imf> imfList = imfRepository.findAll();
        assertThat(imfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteImf() throws Exception {
        // Initialize the database
        imfService.save(imf);

        int databaseSizeBeforeDelete = imfRepository.findAll().size();

        // Get the imf
        restImfMockMvc.perform(delete("/api/imfs/{id}", imf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Imf> imfList = imfRepository.findAll();
        assertThat(imfList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Imf.class);
        Imf imf1 = new Imf();
        imf1.setId(1L);
        Imf imf2 = new Imf();
        imf2.setId(imf1.getId());
        assertThat(imf1).isEqualTo(imf2);
        imf2.setId(2L);
        assertThat(imf1).isNotEqualTo(imf2);
        imf1.setId(null);
        assertThat(imf1).isNotEqualTo(imf2);
    }
}
