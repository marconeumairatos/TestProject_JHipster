package com.marconation.jhp.web.rest;

import com.marconation.jhp.JpollApp;
import com.marconation.jhp.domain.UserAntwort;
import com.marconation.jhp.repository.UserAntwortRepository;
import com.marconation.jhp.repository.search.UserAntwortSearchRepository;

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
 * Integration tests for the {@link UserAntwortResource} REST controller.
 */
@SpringBootTest(classes = JpollApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserAntwortResourceIT {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    @Autowired
    private UserAntwortRepository userAntwortRepository;

    /**
     * This repository is mocked in the com.marconation.jhp.repository.search test package.
     *
     * @see com.marconation.jhp.repository.search.UserAntwortSearchRepositoryMockConfiguration
     */
    @Autowired
    private UserAntwortSearchRepository mockUserAntwortSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserAntwortMockMvc;

    private UserAntwort userAntwort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAntwort createEntity(EntityManager em) {
        UserAntwort userAntwort = new UserAntwort()
            .user(DEFAULT_USER);
        return userAntwort;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAntwort createUpdatedEntity(EntityManager em) {
        UserAntwort userAntwort = new UserAntwort()
            .user(UPDATED_USER);
        return userAntwort;
    }

    @BeforeEach
    public void initTest() {
        userAntwort = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAntwort() throws Exception {
        int databaseSizeBeforeCreate = userAntwortRepository.findAll().size();
        // Create the UserAntwort
        restUserAntwortMockMvc.perform(post("/api/user-antworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAntwort)))
            .andExpect(status().isCreated());

        // Validate the UserAntwort in the database
        List<UserAntwort> userAntwortList = userAntwortRepository.findAll();
        assertThat(userAntwortList).hasSize(databaseSizeBeforeCreate + 1);
        UserAntwort testUserAntwort = userAntwortList.get(userAntwortList.size() - 1);
        assertThat(testUserAntwort.getUser()).isEqualTo(DEFAULT_USER);

        // Validate the UserAntwort in Elasticsearch
        verify(mockUserAntwortSearchRepository, times(1)).save(testUserAntwort);
    }

    @Test
    @Transactional
    public void createUserAntwortWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAntwortRepository.findAll().size();

        // Create the UserAntwort with an existing ID
        userAntwort.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAntwortMockMvc.perform(post("/api/user-antworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAntwort)))
            .andExpect(status().isBadRequest());

        // Validate the UserAntwort in the database
        List<UserAntwort> userAntwortList = userAntwortRepository.findAll();
        assertThat(userAntwortList).hasSize(databaseSizeBeforeCreate);

        // Validate the UserAntwort in Elasticsearch
        verify(mockUserAntwortSearchRepository, times(0)).save(userAntwort);
    }


    @Test
    @Transactional
    public void checkUserIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAntwortRepository.findAll().size();
        // set the field null
        userAntwort.setUser(null);

        // Create the UserAntwort, which fails.


        restUserAntwortMockMvc.perform(post("/api/user-antworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAntwort)))
            .andExpect(status().isBadRequest());

        List<UserAntwort> userAntwortList = userAntwortRepository.findAll();
        assertThat(userAntwortList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserAntworts() throws Exception {
        // Initialize the database
        userAntwortRepository.saveAndFlush(userAntwort);

        // Get all the userAntwortList
        restUserAntwortMockMvc.perform(get("/api/user-antworts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAntwort.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)));
    }
    
    @Test
    @Transactional
    public void getUserAntwort() throws Exception {
        // Initialize the database
        userAntwortRepository.saveAndFlush(userAntwort);

        // Get the userAntwort
        restUserAntwortMockMvc.perform(get("/api/user-antworts/{id}", userAntwort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAntwort.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER));
    }
    @Test
    @Transactional
    public void getNonExistingUserAntwort() throws Exception {
        // Get the userAntwort
        restUserAntwortMockMvc.perform(get("/api/user-antworts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAntwort() throws Exception {
        // Initialize the database
        userAntwortRepository.saveAndFlush(userAntwort);

        int databaseSizeBeforeUpdate = userAntwortRepository.findAll().size();

        // Update the userAntwort
        UserAntwort updatedUserAntwort = userAntwortRepository.findById(userAntwort.getId()).get();
        // Disconnect from session so that the updates on updatedUserAntwort are not directly saved in db
        em.detach(updatedUserAntwort);
        updatedUserAntwort
            .user(UPDATED_USER);

        restUserAntwortMockMvc.perform(put("/api/user-antworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserAntwort)))
            .andExpect(status().isOk());

        // Validate the UserAntwort in the database
        List<UserAntwort> userAntwortList = userAntwortRepository.findAll();
        assertThat(userAntwortList).hasSize(databaseSizeBeforeUpdate);
        UserAntwort testUserAntwort = userAntwortList.get(userAntwortList.size() - 1);
        assertThat(testUserAntwort.getUser()).isEqualTo(UPDATED_USER);

        // Validate the UserAntwort in Elasticsearch
        verify(mockUserAntwortSearchRepository, times(1)).save(testUserAntwort);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAntwort() throws Exception {
        int databaseSizeBeforeUpdate = userAntwortRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAntwortMockMvc.perform(put("/api/user-antworts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userAntwort)))
            .andExpect(status().isBadRequest());

        // Validate the UserAntwort in the database
        List<UserAntwort> userAntwortList = userAntwortRepository.findAll();
        assertThat(userAntwortList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UserAntwort in Elasticsearch
        verify(mockUserAntwortSearchRepository, times(0)).save(userAntwort);
    }

    @Test
    @Transactional
    public void deleteUserAntwort() throws Exception {
        // Initialize the database
        userAntwortRepository.saveAndFlush(userAntwort);

        int databaseSizeBeforeDelete = userAntwortRepository.findAll().size();

        // Delete the userAntwort
        restUserAntwortMockMvc.perform(delete("/api/user-antworts/{id}", userAntwort.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAntwort> userAntwortList = userAntwortRepository.findAll();
        assertThat(userAntwortList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UserAntwort in Elasticsearch
        verify(mockUserAntwortSearchRepository, times(1)).deleteById(userAntwort.getId());
    }

    @Test
    @Transactional
    public void searchUserAntwort() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        userAntwortRepository.saveAndFlush(userAntwort);
        when(mockUserAntwortSearchRepository.search(queryStringQuery("id:" + userAntwort.getId())))
            .thenReturn(Collections.singletonList(userAntwort));

        // Search the userAntwort
        restUserAntwortMockMvc.perform(get("/api/_search/user-antworts?query=id:" + userAntwort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAntwort.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)));
    }
}
