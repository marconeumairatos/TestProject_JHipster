package com.marconation.jhp.repository;

import com.marconation.jhp.domain.Umfrage;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Umfrage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UmfrageRepository extends JpaRepository<Umfrage, Long> {
}
