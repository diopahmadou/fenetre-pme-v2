package com.solenboutique.fenetre_pme_v2.repository;

import com.solenboutique.fenetre_pme_v2.domain.ContactPme;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ContactPme entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactPmeRepository extends JpaRepository<ContactPme, Long> {

}
