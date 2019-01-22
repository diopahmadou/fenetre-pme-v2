package com.solenboutique.fenetre_pme_v2.web.rest;

import com.solenboutique.fenetre_pme_v2.FenetrePmEv2App;

import com.solenboutique.fenetre_pme_v2.domain.ContactPme;
import com.solenboutique.fenetre_pme_v2.repository.ContactPmeRepository;
import com.solenboutique.fenetre_pme_v2.service.ContactPmeService;
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
 * Test class for the ContactPmeResource REST controller.
 *
 * @see ContactPmeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FenetrePmEv2App.class)
public class ContactPmeResourceIntTest {

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "%s@G,.M";
    private static final String UPDATED_EMAIL = "~@Bm.f(";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FONCTION = "AAAAAAAAAA";
    private static final String UPDATED_FONCTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    @Autowired
    private ContactPmeRepository contactPmeRepository;

    @Autowired
    private ContactPmeService contactPmeService;

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

    private MockMvc restContactPmeMockMvc;

    private ContactPme contactPme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ContactPmeResource contactPmeResource = new ContactPmeResource(contactPmeService);
        this.restContactPmeMockMvc = MockMvcBuilders.standaloneSetup(contactPmeResource)
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
    public static ContactPme createEntity(EntityManager em) {
        ContactPme contactPme = new ContactPme()
            .prenom(DEFAULT_PRENOM)
            .nom(DEFAULT_NOM)
            .email(DEFAULT_EMAIL)
            .telephone(DEFAULT_TELEPHONE)
            .fonction(DEFAULT_FONCTION)
            .adresse(DEFAULT_ADRESSE);
        return contactPme;
    }

    @Before
    public void initTest() {
        contactPme = createEntity(em);
    }

