package com.marconation.jhp.repository;

import com.marconation.jhp.domain.Antwort;
import java.util.List;
import java.util.Optional;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Antwort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AntwortRepository extends JpaRepository<Antwort, Long>,JpaSpecificationExecutor<Antwort> {

    List<Antwort> findByUmfrageId(long Umfrage_Id);}

