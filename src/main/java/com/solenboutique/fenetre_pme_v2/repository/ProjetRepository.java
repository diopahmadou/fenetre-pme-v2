package com.solenboutique.fenetre_pme_v2.repository;

import com.solenboutique.fenetre_pme_v2.domain.Projet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Projet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

}
