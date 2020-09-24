package com.marconation.jhp.web.rest;

import com.marconation.jhp.JpollApp;
import com.marconation.jhp.domain.Umfrage;
import com.marconation.jhp.repository.UmfrageRepository;
import com.marconation.jhp.repository.search.UmfrageSearchRepository;

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
 * Integration tests for the {@link UmfrageResource} REST controller.
 */
@SpringBootTest(classes = JpollApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UmfrageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private UmfrageRepository umfrageRepository;

    /**
     * This repository is mocked in the com.marconation.jhp.repository.search test package.
     *
     * @see com.marconation.jhp.repository.search.UmfrageSearchRepositoryMockConfiguration
     */
    @Autowired
    private UmfrageSearchRepository mockUmfrageSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUmfrageMockMvc;

    private Umfrage umfrage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Umfrage createEntity(EntityManager em) {
        Umfrage umfrage = new Umfrage()
            .name(DEFAULT_NAME)
            .text(DEFAULT_TEXT)
            .status(DEFAULT_STATUS);
        return umfrage;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Umfrage createUpdatedEntity(EntityManager em) {
        Umfrage umfrage = new Umfrage()
            .name(UPDATED_NAME)
            .text(UPDATED_TEXT)
            .status(UPDATED_STATUS);
        return umfrage;
    }

    @BeforeEach
    public void initTest() {
        umfrage = createEntity(em);
    }

    @Test
    @Transactional
    public void createUmfrage() throws Exception {
        int databaseSizeBeforeCreate = umfrageRepository.findAll().size();
        // Create the Umfrage
        restUmfrageMockMvc.perform(post("/api/umfrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umfrage)))
            .andExpect(status().isCreated());

        // Validate the Umfrage in the database
        List<Umfrage> umfrageList = umfrageRepository.findAll();
        assertThat(umfrageList).hasSize(databaseSizeBeforeCreate + 1);
        Umfrage testUmfrage = umfrageList.get(umfrageList.size() - 1);
        assertThat(testUmfrage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUmfrage.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testUmfrage.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the Umfrage in Elasticsearch
        verify(mockUmfrageSearchRepository, times(1)).save(testUmfrage);
    }

    @Test
    @Transactional
    public void createUmfrageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = umfrageRepository.findAll().size();

        // Create the Umfrage with an existing ID
        umfrage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUmfrageMockMvc.perform(post("/api/umfrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umfrage)))
            .andExpect(status().isBadRequest());

        // Validate the Umfrage in the database
        List<Umfrage> umfrageList = umfrageRepository.findAll();
        assertThat(umfrageList).hasSize(databaseSizeBeforeCreate);

        // Validate the Umfrage in Elasticsearch
        verify(mockUmfrageSearchRepository, times(0)).save(umfrage);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = umfrageRepository.findAll().size();
        // set the field null
        umfrage.setName(null);

        // Create the Umfrage, which fails.


        restUmfrageMockMvc.perform(post("/api/umfrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umfrage)))
            .andExpect(status().isBadRequest());

        List<Umfrage> umfrageList = umfrageRepository.findAll();
        assertThat(umfrageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = umfrageRepository.findAll().size();
        // set the field null
        umfrage.setText(null);

        // Create the Umfrage, which fails.


        restUmfrageMockMvc.perform(post("/api/umfrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umfrage)))
            .andExpect(status().isBadRequest());

        List<Umfrage> umfrageList = umfrageRepository.findAll();
        assertThat(umfrageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = umfrageRepository.findAll().size();
        // set the field null
        umfrage.setStatus(null);

        // Create the Umfrage, which fails.


        restUmfrageMockMvc.perform(post("/api/umfrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umfrage)))
            .andExpect(status().isBadRequest());

        List<Umfrage> umfrageList = umfrageRepository.findAll();
        assertThat(umfrageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUmfrages() throws Exception {
        // Initialize the database
        umfrageRepository.saveAndFlush(umfrage);

        // Get all the umfrageList
        restUmfrageMockMvc.perform(get("/api/umfrages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(umfrage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getUmfrage() throws Exception {
        // Initialize the database
        umfrageRepository.saveAndFlush(umfrage);

        // Get the umfrage
        restUmfrageMockMvc.perform(get("/api/umfrages/{id}", umfrage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(umfrage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }
    @Test
    @Transactional
    public void getNonExistingUmfrage() throws Exception {
        // Get the umfrage
        restUmfrageMockMvc.perform(get("/api/umfrages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUmfrage() throws Exception {
        // Initialize the database
        umfrageRepository.saveAndFlush(umfrage);

        int databaseSizeBeforeUpdate = umfrageRepository.findAll().size();

        // Update the umfrage
        Umfrage updatedUmfrage = umfrageRepository.findById(umfrage.getId()).get();
        // Disconnect from session so that the updates on updatedUmfrage are not directly saved in db
        em.detach(updatedUmfrage);
        updatedUmfrage
            .name(UPDATED_NAME)
            .text(UPDATED_TEXT)
            .status(UPDATED_STATUS);

        restUmfrageMockMvc.perform(put("/api/umfrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUmfrage)))
            .andExpect(status().isOk());

        // Validate the Umfrage in the database
        List<Umfrage> umfrageList = umfrageRepository.findAll();
        assertThat(umfrageList).hasSize(databaseSizeBeforeUpdate);
        Umfrage testUmfrage = umfrageList.get(umfrageList.size() - 1);
        assertThat(testUmfrage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUmfrage.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testUmfrage.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the Umfrage in Elasticsearch
        verify(mockUmfrageSearchRepository, times(1)).save(testUmfrage);
    }

    @Test
    @Transactional
    public void updateNonExistingUmfrage() throws Exception {
        int databaseSizeBeforeUpdate = umfrageRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUmfrageMockMvc.perform(put("/api/umfrages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(umfrage)))
            .andExpect(status().isBadRequest());

        // Validate the Umfrage in the database
        List<Umfrage> umfrageList = umfrageRepository.findAll();
        assertThat(umfrageList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Umfrage in Elasticsearch
        verify(mockUmfrageSearchRepository, times(0)).save(umfrage);
    }

    @Test
    @Transactional
    public void deleteUmfrage() throws Exception {
        // Initialize the database
        umfrageRepository.saveAndFlush(umfrage);

        int databaseSizeBeforeDelete = umfrageRepository.findAll().size();

        // Delete the umfrage
        restUmfrageMockMvc.perform(delete("/api/umfrages/{id}", umfrage.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Umfrage> umfrageList = umfrageRepository.findAll();
        assertThat(umfrageList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Umfrage in Elasticsearch
        verify(mockUmfrageSearchRepository, times(1)).deleteById(umfrage.getId());
    }

    @Test
    @Transactional
    public void searchUmfrage() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        umfrageRepository.saveAndFlush(umfrage);
        when(mockUmfrageSearchRepository.search(queryStringQuery("id:" + umfrage.getId())))
            .thenReturn(Collections.singletonList(umfrage));

        // Search the umfrage
        restUmfrageMockMvc.perform(get("/api/_search/umfrages?query=id:" + umfrage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(umfrage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
}
