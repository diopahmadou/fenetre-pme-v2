package com.solenboutique.fenetre_pme_v2.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.solenboutique.fenetre_pme_v2.domain.ContactPme;
import com.solenboutique.fenetre_pme_v2.service.ContactPmeService;
import com.solenboutique.fenetre_pme_v2.web.rest.errors.BadRequestAlertException;
import com.solenboutique.fenetre_pme_v2.web.rest.util.HeaderUtil;
import com.solenboutique.fenetre_pme_v2.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ContactPme.
 */
@RestController
@RequestMapping("/api")
public class ContactPmeResource {

    private final Logger log = LoggerFactory.getLogger(ContactPmeResource.class);

    private static final String ENTITY_NAME = "contactPme";

    private final ContactPmeService contactPmeService;

    public ContactPmeResource(ContactPmeService contactPmeService) {
        this.contactPmeService = contactPmeService;
    }

    /**
     * POST  /contact-pmes : Create a new contactPme.
     *
     * @param contactPme the contactPme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactPme, or with status 400 (Bad Request) if the contactPme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact-pmes")
    @Timed
    public ResponseEntity<ContactPme> createContactPme(@Valid @RequestBody ContactPme contactPme) throws URISyntaxException {
        log.debug("REST request to save ContactPme : {}", contactPme);
        if (contactPme.getId() != null) {
            throw new BadRequestAlertException("A new contactPme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactPme result = contactPmeService.save(contactPme);
        return ResponseEntity.created(new URI("/api/contact-pmes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contact-pmes : Updates an existing contactPme.
     *
     * @param contactPme the contactPme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactPme,
     * or with status 400 (Bad Request) if the contactPme is not valid,
     * or with status 500 (Internal Server Error) if the contactPme couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contact-pmes")
    @Timed
    public ResponseEntity<ContactPme> updateContactPme(@Valid @RequestBody ContactPme contactPme) throws URISyntaxException {
        log.debug("REST request to update ContactPme : {}", contactPme);
        if (contactPme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContactPme result = contactPmeService.save(contactPme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactPme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-pmes : get all the contactPmes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contactPmes in body
     */
    @GetMapping("/contact-pmes")
    @Timed
    public ResponseEntity<List<ContactPme>> getAllContactPmes(Pageable pageable) {
        log.debug("REST request to get a page of ContactPmes");
        Page<ContactPme> page = contactPmeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contact-pmes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /contact-pmes/:id : get the "id" contactPme.
     *
     * @param id the id of the contactPme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactPme, or with status 404 (Not Found)
     */
    @GetMapping("/contact-pmes/{id}")
    @Timed
    public ResponseEntity<ContactPme> getContactPme(@PathVariable Long id) {
        log.debug("REST request to get ContactPme : {}", id);
        Optional<ContactPme> contactPme = contactPmeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contactPme);
    }

    /**
     * DELETE  /contact-pmes/:id : delete the "id" contactPme.
     *
     * @param id the id of the contactPme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-pmes/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactPme(@PathVariable Long id) {
        log.debug("REST request to delete ContactPme : {}", id);
        contactPmeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
