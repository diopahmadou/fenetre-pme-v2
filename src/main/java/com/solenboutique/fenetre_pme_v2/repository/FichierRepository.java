package com.solenboutique.fenetre_pme_v2.repository;

import com.solenboutique.fenetre_pme_v2.domain.Fichier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Fichier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FichierRepository extends JpaRepository<Fichier, Long> {

}
