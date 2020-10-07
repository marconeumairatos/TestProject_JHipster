package com.marconation.jhp.web.rest;

import com.marconation.jhp.domain.Umfrage;
import com.marconation.jhp.repository.UmfrageRepository;
import com.marconation.jhp.repository.search.UmfrageSearchRepository;
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
 * REST controller for managing {@link com.marconation.jhp.domain.Umfrage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UmfrageResource {

    private final Logger log = LoggerFactory.getLogger(UmfrageResource.class);

    private static final String ENTITY_NAME = "umfrage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UmfrageRepository umfrageRepository;

    private final UmfrageSearchRepository umfrageSearchRepository;

    public UmfrageResource(UmfrageRepository umfrageRepository, UmfrageSearchRepository umfrageSearchRepository) {
        this.umfrageRepository = umfrageRepository;
        this.umfrageSearchRepository = umfrageSearchRepository;
    }

    /**
     * {@code POST  /umfrages} : Create a new umfrage.
     *
     * @param umfrage the umfrage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new umfrage, or with status {@code 400 (Bad Request)} if the umfrage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/umfrages")
    public ResponseEntity<Umfrage> createUmfrage(@Valid @RequestBody Umfrage umfrage) throws URISyntaxException {
        log.debug("REST request to save Umfrage : {}", umfrage);
        if (umfrage.getId() != null) {
            throw new BadRequestAlertException("A new umfrage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Umfrage result = umfrageRepository.save(umfrage);
        umfrageSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/umfrages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /umfrages} : Updates an existing umfrage.
     *
     * @param umfrage the umfrage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated umfrage,
     * or with status {@code 400 (Bad Request)} if the umfrage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the umfrage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/umfrages")
    public ResponseEntity<Umfrage> updateUmfrage(@Valid @RequestBody Umfrage umfrage) throws URISyntaxException {
        log.debug("REST request to update Umfrage : {}", umfrage);
        if (umfrage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Umfrage result = umfrageRepository.save(umfrage);
        umfrageSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, umfrage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /umfrages} : get all the umfrages.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of umfrages in body.
     */
    @GetMapping("/umfrages")
    public List<Umfrage> getAllUmfrages() {
        log.debug("REST request to get all Umfrages");
        return umfrageRepository.findAll();
    }

    /**
     * {@code GET  /umfrages/:id} : get the "id" umfrage.
     *
     * @param id the id of the umfrage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the umfrage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/umfrages/{id}")
    public ResponseEntity<Umfrage> getUmfrage(@PathVariable Long id) {
        log.debug("REST request to get Umfrage : {}", id);
        Optional<Umfrage> umfrage = umfrageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(umfrage);
    }

    /**
     * {@code DELETE  /umfrages/:id} : delete the "id" umfrage.
     *
     * @param id the id of the umfrage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/umfrages/{id}")
    public ResponseEntity<Void> deleteUmfrage(@PathVariable Long id) {
        log.debug("REST request to delete Umfrage : {}", id);
        umfrageRepository.deleteById(id);
        umfrageSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/umfrages?query=:query} : search for the umfrage corresponding
     * to the query.
     *
     * @param query the query of the umfrage search.
     * @return the result of the search.
     */
    @GetMapping("/_search/umfrages")
    public List<Umfrage> searchUmfrages(@RequestParam String query) {
        log.debug("REST request to search Umfrages for query {}", query);
        return StreamSupport
            .stream(umfrageSearchRepository.search(queryStringQuery(query)).spliterator(), false)
        .collect(Collectors.toList());
    }





    
}
