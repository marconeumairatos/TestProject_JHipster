package com.marconation.jhp.repository;

import com.marconation.jhp.domain.AntwortbyUser;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AntwortbyUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntwortbyUserRepository extends JpaRepository<AntwortbyUser, Long> {
}
