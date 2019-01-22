package com.solenboutique.fenetre_pme_v2.repository;

import com.solenboutique.fenetre_pme_v2.domain.Imf;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Imf entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ImfRepository extends JpaRepository<Imf, Long> {

}
