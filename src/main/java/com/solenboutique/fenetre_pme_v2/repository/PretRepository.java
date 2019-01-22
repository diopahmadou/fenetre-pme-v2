package com.solenboutique.fenetre_pme_v2.repository;

import com.solenboutique.fenetre_pme_v2.domain.Pret;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pret entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PretRepository extends JpaRepository<Pret, Long> {

}
