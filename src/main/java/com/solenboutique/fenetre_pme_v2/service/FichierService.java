package com.solenboutique.fenetre_pme_v2.service;

import com.solenboutique.fenetre_pme_v2.domain.Fichier;
import com.solenboutique.fenetre_pme_v2.repository.FichierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Fichier.
 */
@Service
@Transactional
public class FichierService {

    private final Logger log = LoggerFactory.getLogger(FichierService.class);

    private final FichierRepository fichierRepository;

    public FichierService(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;
    }

    /**
     * Save a fichier.
     *
     * @param fichier the entity to save
     * @return the persisted entity
     */
    public Fichier save(Fichier fichier) {
        log.debug("Request to save Fichier : {}", fichier);
        return fichierRepository.save(fichier);
    }

    /**
     * Get all the fichiers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Fichier> findAll(Pageable pageable) {
        log.debug("Request to get all Fichiers");
        return fichierRepository.findAll(pageable);
    }


    /**
     * Get one fichier by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Fichier> findOne(Long id) {
        log.debug("Request to get Fichier : {}", id);
        return fichierRepository.findById(id);
    }

    /**
     * Delete the fichier by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Fichier : {}", id);
        fichierRepository.deleteById(id);
    }
}
