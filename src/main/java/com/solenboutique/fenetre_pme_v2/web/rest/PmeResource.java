package com.solenboutique.fenetre_pme_v2.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.solenboutique.fenetre_pme_v2.domain.Pme;
import com.solenboutique.fenetre_pme_v2.service.PmeService;
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
 * REST controller for managing Pme.
 */
@RestController
@RequestMapping("/api")
public class PmeResource {

    private final Logger log = LoggerFactory.getLogger(PmeResource.class);

    private static final String ENTITY_NAME = "pme";

    private final PmeService pmeService;

    public PmeResource(PmeService pmeService) {
        this.pmeService = pmeService;
    }

    /**
     * POST  /pmes : Create a new pme.
     *
     * @param pme the pme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pme, or with status 400 (Bad Request) if the pme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pmes")
    @Timed
    public ResponseEntity<Pme> createPme(@Valid @RequestBody Pme pme) throws URISyntaxException {
        log.debug("REST request to save Pme : {}", pme);
        if (pme.getId() != null) {
            throw new BadRequestAlertException("A new pme cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Pme result = pmeService.save(pme);
        return ResponseEntity.created(new URI("/api/pmes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pmes : Updates an existing pme.
     *
     * @param pme the pme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pme,
     * or with status 400 (Bad Request) if the pme is not valid,
     * or with status 500 (Internal Server Error) if the pme couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pmes")
    @Timed
    public ResponseEntity<Pme> updatePme(@Valid @RequestBody Pme pme) throws URISyntaxException {
        log.debug("REST request to update Pme : {}", pme);
        if (pme.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Pme result = pmeService.save(pme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pmes : get all the pmes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pmes in body
     */
    @GetMapping("/pmes")
    @Timed
    public ResponseEntity<List<Pme>> getAllPmes(Pageable pageable) {
        log.debug("REST request to get a page of Pmes");
        Page<Pme> page = pmeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pmes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /pmes/:id : get the "id" pme.
     *
     * @param id the id of the pme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pme, or with status 404 (Not Found)
     */
    @GetMapping("/pmes/{id}")
    @Timed
    public ResponseEntity<Pme> getPme(@PathVariable Long id) {
        log.debug("REST request to get Pme : {}", id);
        Optional<Pme> pme = pmeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(pme);
    }

    /**
     * DELETE  /pmes/:id : delete the "id" pme.
     *
     * @param id the id of the pme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pmes/{id}")
    @Timed
    public ResponseEntity<Void> deletePme(@PathVariable Long id) {
        log.debug("REST request to delete Pme : {}", id);
        pmeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
