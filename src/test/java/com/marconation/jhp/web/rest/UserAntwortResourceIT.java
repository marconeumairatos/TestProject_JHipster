package com.marconation.jhp.web.rest;

import com.marconation.jhp.JpollApp;
import com.marconation.jhp.domain.Userantwort;
import com.marconation.jhp.repository.UserantwortRepository;
import com.marconation.jhp.repository.search.UserantwortSearchRepository;

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
 * Integration tests for the {@link UserantwortResource} REST controller.
 */
@SpringBootTest(classes = JpollApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserantwortResourceIT {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    @Autowired
    private UserantwortRepository userantwortRepository;

    /**
     * This repository is mocked in the com.marconation.jhp.repository.search test package.
     *
     * @see com.marconation.jhp.repository.search.UserantwortSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserantwortSearchRepository mockUserantwortSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserantwortMockMvc;

    private Userantwort userantwort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Userantwort createEntity(EntityManager em) {
        Userantwort userantwort = new Userantwort()
            .userID(DEFAULT_USER_ID);
        return userantwort;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Userantwort createUpdatedEntity(EntityManager em) {
        Userantwort userantwort = new Userantwort()
            .userID(UPDATED_USER_ID);
        return userantwort;
    }

    @BeforeEach
    public void initTest() {
        userantwort = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserantwort() throws Exception {
        int databaseSizeBeforeCreate = userantwortRepository.findAll().size();
        // Create the Userantwort
        restUserantwortMockMvc.perform(post("/api/userantworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userantwort)))
            .andExpect(status().isCreated());

        // Validate the Userantwort in the database
        List<Userantwort> userantwortList = userantwortRepository.findAll();
        assertThat(userantwortList).hasSize(databaseSizeBeforeCreate + 1);
        Userantwort testUserantwort = userantwortList.get(userantwortList.size() - 1);
        assertThat(testUserantwort.getUserID()).isEqualTo(DEFAULT_USER_ID);

        // Validate the Userantwort in Elasticsearch
        verify(mockUserantwortSearchRepository, times(1)).save(testUserantwort);
    }

    @Test
    @Transactional
    public void createUserantwortWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userantwortRepository.findAll().size();

        // Create the Userantwort with an existing ID
        userantwort.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserantwortMockMvc.perform(post("/api/userantworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userantwort)))
            .andExpect(status().isBadRequest());

        // Validate the Userantwort in the database
        List<Userantwort> userantwortList = userantwortRepository.findAll();
        assertThat(userantwortList).hasSize(databaseSizeBeforeCreate);

        // Validate the Userantwort in Elasticsearch
        verify(mockUserantwortSearchRepository, times(0)).save(userantwort);
    }


    @Test
    @Transactional
    public void checkUserIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = userantwortRepository.findAll().size();
        // set the field null
        userantwort.setUserID(null);

        // Create the Userantwort, which fails.


        restUserantwortMockMvc.perform(post("/api/userantworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userantwort)))
            .andExpect(status().isBadRequest());

        List<Userantwort> userantwortList = userantwortRepository.findAll();
        assertThat(userantwortList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserantworts() throws Exception {
        // Initialize the database
        userantwortRepository.saveAndFlush(userantwort);

        // Get all the userantwortList
        restUserantwortMockMvc.perform(get("/api/userantworts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userantwort.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID)));
    }
    
    @Test
    @Transactional
    public void getUserantwort() throws Exception {
        // Initialize the database
        userantwortRepository.saveAndFlush(userantwort);

        // Get the userantwort
        restUserantwortMockMvc.perform(get("/api/userantworts/{id}", userantwort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userantwort.getId().intValue()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID));
    }
    @Test
    @Transactional
    public void getNonExistingUserantwort() throws Exception {
        // Get the userantwort
        restUserantwortMockMvc.perform(get("/api/userantworts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserantwort() throws Exception {
        // Initialize the database
        userantwortRepository.saveAndFlush(userantwort);

        int databaseSizeBeforeUpdate = userantwortRepository.findAll().size();

        // Update the userantwort
        Userantwort updatedUserantwort = userantwortRepository.findById(userantwort.getId()).get();
        // Disconnect from session so that the updates on updatedUserantwort are not directly saved in db
        em.detach(updatedUserantwort);
        updatedUserantwort
            .userID(UPDATED_USER_ID);

        restUserantwortMockMvc.perform(put("/api/userantworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserantwort)))
            .andExpect(status().isOk());

        // Validate the Userantwort in the database
        List<Userantwort> userantwortList = userantwortRepository.findAll();
        assertThat(userantwortList).hasSize(databaseSizeBeforeUpdate);
        Userantwort testUserantwort = userantwortList.get(userantwortList.size() - 1);
        assertThat(testUserantwort.getUserID()).isEqualTo(UPDATED_USER_ID);

        // Validate the Userantwort in Elasticsearch
        verify(mockUserantwortSearchRepository, times(1)).save(testUserantwort);
    }

    @Test
    @Transactional
    public void updateNonExistingUserantwort() throws Exception {
        int databaseSizeBeforeUpdate = userantwortRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserantwortMockMvc.perform(put("/api/userantworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userantwort)))
            .andExpect(status().isBadRequest());

        // Validate the Userantwort in the database
        List<Userantwort> userantwortList = userantwortRepository.findAll();
        assertThat(userantwortList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Userantwort in Elasticsearch
        verify(mockUserantwortSearchRepository, times(0)).save(userantwort);
    }

    @Test
    @Transactional
    public void deleteUserantwort() throws Exception {
        // Initialize the database
        userantwortRepository.saveAndFlush(userantwort);

        int databaseSizeBeforeDelete = userantwortRepository.findAll().size();

        // Delete the userantwort
        restUserantwortMockMvc.perform(delete("/api/userantworts/{id}", userantwort.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Userantwort> userantwortList = userantwortRepository.findAll();
        assertThat(userantwortList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Userantwort in Elasticsearch
        verify(mockUserantwortSearchRepository, times(1)).deleteById(userantwort.getId());
    }

    @Test
    @Transactional
    public void searchUserantwort() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        userantwortRepository.saveAndFlush(userantwort);
        when(mockUserantwortSearchRepository.search(queryStringQuery("id:" + userantwort.getId())))
            .thenReturn(Collections.singletonList(userantwort));

        // Search the userantwort
        restUserantwortMockMvc.perform(get("/api/_search/userantworts?query=id:" + userantwort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userantwort.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID)));
    }
}
