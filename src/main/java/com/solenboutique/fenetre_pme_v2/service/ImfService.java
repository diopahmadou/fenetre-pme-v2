package com.solenboutique.fenetre_pme_v2.service;

import com.solenboutique.fenetre_pme_v2.domain.Imf;
import com.solenboutique.fenetre_pme_v2.repository.ImfRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Imf.
 */
@Service
@Transactional
public class ImfService {

    private final Logger log = LoggerFactory.getLogger(ImfService.class);

    private final ImfRepository imfRepository;

    public ImfService(ImfRepository imfRepository) {
        this.imfRepository = imfRepository;
    }

    /**
     * Save a imf.
     *
     * @param imf the entity to save
     * @return the persisted entity
     */
    public Imf save(Imf imf) {
        log.debug("Request to save Imf : {}", imf);
        return imfRepository.save(imf);
    }

    /**
     * Get all the imfs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Imf> findAll(Pageable pageable) {
        log.debug("Request to get all Imfs");
        return imfRepository.findAll(pageable);
    }


    /**
     * Get one imf by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Imf> findOne(Long id) {
        log.debug("Request to get Imf : {}", id);
        return imfRepository.findById(id);
    }

    /**
     * Delete the imf by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Imf : {}", id);
        imfRepository.deleteById(id);
    }
}