    @Test
    @Transactional
    public void createContactPme() throws Exception {
        int databaseSizeBeforeCreate = contactPmeRepository.findAll().size();

        // Create the ContactPme
        restContactPmeMockMvc.perform(post("/api/contact-pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPme)))
            .andExpect(status().isCreated());

        // Validate the ContactPme in the database
        List<ContactPme> contactPmeList = contactPmeRepository.findAll();
        assertThat(contactPmeList).hasSize(databaseSizeBeforeCreate + 1);
        ContactPme testContactPme = contactPmeList.get(contactPmeList.size() - 1);
        assertThat(testContactPme.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testContactPme.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testContactPme.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContactPme.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testContactPme.getFonction()).isEqualTo(DEFAULT_FONCTION);
        assertThat(testContactPme.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
    }

    @Test
    @Transactional
    public void createContactPmeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contactPmeRepository.findAll().size();

        // Create the ContactPme with an existing ID
        contactPme.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContactPmeMockMvc.perform(post("/api/contact-pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPme)))
            .andExpect(status().isBadRequest());

        // Validate the ContactPme in the database
        List<ContactPme> contactPmeList = contactPmeRepository.findAll();
        assertThat(contactPmeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactPmeRepository.findAll().size();
        // set the field null
        contactPme.setPrenom(null);

        // Create the ContactPme, which fails.

        restContactPmeMockMvc.perform(post("/api/contact-pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPme)))
            .andExpect(status().isBadRequest());

        List<ContactPme> contactPmeList = contactPmeRepository.findAll();
        assertThat(contactPmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactPmeRepository.findAll().size();
        // set the field null
        contactPme.setNom(null);

        // Create the ContactPme, which fails.

        restContactPmeMockMvc.perform(post("/api/contact-pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPme)))
            .andExpect(status().isBadRequest());

        List<ContactPme> contactPmeList = contactPmeRepository.findAll();
        assertThat(contactPmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactPmeRepository.findAll().size();
        // set the field null
        contactPme.setEmail(null);

        // Create the ContactPme, which fails.

        restContactPmeMockMvc.perform(post("/api/contact-pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPme)))
            .andExpect(status().isBadRequest());

        List<ContactPme> contactPmeList = contactPmeRepository.findAll();
        assertThat(contactPmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactPmeRepository.findAll().size();
        // set the field null
        contactPme.setTelephone(null);

        // Create the ContactPme, which fails.

        restContactPmeMockMvc.perform(post("/api/contact-pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPme)))
            .andExpect(status().isBadRequest());

        List<ContactPme> contactPmeList = contactPmeRepository.findAll();
        assertThat(contactPmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFonctionIsRequired() throws Exception {
        int databaseSizeBeforeTest = contactPmeRepository.findAll().size();
        // set the field null
        contactPme.setFonction(null);

        // Create the ContactPme, which fails.

        restContactPmeMockMvc.perform(post("/api/contact-pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPme)))
            .andExpect(status().isBadRequest());

        List<ContactPme> contactPmeList = contactPmeRepository.findAll();
        assertThat(contactPmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllContactPmes() throws Exception {
        // Initialize the database
        contactPmeRepository.saveAndFlush(contactPme);

        // Get all the contactPmeList
        restContactPmeMockMvc.perform(get("/api/contact-pmes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contactPme.getId().intValue())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].fonction").value(hasItem(DEFAULT_FONCTION.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())));
    }
    
    @Test
    @Transactional
    public void getContactPme() throws Exception {
        // Initialize the database
        contactPmeRepository.saveAndFlush(contactPme);

        // Get the contactPme
        restContactPmeMockMvc.perform(get("/api/contact-pmes/{id}", contactPme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contactPme.getId().intValue()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.fonction").value(DEFAULT_FONCTION.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContactPme() throws Exception {
        // Get the contactPme
        restContactPmeMockMvc.perform(get("/api/contact-pmes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContactPme() throws Exception {
        // Initialize the database
        contactPmeService.save(contactPme);

        int databaseSizeBeforeUpdate = contactPmeRepository.findAll().size();

        // Update the contactPme
        ContactPme updatedContactPme = contactPmeRepository.findById(contactPme.getId()).get();
        // Disconnect from session so that the updates on updatedContactPme are not directly saved in db
        em.detach(updatedContactPme);
        updatedContactPme
            .prenom(UPDATED_PRENOM)
            .nom(UPDATED_NOM)
            .email(UPDATED_EMAIL)
            .telephone(UPDATED_TELEPHONE)
            .fonction(UPDATED_FONCTION)
            .adresse(UPDATED_ADRESSE);

        restContactPmeMockMvc.perform(put("/api/contact-pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContactPme)))
            .andExpect(status().isOk());

        // Validate the ContactPme in the database
        List<ContactPme> contactPmeList = contactPmeRepository.findAll();
        assertThat(contactPmeList).hasSize(databaseSizeBeforeUpdate);
        ContactPme testContactPme = contactPmeList.get(contactPmeList.size() - 1);
        assertThat(testContactPme.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testContactPme.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testContactPme.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContactPme.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testContactPme.getFonction()).isEqualTo(UPDATED_FONCTION);
        assertThat(testContactPme.getAdresse()).isEqualTo(UPDATED_ADRESSE);
    }

    @Test
    @Transactional
    public void updateNonExistingContactPme() throws Exception {
        int databaseSizeBeforeUpdate = contactPmeRepository.findAll().size();

        // Create the ContactPme

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContactPmeMockMvc.perform(put("/api/contact-pmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contactPme)))
            .andExpect(status().isBadRequest());

        // Validate the ContactPme in the database
        List<ContactPme> contactPmeList = contactPmeRepository.findAll();
        assertThat(contactPmeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContactPme() throws Exception {
        // Initialize the database
        contactPmeService.save(contactPme);

        int databaseSizeBeforeDelete = contactPmeRepository.findAll().size();

        // Get the contactPme
        restContactPmeMockMvc.perform(delete("/api/contact-pmes/{id}", contactPme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContactPme> contactPmeList = contactPmeRepository.findAll();
        assertThat(contactPmeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContactPme.class);
        ContactPme contactPme1 = new ContactPme();
        contactPme1.setId(1L);
        ContactPme contactPme2 = new ContactPme();
        contactPme2.setId(contactPme1.getId());
        assertThat(contactPme1).isEqualTo(contactPme2);
        contactPme2.setId(2L);
        assertThat(contactPme1).isNotEqualTo(contactPme2);
        contactPme1.setId(null);
        assertThat(contactPme1).isNotEqualTo(contactPme2);
    }
}
