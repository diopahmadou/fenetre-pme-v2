package com.solenboutique.fenetre_pme_v2.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.solenboutique.fenetre_pme_v2.domain.Imf;
import com.solenboutique.fenetre_pme_v2.service.ImfService;
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
 * REST controller for managing Imf.
 */
@RestController
@RequestMapping("/api")
public class ImfResource {

    private final Logger log = LoggerFactory.getLogger(ImfResource.class);

    private static final String ENTITY_NAME = "imf";

    private final ImfService imfService;

    public ImfResource(ImfService imfService) {
        this.imfService = imfService;
    }

    /**
     * POST  /imfs : Create a new imf.
     *
     * @param imf the imf to create
     * @return the ResponseEntity with status 201 (Created) and with body the new imf, or with status 400 (Bad Request) if the imf has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/imfs")
    @Timed
    public ResponseEntity<Imf> createImf(@Valid @RequestBody Imf imf) throws URISyntaxException {
        log.debug("REST request to save Imf : {}", imf);
        if (imf.getId() != null) {
            throw new BadRequestAlertException("A new imf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Imf result = imfService.save(imf);
        return ResponseEntity.created(new URI("/api/imfs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /imfs : Updates an existing imf.
     *
     * @param imf the imf to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated imf,
     * or with status 400 (Bad Request) if the imf is not valid,
     * or with status 500 (Internal Server Error) if the imf couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/imfs")
    @Timed
    public ResponseEntity<Imf> updateImf(@Valid @RequestBody Imf imf) throws URISyntaxException {
        log.debug("REST request to update Imf : {}", imf);
        if (imf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Imf result = imfService.save(imf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, imf.getId().toString()))
            .body(result);
    }

    /**
     * GET  /imfs : get all the imfs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of imfs in body
     */
    @GetMapping("/imfs")
    @Timed
    public ResponseEntity<List<Imf>> getAllImfs(Pageable pageable) {
        log.debug("REST request to get a page of Imfs");
        Page<Imf> page = imfService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/imfs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /imfs/:id : get the "id" imf.
     *
     * @param id the id of the imf to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the imf, or with status 404 (Not Found)
     */
    @GetMapping("/imfs/{id}")
    @Timed
    public ResponseEntity<Imf> getImf(@PathVariable Long id) {
        log.debug("REST request to get Imf : {}", id);
        Optional<Imf> imf = imfService.findOne(id);
        return ResponseUtil.wrapOrNotFound(imf);
    }

    /**
     * DELETE  /imfs/:id : delete the "id" imf.
     *
     * @param id the id of the imf to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/imfs/{id}")
    @Timed
    public ResponseEntity<Void> deleteImf(@PathVariable Long id) {
        log.debug("REST request to delete Imf : {}", id);
        imfService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
