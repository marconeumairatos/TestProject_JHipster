package com.marconation.jhp.repository;

import com.marconation.jhp.domain.UserAntwort;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserAntwort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAntwortRepository extends JpaRepository<UserAntwort, Long> {
}
