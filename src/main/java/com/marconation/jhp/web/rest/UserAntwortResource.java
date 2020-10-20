package com.marconation.jhp.web.rest;

import com.marconation.jhp.domain.Userantwort;
import com.marconation.jhp.repository.UserantwortRepository;
import com.marconation.jhp.repository.search.UserantwortSearchRepository;
import com.marconation.jhp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link com.marconation.jhp.domain.Userantwort}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserantwortResource {

    private final Logger log = LoggerFactory.getLogger(UserantwortResource.class);

    private static final String ENTITY_NAME = "userantwort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserantwortRepository userantwortRepository;

    private final UserantwortSearchRepository userantwortSearchRepository;

    public UserantwortResource(UserantwortRepository userantwortRepository, UserantwortSearchRepository userantwortSearchRepository) {
        this.userantwortRepository = userantwortRepository;
        this.userantwortSearchRepository = userantwortSearchRepository;
    }

    /**
     * {@code POST  /userantworts} : Create a new userantwort.
     *
     * @param userantwort the userantwort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userantwort, or with status {@code 400 (Bad Request)} if the userantwort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/userantworts")
    public ResponseEntity<Userantwort> createUserantwort(@Valid @RequestBody Userantwort userantwort) throws URISyntaxException {
        log.debug("REST request to save Userantwort : {}", userantwort);
        if (userantwort.getId() != null) {
            throw new BadRequestAlertException("A new userantwort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Userantwort result = userantwortRepository.save(userantwort);
        userantwortSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/userantworts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /userantworts} : Updates an existing userantwort.
     *
     * @param userantwort the userantwort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userantwort,
     * or with status {@code 400 (Bad Request)} if the userantwort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userantwort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/userantworts")
    public ResponseEntity<Userantwort> updateUserantwort(@Valid @RequestBody Userantwort userantwort) throws URISyntaxException {
        log.debug("REST request to update Userantwort : {}", userantwort);
        if (userantwort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Userantwort result = userantwortRepository.save(userantwort);
        userantwortSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userantwort.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /userantworts} : get all the userantworts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userantworts in body.
     */
    @GetMapping("/userantworts")
    public List<Userantwort> getAllUserantworts() {
        log.debug("REST request to get all Userantworts");
        return userantwortRepository.findAll();
    }

    /**
     * {@code GET  /userantworts/:id} : get the "id" userantwort.
     *
     * @param id the id of the userantwort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userantwort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/userantworts/{id}")
    public ResponseEntity<Userantwort> getUserantwort(@PathVariable Long id) {
        log.debug("REST request to get Userantwort : {}", id);
        Optional<Userantwort> userantwort = userantwortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userantwort);
    }

    /**
     * {@code DELETE  /userantworts/:id} : delete the "id" userantwort.
     *
     * @param id the id of the userantwort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/userantworts/{id}")
    public ResponseEntity<Void> deleteUserantwort(@PathVariable Long id) {
        log.debug("REST request to delete Userantwort : {}", id);
        userantwortRepository.deleteById(id);
        userantwortSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/userantworts?query=:query} : search for the userantwort corresponding
     * to the query.
     *
     * @param query the query of the userantwort search.
     * @return the result of the search.
     */
    @GetMapping("/_search/userantworts")
    public List<Userantwort> searchUserantworts(@RequestParam String query) {
        log.debug("REST request to search Userantworts for query {}", query);
        return StreamSupport
            .stream(userantwortSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
