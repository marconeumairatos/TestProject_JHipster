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
 * REST controller for managing {@link com.marconation.jhp.domain.UserAntwort}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserantwortResource {

    private final Logger log = LoggerFactory.getLogger(UserantwortResource.class);

    private static final String ENTITY_NAME = "userAntwort";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserantwortRepository userAntwortRepository;

    private final UserantwortSearchRepository userAntwortSearchRepository;

    public UserantwortResource(UserantwortRepository userAntwortRepository, UserantwortSearchRepository userAntwortSearchRepository) {
        this.userAntwortRepository = userAntwortRepository;
        this.userAntwortSearchRepository = userAntwortSearchRepository;
    }

    /**
     * {@code POST  /user-antworts} : Create a new userAntwort.
     *
     * @param userAntwort the userAntwort to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userAntwort, or with status {@code 400 (Bad Request)} if the userAntwort has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-antworts")
    public ResponseEntity<Userantwort> createUserAntwort(@Valid @RequestBody Userantwort userAntwort) throws URISyntaxException {
        log.debug("REST request to save UserAntwort : {}", userAntwort);
        if (userAntwort.getId() != null) {
            throw new BadRequestAlertException("A new userAntwort cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Userantwort result = userAntwortRepository.save(userAntwort);
        userAntwortSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/user-antworts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-antworts} : Updates an existing userAntwort.
     *
     * @param userAntwort the userAntwort to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userAntwort,
     * or with status {@code 400 (Bad Request)} if the userAntwort is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userAntwort couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-antworts")
    public ResponseEntity<Userantwort> updateUserAntwort(@Valid @RequestBody Userantwort userAntwort) throws URISyntaxException {
        log.debug("REST request to update UserAntwort : {}", userAntwort);
        if (userAntwort.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Userantwort result = userAntwortRepository.save(userAntwort);
        userAntwortSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userAntwort.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-antworts} : get all the userAntworts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userAntworts in body.
     */
    @GetMapping("/user-antworts")
    public List<Userantwort> getAllUserAntworts() {
        log.debug("REST request to get all UserAntworts");
        return userAntwortRepository.findAll();
    }

    /**
     * {@code GET  /user-antworts/:id} : get the "id" userAntwort.
     *
     * @param id the id of the userAntwort to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userAntwort, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-antworts/{id}")
    public ResponseEntity<Userantwort> getUserAntwort(@PathVariable Long id) {
        log.debug("REST request to get UserAntwort : {}", id);
        Optional<Userantwort> userAntwort = userAntwortRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userAntwort);
    }

    /**
     * {@code DELETE  /user-antworts/:id} : delete the "id" userAntwort.
     *
     * @param id the id of the userAntwort to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-antworts/{id}")
    public ResponseEntity<Void> deleteUserAntwort(@PathVariable Long id) {
        log.debug("REST request to delete UserAntwort : {}", id);
        userAntwortRepository.deleteById(id);
        userAntwortSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/user-antworts?query=:query} : search for the userAntwort corresponding
     * to the query.
     *
     * @param query the query of the userAntwort search.
     * @return the result of the search.
     */
    @GetMapping("/_search/user-antworts")
    public List<Userantwort> searchUserAntworts(@RequestParam String query) {
        log.debug("REST request to search UserAntworts for query {}", query);
        return StreamSupport
            .stream(userAntwortSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
