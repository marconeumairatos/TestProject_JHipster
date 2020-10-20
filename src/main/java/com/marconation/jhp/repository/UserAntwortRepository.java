package com.marconation.jhp.repository;

import com.marconation.jhp.domain.Userantwort;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserAntwort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserantwortRepository extends JpaRepository<Userantwort, Long> {
}
