package com.solenboutique.fenetre_pme_v2.repository;

import com.solenboutique.fenetre_pme_v2.domain.Pme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PmeRepository extends JpaRepository<Pme, Long> {

}
