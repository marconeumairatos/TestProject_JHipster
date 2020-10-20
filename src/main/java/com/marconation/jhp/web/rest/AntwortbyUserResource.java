package com.marconation.jhp.web.rest;

import com.marconation.jhp.domain.AntwortbyUser;
import com.marconation.jhp.repository.AntwortbyUserRepository;
import com.marconation.jhp.repository.search.AntwortbyUserSearchRepository;
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
 * REST controller for managing {@link com.marconation.jhp.domain.AntwortbyUser}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class AntwortbyUserResource {

    private final Logger log = LoggerFactory.getLogger(AntwortbyUserResource.class);

    private static final String ENTITY_NAME = "antwortbyUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AntwortbyUserRepository antwortbyUserRepository;

    private final AntwortbyUserSearchRepository antwortbyUserSearchRepository;

    public AntwortbyUserResource(AntwortbyUserRepository antwortbyUserRepository, AntwortbyUserSearchRepository antwortbyUserSearchRepository) {
        this.antwortbyUserRepository = antwortbyUserRepository;
        this.antwortbyUserSearchRepository = antwortbyUserSearchRepository;
    }

    /**
     * {@code POST  /antwortby-users} : Create a new antwortbyUser.
     *
     * @param antwortbyUser the antwortbyUser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new antwortbyUser, or with status {@code 400 (Bad Request)} if the antwortbyUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/antwortby-users")
    public ResponseEntity<AntwortbyUser> createAntwortbyUser(@Valid @RequestBody AntwortbyUser antwortbyUser) throws URISyntaxException {
        log.debug("REST request to save AntwortbyUser : {}", antwortbyUser);
        if (antwortbyUser.getId() != null) {
            throw new BadRequestAlertException("A new antwortbyUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AntwortbyUser result = antwortbyUserRepository.save(antwortbyUser);
        antwortbyUserSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/antwortby-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /antwortby-users} : Updates an existing antwortbyUser.
     *
     * @param antwortbyUser the antwortbyUser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated antwortbyUser,
     * or with status {@code 400 (Bad Request)} if the antwortbyUser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the antwortbyUser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/antwortby-users")
    public ResponseEntity<AntwortbyUser> updateAntwortbyUser(@Valid @RequestBody AntwortbyUser antwortbyUser) throws URISyntaxException {
        log.debug("REST request to update AntwortbyUser : {}", antwortbyUser);
        if (antwortbyUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AntwortbyUser result = antwortbyUserRepository.save(antwortbyUser);
        antwortbyUserSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, antwortbyUser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /antwortby-users} : get all the antwortbyUsers.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of antwortbyUsers in body.
     */
    @GetMapping("/antwortby-users")
    public List<AntwortbyUser> getAllAntwortbyUsers() {
        log.debug("REST request to get all AntwortbyUsers");
        return antwortbyUserRepository.findAll();
    }

    /**
     * {@code GET  /antwortby-users/:id} : get the "id" antwortbyUser.
     *
     * @param id the id of the antwortbyUser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the antwortbyUser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/antwortby-users/{id}")
    public ResponseEntity<AntwortbyUser> getAntwortbyUser(@PathVariable Long id) {
        log.debug("REST request to get AntwortbyUser : {}", id);
        Optional<AntwortbyUser> antwortbyUser = antwortbyUserRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(antwortbyUser);
    }

    /**
     * {@code DELETE  /antwortby-users/:id} : delete the "id" antwortbyUser.
     *
     * @param id the id of the antwortbyUser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/antwortby-users/{id}")
    public ResponseEntity<Void> deleteAntwortbyUser(@PathVariable Long id) {
        log.debug("REST request to delete AntwortbyUser : {}", id);
        antwortbyUserRepository.deleteById(id);
        antwortbyUserSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/antwortby-users?query=:query} : search for the antwortbyUser corresponding
     * to the query.
     *
     * @param query the query of the antwortbyUser search.
     * @return the result of the search.
     */
    @GetMapping("/_search/antwortby-users")
    public List<AntwortbyUser> searchAntwortbyUsers(@RequestParam String query) {
        log.debug("REST request to search AntwortbyUsers for query {}", query);
        return StreamSupport
            .stream(antwortbyUserSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }
}
