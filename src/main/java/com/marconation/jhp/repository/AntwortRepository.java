package com.marconation.jhp.repository;

import com.marconation.jhp.domain.Antwort;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Spring Data  repository for the Antwort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntwortRepository extends JpaRepository<Antwort, Long>,JpaSpecificationExecutor<Antwort> {
}
