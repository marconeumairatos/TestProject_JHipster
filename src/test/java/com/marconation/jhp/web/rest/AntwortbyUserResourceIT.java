package com.marconation.jhp.web.rest;

import com.marconation.jhp.JpollApp;
import com.marconation.jhp.domain.AntwortbyUser;
import com.marconation.jhp.repository.AntwortbyUserRepository;
import com.marconation.jhp.repository.search.AntwortbyUserSearchRepository;

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
 * Integration tests for the {@link AntwortbyUserResource} REST controller.
 */
@SpringBootTest(classes = JpollApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class AntwortbyUserResourceIT {

    private static final Integer DEFAULT_USER_ID = 1;
    private static final Integer UPDATED_USER_ID = 2;

    @Autowired
    private AntwortbyUserRepository antwortbyUserRepository;

    /**
     * This repository is mocked in the com.marconation.jhp.repository.search test package.
     *
     * @see com.marconation.jhp.repository.search.AntwortbyUserSearchRepositoryMockConfiguration
     */
    @Autowired
    private AntwortbyUserSearchRepository mockAntwortbyUserSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAntwortbyUserMockMvc;

    private AntwortbyUser antwortbyUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AntwortbyUser createEntity(EntityManager em) {
        AntwortbyUser antwortbyUser = new AntwortbyUser()
            .userID(DEFAULT_USER_ID);
        return antwortbyUser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AntwortbyUser createUpdatedEntity(EntityManager em) {
        AntwortbyUser antwortbyUser = new AntwortbyUser()
            .userID(UPDATED_USER_ID);
        return antwortbyUser;
    }

    @BeforeEach
    public void initTest() {
        antwortbyUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createAntwortbyUser() throws Exception {
        int databaseSizeBeforeCreate = antwortbyUserRepository.findAll().size();
        // Create the AntwortbyUser
        restAntwortbyUserMockMvc.perform(post("/api/antwortby-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antwortbyUser)))
            .andExpect(status().isCreated());

        // Validate the AntwortbyUser in the database
        List<AntwortbyUser> antwortbyUserList = antwortbyUserRepository.findAll();
        assertThat(antwortbyUserList).hasSize(databaseSizeBeforeCreate + 1);
        AntwortbyUser testAntwortbyUser = antwortbyUserList.get(antwortbyUserList.size() - 1);
        assertThat(testAntwortbyUser.getUserID()).isEqualTo(DEFAULT_USER_ID);

        // Validate the AntwortbyUser in Elasticsearch
        verify(mockAntwortbyUserSearchRepository, times(1)).save(testAntwortbyUser);
    }

    @Test
    @Transactional
    public void createAntwortbyUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = antwortbyUserRepository.findAll().size();

        // Create the AntwortbyUser with an existing ID
        antwortbyUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAntwortbyUserMockMvc.perform(post("/api/antwortby-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antwortbyUser)))
            .andExpect(status().isBadRequest());

        // Validate the AntwortbyUser in the database
        List<AntwortbyUser> antwortbyUserList = antwortbyUserRepository.findAll();
        assertThat(antwortbyUserList).hasSize(databaseSizeBeforeCreate);

        // Validate the AntwortbyUser in Elasticsearch
        verify(mockAntwortbyUserSearchRepository, times(0)).save(antwortbyUser);
    }


    @Test
    @Transactional
    public void checkUserIDIsRequired() throws Exception {
        int databaseSizeBeforeTest = antwortbyUserRepository.findAll().size();
        // set the field null
        antwortbyUser.setUserID(null);

        // Create the AntwortbyUser, which fails.


        restAntwortbyUserMockMvc.perform(post("/api/antwortby-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antwortbyUser)))
            .andExpect(status().isBadRequest());

        List<AntwortbyUser> antwortbyUserList = antwortbyUserRepository.findAll();
        assertThat(antwortbyUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAntwortbyUsers() throws Exception {
        // Initialize the database
        antwortbyUserRepository.saveAndFlush(antwortbyUser);

        // Get all the antwortbyUserList
        restAntwortbyUserMockMvc.perform(get("/api/antwortby-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antwortbyUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID)));
    }
    
    @Test
    @Transactional
    public void getAntwortbyUser() throws Exception {
        // Initialize the database
        antwortbyUserRepository.saveAndFlush(antwortbyUser);

        // Get the antwortbyUser
        restAntwortbyUserMockMvc.perform(get("/api/antwortby-users/{id}", antwortbyUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(antwortbyUser.getId().intValue()))
            .andExpect(jsonPath("$.userID").value(DEFAULT_USER_ID));
    }
    @Test
    @Transactional
    public void getNonExistingAntwortbyUser() throws Exception {
        // Get the antwortbyUser
        restAntwortbyUserMockMvc.perform(get("/api/antwortby-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAntwortbyUser() throws Exception {
        // Initialize the database
        antwortbyUserRepository.saveAndFlush(antwortbyUser);

        int databaseSizeBeforeUpdate = antwortbyUserRepository.findAll().size();

        // Update the antwortbyUser
        AntwortbyUser updatedAntwortbyUser = antwortbyUserRepository.findById(antwortbyUser.getId()).get();
        // Disconnect from session so that the updates on updatedAntwortbyUser are not directly saved in db
        em.detach(updatedAntwortbyUser);
        updatedAntwortbyUser
            .userID(UPDATED_USER_ID);

        restAntwortbyUserMockMvc.perform(put("/api/antwortby-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAntwortbyUser)))
            .andExpect(status().isOk());

        // Validate the AntwortbyUser in the database
        List<AntwortbyUser> antwortbyUserList = antwortbyUserRepository.findAll();
        assertThat(antwortbyUserList).hasSize(databaseSizeBeforeUpdate);
        AntwortbyUser testAntwortbyUser = antwortbyUserList.get(antwortbyUserList.size() - 1);
        assertThat(testAntwortbyUser.getUserID()).isEqualTo(UPDATED_USER_ID);

        // Validate the AntwortbyUser in Elasticsearch
        verify(mockAntwortbyUserSearchRepository, times(1)).save(testAntwortbyUser);
    }

    @Test
    @Transactional
    public void updateNonExistingAntwortbyUser() throws Exception {
        int databaseSizeBeforeUpdate = antwortbyUserRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAntwortbyUserMockMvc.perform(put("/api/antwortby-users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(antwortbyUser)))
            .andExpect(status().isBadRequest());

        // Validate the AntwortbyUser in the database
        List<AntwortbyUser> antwortbyUserList = antwortbyUserRepository.findAll();
        assertThat(antwortbyUserList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AntwortbyUser in Elasticsearch
        verify(mockAntwortbyUserSearchRepository, times(0)).save(antwortbyUser);
    }

    @Test
    @Transactional
    public void deleteAntwortbyUser() throws Exception {
        // Initialize the database
        antwortbyUserRepository.saveAndFlush(antwortbyUser);

        int databaseSizeBeforeDelete = antwortbyUserRepository.findAll().size();

        // Delete the antwortbyUser
        restAntwortbyUserMockMvc.perform(delete("/api/antwortby-users/{id}", antwortbyUser.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AntwortbyUser> antwortbyUserList = antwortbyUserRepository.findAll();
        assertThat(antwortbyUserList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AntwortbyUser in Elasticsearch
        verify(mockAntwortbyUserSearchRepository, times(1)).deleteById(antwortbyUser.getId());
    }

    @Test
    @Transactional
    public void searchAntwortbyUser() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        antwortbyUserRepository.saveAndFlush(antwortbyUser);
        when(mockAntwortbyUserSearchRepository.search(queryStringQuery("id:" + antwortbyUser.getId())))
            .thenReturn(Collections.singletonList(antwortbyUser));

        // Search the antwortbyUser
        restAntwortbyUserMockMvc.perform(get("/api/_search/antwortby-users?query=id:" + antwortbyUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(antwortbyUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userID").value(hasItem(DEFAULT_USER_ID)));
    }
}
