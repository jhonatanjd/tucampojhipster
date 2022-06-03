package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Anonymous;
import co.edu.sena.repository.AnonymousRepository;
import co.edu.sena.service.dto.AnonymousDTO;
import co.edu.sena.service.mapper.AnonymousMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AnonymousResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnonymousResourceIT {

    private static final String ENTITY_API_URL = "/api/anonymous";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnonymousRepository anonymousRepository;

    @Autowired
    private AnonymousMapper anonymousMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnonymousMockMvc;

    private Anonymous anonymous;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anonymous createEntity(EntityManager em) {
        Anonymous anonymous = new Anonymous();
        return anonymous;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anonymous createUpdatedEntity(EntityManager em) {
        Anonymous anonymous = new Anonymous();
        return anonymous;
    }

    @BeforeEach
    public void initTest() {
        anonymous = createEntity(em);
    }

    @Test
    @Transactional
    void createAnonymous() throws Exception {
        int databaseSizeBeforeCreate = anonymousRepository.findAll().size();
        // Create the Anonymous
        AnonymousDTO anonymousDTO = anonymousMapper.toDto(anonymous);
        restAnonymousMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anonymousDTO)))
            .andExpect(status().isCreated());

        // Validate the Anonymous in the database
        List<Anonymous> anonymousList = anonymousRepository.findAll();
        assertThat(anonymousList).hasSize(databaseSizeBeforeCreate + 1);
        Anonymous testAnonymous = anonymousList.get(anonymousList.size() - 1);
    }

    @Test
    @Transactional
    void createAnonymousWithExistingId() throws Exception {
        // Create the Anonymous with an existing ID
        anonymous.setId(1L);
        AnonymousDTO anonymousDTO = anonymousMapper.toDto(anonymous);

        int databaseSizeBeforeCreate = anonymousRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnonymousMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anonymousDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Anonymous in the database
        List<Anonymous> anonymousList = anonymousRepository.findAll();
        assertThat(anonymousList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnonymous() throws Exception {
        // Initialize the database
        anonymousRepository.saveAndFlush(anonymous);

        // Get all the anonymousList
        restAnonymousMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anonymous.getId().intValue())));
    }

    @Test
    @Transactional
    void getAnonymous() throws Exception {
        // Initialize the database
        anonymousRepository.saveAndFlush(anonymous);

        // Get the anonymous
        restAnonymousMockMvc
            .perform(get(ENTITY_API_URL_ID, anonymous.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anonymous.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingAnonymous() throws Exception {
        // Get the anonymous
        restAnonymousMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnonymous() throws Exception {
        // Initialize the database
        anonymousRepository.saveAndFlush(anonymous);

        int databaseSizeBeforeUpdate = anonymousRepository.findAll().size();

        // Update the anonymous
        Anonymous updatedAnonymous = anonymousRepository.findById(anonymous.getId()).get();
        // Disconnect from session so that the updates on updatedAnonymous are not directly saved in db
        em.detach(updatedAnonymous);
        AnonymousDTO anonymousDTO = anonymousMapper.toDto(updatedAnonymous);

        restAnonymousMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anonymousDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anonymousDTO))
            )
            .andExpect(status().isOk());

        // Validate the Anonymous in the database
        List<Anonymous> anonymousList = anonymousRepository.findAll();
        assertThat(anonymousList).hasSize(databaseSizeBeforeUpdate);
        Anonymous testAnonymous = anonymousList.get(anonymousList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingAnonymous() throws Exception {
        int databaseSizeBeforeUpdate = anonymousRepository.findAll().size();
        anonymous.setId(count.incrementAndGet());

        // Create the Anonymous
        AnonymousDTO anonymousDTO = anonymousMapper.toDto(anonymous);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnonymousMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anonymousDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anonymousDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anonymous in the database
        List<Anonymous> anonymousList = anonymousRepository.findAll();
        assertThat(anonymousList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnonymous() throws Exception {
        int databaseSizeBeforeUpdate = anonymousRepository.findAll().size();
        anonymous.setId(count.incrementAndGet());

        // Create the Anonymous
        AnonymousDTO anonymousDTO = anonymousMapper.toDto(anonymous);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnonymousMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(anonymousDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anonymous in the database
        List<Anonymous> anonymousList = anonymousRepository.findAll();
        assertThat(anonymousList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnonymous() throws Exception {
        int databaseSizeBeforeUpdate = anonymousRepository.findAll().size();
        anonymous.setId(count.incrementAndGet());

        // Create the Anonymous
        AnonymousDTO anonymousDTO = anonymousMapper.toDto(anonymous);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnonymousMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(anonymousDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Anonymous in the database
        List<Anonymous> anonymousList = anonymousRepository.findAll();
        assertThat(anonymousList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnonymousWithPatch() throws Exception {
        // Initialize the database
        anonymousRepository.saveAndFlush(anonymous);

        int databaseSizeBeforeUpdate = anonymousRepository.findAll().size();

        // Update the anonymous using partial update
        Anonymous partialUpdatedAnonymous = new Anonymous();
        partialUpdatedAnonymous.setId(anonymous.getId());

        restAnonymousMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnonymous.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnonymous))
            )
            .andExpect(status().isOk());

        // Validate the Anonymous in the database
        List<Anonymous> anonymousList = anonymousRepository.findAll();
        assertThat(anonymousList).hasSize(databaseSizeBeforeUpdate);
        Anonymous testAnonymous = anonymousList.get(anonymousList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateAnonymousWithPatch() throws Exception {
        // Initialize the database
        anonymousRepository.saveAndFlush(anonymous);

        int databaseSizeBeforeUpdate = anonymousRepository.findAll().size();

        // Update the anonymous using partial update
        Anonymous partialUpdatedAnonymous = new Anonymous();
        partialUpdatedAnonymous.setId(anonymous.getId());

        restAnonymousMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnonymous.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnonymous))
            )
            .andExpect(status().isOk());

        // Validate the Anonymous in the database
        List<Anonymous> anonymousList = anonymousRepository.findAll();
        assertThat(anonymousList).hasSize(databaseSizeBeforeUpdate);
        Anonymous testAnonymous = anonymousList.get(anonymousList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingAnonymous() throws Exception {
        int databaseSizeBeforeUpdate = anonymousRepository.findAll().size();
        anonymous.setId(count.incrementAndGet());

        // Create the Anonymous
        AnonymousDTO anonymousDTO = anonymousMapper.toDto(anonymous);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnonymousMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anonymousDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anonymousDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anonymous in the database
        List<Anonymous> anonymousList = anonymousRepository.findAll();
        assertThat(anonymousList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnonymous() throws Exception {
        int databaseSizeBeforeUpdate = anonymousRepository.findAll().size();
        anonymous.setId(count.incrementAndGet());

        // Create the Anonymous
        AnonymousDTO anonymousDTO = anonymousMapper.toDto(anonymous);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnonymousMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(anonymousDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anonymous in the database
        List<Anonymous> anonymousList = anonymousRepository.findAll();
        assertThat(anonymousList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnonymous() throws Exception {
        int databaseSizeBeforeUpdate = anonymousRepository.findAll().size();
        anonymous.setId(count.incrementAndGet());

        // Create the Anonymous
        AnonymousDTO anonymousDTO = anonymousMapper.toDto(anonymous);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnonymousMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(anonymousDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Anonymous in the database
        List<Anonymous> anonymousList = anonymousRepository.findAll();
        assertThat(anonymousList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnonymous() throws Exception {
        // Initialize the database
        anonymousRepository.saveAndFlush(anonymous);

        int databaseSizeBeforeDelete = anonymousRepository.findAll().size();

        // Delete the anonymous
        restAnonymousMockMvc
            .perform(delete(ENTITY_API_URL_ID, anonymous.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Anonymous> anonymousList = anonymousRepository.findAll();
        assertThat(anonymousList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
