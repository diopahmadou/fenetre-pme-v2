package com.solenboutique.fenetre_pme_v2.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.solenboutique.fenetre_pme_v2.domain.Fichier;
import com.solenboutique.fenetre_pme_v2.service.FichierService;
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
 * REST controller for managing Fichier.
 */
@RestController
@RequestMapping("/api")
public class FichierResource {

    private final Logger log = LoggerFactory.getLogger(FichierResource.class);

    private static final String ENTITY_NAME = "fichier";

    private final FichierService fichierService;

    public FichierResource(FichierService fichierService) {
        this.fichierService = fichierService;
    }

    /**
     * POST  /fichiers : Create a new fichier.
     *
     * @param fichier the fichier to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fichier, or with status 400 (Bad Request) if the fichier has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fichiers")
    @Timed
    public ResponseEntity<Fichier> createFichier(@Valid @RequestBody Fichier fichier) throws URISyntaxException {
        log.debug("REST request to save Fichier : {}", fichier);
        if (fichier.getId() != null) {
            throw new BadRequestAlertException("A new fichier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Fichier result = fichierService.save(fichier);
        return ResponseEntity.created(new URI("/api/fichiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fichiers : Updates an existing fichier.
     *
     * @param fichier the fichier to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fichier,
     * or with status 400 (Bad Request) if the fichier is not valid,
     * or with status 500 (Internal Server Error) if the fichier couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fichiers")
    @Timed
    public ResponseEntity<Fichier> updateFichier(@Valid @RequestBody Fichier fichier) throws URISyntaxException {
        log.debug("REST request to update Fichier : {}", fichier);
        if (fichier.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Fichier result = fichierService.save(fichier);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fichier.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fichiers : get all the fichiers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fichiers in body
     */
    @GetMapping("/fichiers")
    @Timed
    public ResponseEntity<List<Fichier>> getAllFichiers(Pageable pageable) {
        log.debug("REST request to get a page of Fichiers");
        Page<Fichier> page = fichierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fichiers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /fichiers/:id : get the "id" fichier.
     *
     * @param id the id of the fichier to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fichier, or with status 404 (Not Found)
     */
    @GetMapping("/fichiers/{id}")
    @Timed
    public ResponseEntity<Fichier> getFichier(@PathVariable Long id) {
        log.debug("REST request to get Fichier : {}", id);
        Optional<Fichier> fichier = fichierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fichier);
    }

    /**
     * DELETE  /fichiers/:id : delete the "id" fichier.
     *
     * @param id the id of the fichier to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fichiers/{id}")
    @Timed
    public ResponseEntity<Void> deleteFichier(@PathVariable Long id) {
        log.debug("REST request to delete Fichier : {}", id);
        fichierService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
