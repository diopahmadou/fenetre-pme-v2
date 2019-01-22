package com.solenboutique.fenetre_pme_v2.service;

import com.solenboutique.fenetre_pme_v2.domain.ContactPme;
import com.solenboutique.fenetre_pme_v2.repository.ContactPmeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing ContactPme.
 */
@Service
@Transactional
public class ContactPmeService {

    private final Logger log = LoggerFactory.getLogger(ContactPmeService.class);

    private final ContactPmeRepository contactPmeRepository;

    public ContactPmeService(ContactPmeRepository contactPmeRepository) {
        this.contactPmeRepository = contactPmeRepository;
    }

    /**
     * Save a contactPme.
     *
     * @param contactPme the entity to save
     * @return the persisted entity
     */
    public ContactPme save(ContactPme contactPme) {
        log.debug("Request to save ContactPme : {}", contactPme);
        return contactPmeRepository.save(contactPme);
    }

    /**
     * Get all the contactPmes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ContactPme> findAll(Pageable pageable) {
        log.debug("Request to get all ContactPmes");
        return contactPmeRepository.findAll(pageable);
    }


    /**
     * Get one contactPme by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ContactPme> findOne(Long id) {
        log.debug("Request to get ContactPme : {}", id);
        return contactPmeRepository.findById(id);
    }

    /**
     * Delete the contactPme by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ContactPme : {}", id);
        contactPmeRepository.deleteById(id);
    }
}
