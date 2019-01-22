package com.solenboutique.fenetre_pme_v2.service;

import com.solenboutique.fenetre_pme_v2.domain.Pme;
import com.solenboutique.fenetre_pme_v2.repository.PmeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Pme.
 */
@Service
@Transactional
public class PmeService {

    private final Logger log = LoggerFactory.getLogger(PmeService.class);

    private final PmeRepository pmeRepository;

    public PmeService(PmeRepository pmeRepository) {
        this.pmeRepository = pmeRepository;
    }

    /**
     * Save a pme.
     *
     * @param pme the entity to save
     * @return the persisted entity
     */
    public Pme save(Pme pme) {
        log.debug("Request to save Pme : {}", pme);
        return pmeRepository.save(pme);
    }

    /**
     * Get all the pmes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Pme> findAll(Pageable pageable) {
        log.debug("Request to get all Pmes");
        return pmeRepository.findAll(pageable);
    }


    /**
     * Get one pme by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Pme> findOne(Long id) {
        log.debug("Request to get Pme : {}", id);
        return pmeRepository.findById(id);
    }

    /**
     * Delete the pme by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Pme : {}", id);
        pmeRepository.deleteById(id);
    }
}
