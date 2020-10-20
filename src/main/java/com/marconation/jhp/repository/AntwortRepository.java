package com.marconation.jhp.repository;

import com.marconation.jhp.domain.Antwort;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Antwort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntwortRepository extends JpaRepository<Antwort, Long> {
}
