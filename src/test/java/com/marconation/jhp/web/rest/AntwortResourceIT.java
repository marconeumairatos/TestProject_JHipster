package com.marconation.jhp.web.rest;

import com.marconation.jhp.JpollApp;
import com.marconation.jhp.domain.Antwort;
import com.marconation.jhp.repository.AntwortRepository;
import com.marconation.jhp.repository.search.AntwortSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AntwortResource} REST controller.
 */
@SpringBootTest(classes = JpollApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AntwortResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    @Autowired
    private AntwortRepository antwortRepository;

    /**
     * This repository is mocked in the com.marconation.jhp.repository.search test package.
     *
     * @see com.marconation.jhp.repository.search.AntwortSearchRepositoryMockConfiguration
     */
    @Autowired
    private AntwortSearchRepository mockAntwortSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAntwortMockMvc;

    private Antwort antwort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Antwort createEntity(EntityManager em) {
        Antwort antwort = new Antwort()
            .text(DEFAULT_TEXT);
        return antwort;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Antwort createUpdatedEntity(EntityManager em) {
        Antwort antwort = new Antwort()
            .text(UPDATED_TEXT);
        return antwort;
    }

    @BeforeEach
    public void initTest() {
        antwort = createEntity(em);
    }

    @Test
    @Transactional
    public void createAntwort() throws Exception {
        int databaseSizeBeforeCreate = antwortRepository.findAll().size();
        // Create the Antwort
        restAntwortMockMvc.perform(post("/api/antworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antwort)))
            .andExpect(status().isCreated());

        // Validate the Antwort in the database
        List<Antwort> antwortList = antwortRepository.findAll();
        assertThat(antwortList).hasSize(databaseSizeBeforeCreate + 1);
        Antwort testAntwort = antwortList.get(antwortList.size() - 1);
        assertThat(testAntwort.getText()).isEqualTo(DEFAULT_TEXT);

        // Validate the Antwort in Elasticsearch
        verify(mockAntwortSearchRepository, times(1)).save(testAntwort);
    }

    @Test
    @Transactional
    public void createAntwortWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = antwortRepository.findAll().size();

        // Create the Antwort with an existing ID
        antwort.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAntwortMockMvc.perform(post("/api/antworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antwort)))
            .andExpect(status().isBadRequest());

        // Validate the Antwort in the database
        List<Antwort> antwortList = antwortRepository.findAll();
        assertThat(antwortList).hasSize(databaseSizeBeforeCreate);

        // Validate the Antwort in Elasticsearch
        verify(mockAntwortSearchRepository, times(0)).save(antwort);
    }


    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = antwortRepository.findAll().size();
        // set the field null
        antwort.setText(null);

        // Create the Antwort, which fails.


        restAntwortMockMvc.perform(post("/api/antworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antwort)))
            .andExpect(status().isBadRequest());

        List<Antwort> antwortList = antwortRepository.findAll();
        assertThat(antwortList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAntworts() throws Exception {
        // Initialize the database
        antwortRepository.saveAndFlush(antwort);

        // Get all the antwortList
        restAntwortMockMvc.perform(get("/api/antworts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antwort.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }
    
    @Test
    @Transactional
    public void getAntwort() throws Exception {
        // Initialize the database
        antwortRepository.saveAndFlush(antwort);

        // Get the antwort
        restAntwortMockMvc.perform(get("/api/antworts/{id}", antwort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(antwort.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }
    @Test
    @Transactional
    public void getNonExistingAntwort() throws Exception {
        // Get the antwort
        restAntwortMockMvc.perform(get("/api/antworts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAntwort() throws Exception {
        // Initialize the database
        antwortRepository.saveAndFlush(antwort);

        int databaseSizeBeforeUpdate = antwortRepository.findAll().size();

        // Update the antwort
        Antwort updatedAntwort = antwortRepository.findById(antwort.getId()).get();
        // Disconnect from session so that the updates on updatedAntwort are not directly saved in db
        em.detach(updatedAntwort);
        updatedAntwort
            .text(UPDATED_TEXT);

        restAntwortMockMvc.perform(put("/api/antworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAntwort)))
            .andExpect(status().isOk());

        // Validate the Antwort in the database
        List<Antwort> antwortList = antwortRepository.findAll();
        assertThat(antwortList).hasSize(databaseSizeBeforeUpdate);
        Antwort testAntwort = antwortList.get(antwortList.size() - 1);
        assertThat(testAntwort.getText()).isEqualTo(UPDATED_TEXT);

        // Validate the Antwort in Elasticsearch
        verify(mockAntwortSearchRepository, times(1)).save(testAntwort);
    }

    @Test
    @Transactional
    public void updateNonExistingAntwort() throws Exception {
        int databaseSizeBeforeUpdate = antwortRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAntwortMockMvc.perform(put("/api/antworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antwort)))
            .andExpect(status().isBadRequest());

        // Validate the Antwort in the database
        List<Antwort> antwortList = antwortRepository.findAll();
        assertThat(antwortList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Antwort in Elasticsearch
        verify(mockAntwortSearchRepository, times(0)).save(antwort);
    }

    @Test
    @Transactional
    public void deleteAntwort() throws Exception {
        // Initialize the database
        antwortRepository.saveAndFlush(antwort);

        int databaseSizeBeforeDelete = antwortRepository.findAll().size();

        // Delete the antwort
        restAntwortMockMvc.perform(delete("/api/antworts/{id}", antwort.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Antwort> antwortList = antwortRepository.findAll();
        assertThat(antwortList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Antwort in Elasticsearch
        verify(mockAntwortSearchRepository, times(1)).deleteById(antwort.getId());
    }

    @Test
    @Transactional
    public void searchAntwort() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        antwortRepository.saveAndFlush(antwort);
        when(mockAntwortSearchRepository.search(queryStringQuery("id:" + antwort.getId())))
            .thenReturn(Collections.singletonList(antwort));

        // Search the antwort
        restAntwortMockMvc.perform(get("/api/_search/antworts?query=id:" + antwort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antwort.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }

}
