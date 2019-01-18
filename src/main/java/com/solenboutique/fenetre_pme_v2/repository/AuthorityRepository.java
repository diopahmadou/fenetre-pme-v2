package com.solenboutique.fenetre_pme_v2.repository;

import com.solenboutique.fenetre_pme_v2.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
