package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.WayToPay;
import co.edu.sena.repository.WayToPayRepository;
import co.edu.sena.service.dto.WayToPayDTO;
import co.edu.sena.service.mapper.WayToPayMapper;
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
 * Integration tests for the {@link WayToPayResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WayToPayResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/way-to-pays";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WayToPayRepository wayToPayRepository;

    @Autowired
    private WayToPayMapper wayToPayMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWayToPayMockMvc;

    private WayToPay wayToPay;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WayToPay createEntity(EntityManager em) {
        WayToPay wayToPay = new WayToPay().description(DEFAULT_DESCRIPTION);
        return wayToPay;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WayToPay createUpdatedEntity(EntityManager em) {
        WayToPay wayToPay = new WayToPay().description(UPDATED_DESCRIPTION);
        return wayToPay;
    }

    @BeforeEach
    public void initTest() {
        wayToPay = createEntity(em);
    }

    @Test
    @Transactional
    void createWayToPay() throws Exception {
        int databaseSizeBeforeCreate = wayToPayRepository.findAll().size();
        // Create the WayToPay
        WayToPayDTO wayToPayDTO = wayToPayMapper.toDto(wayToPay);
        restWayToPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wayToPayDTO)))
            .andExpect(status().isCreated());

        // Validate the WayToPay in the database
        List<WayToPay> wayToPayList = wayToPayRepository.findAll();
        assertThat(wayToPayList).hasSize(databaseSizeBeforeCreate + 1);
        WayToPay testWayToPay = wayToPayList.get(wayToPayList.size() - 1);
        assertThat(testWayToPay.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createWayToPayWithExistingId() throws Exception {
        // Create the WayToPay with an existing ID
        wayToPay.setId(1L);
        WayToPayDTO wayToPayDTO = wayToPayMapper.toDto(wayToPay);

        int databaseSizeBeforeCreate = wayToPayRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWayToPayMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wayToPayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WayToPay in the database
        List<WayToPay> wayToPayList = wayToPayRepository.findAll();
        assertThat(wayToPayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWayToPays() throws Exception {
        // Initialize the database
        wayToPayRepository.saveAndFlush(wayToPay);

        // Get all the wayToPayList
        restWayToPayMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(wayToPay.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getWayToPay() throws Exception {
        // Initialize the database
        wayToPayRepository.saveAndFlush(wayToPay);

        // Get the wayToPay
        restWayToPayMockMvc
            .perform(get(ENTITY_API_URL_ID, wayToPay.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(wayToPay.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingWayToPay() throws Exception {
        // Get the wayToPay
        restWayToPayMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWayToPay() throws Exception {
        // Initialize the database
        wayToPayRepository.saveAndFlush(wayToPay);

        int databaseSizeBeforeUpdate = wayToPayRepository.findAll().size();

        // Update the wayToPay
        WayToPay updatedWayToPay = wayToPayRepository.findById(wayToPay.getId()).get();
        // Disconnect from session so that the updates on updatedWayToPay are not directly saved in db
        em.detach(updatedWayToPay);
        updatedWayToPay.description(UPDATED_DESCRIPTION);
        WayToPayDTO wayToPayDTO = wayToPayMapper.toDto(updatedWayToPay);

        restWayToPayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wayToPayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wayToPayDTO))
            )
            .andExpect(status().isOk());

        // Validate the WayToPay in the database
        List<WayToPay> wayToPayList = wayToPayRepository.findAll();
        assertThat(wayToPayList).hasSize(databaseSizeBeforeUpdate);
        WayToPay testWayToPay = wayToPayList.get(wayToPayList.size() - 1);
        assertThat(testWayToPay.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingWayToPay() throws Exception {
        int databaseSizeBeforeUpdate = wayToPayRepository.findAll().size();
        wayToPay.setId(count.incrementAndGet());

        // Create the WayToPay
        WayToPayDTO wayToPayDTO = wayToPayMapper.toDto(wayToPay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWayToPayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, wayToPayDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wayToPayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WayToPay in the database
        List<WayToPay> wayToPayList = wayToPayRepository.findAll();
        assertThat(wayToPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWayToPay() throws Exception {
        int databaseSizeBeforeUpdate = wayToPayRepository.findAll().size();
        wayToPay.setId(count.incrementAndGet());

        // Create the WayToPay
        WayToPayDTO wayToPayDTO = wayToPayMapper.toDto(wayToPay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWayToPayMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(wayToPayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WayToPay in the database
        List<WayToPay> wayToPayList = wayToPayRepository.findAll();
        assertThat(wayToPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWayToPay() throws Exception {
        int databaseSizeBeforeUpdate = wayToPayRepository.findAll().size();
        wayToPay.setId(count.incrementAndGet());

        // Create the WayToPay
        WayToPayDTO wayToPayDTO = wayToPayMapper.toDto(wayToPay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWayToPayMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(wayToPayDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WayToPay in the database
        List<WayToPay> wayToPayList = wayToPayRepository.findAll();
        assertThat(wayToPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWayToPayWithPatch() throws Exception {
        // Initialize the database
        wayToPayRepository.saveAndFlush(wayToPay);

        int databaseSizeBeforeUpdate = wayToPayRepository.findAll().size();

        // Update the wayToPay using partial update
        WayToPay partialUpdatedWayToPay = new WayToPay();
        partialUpdatedWayToPay.setId(wayToPay.getId());

        restWayToPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWayToPay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWayToPay))
            )
            .andExpect(status().isOk());

        // Validate the WayToPay in the database
        List<WayToPay> wayToPayList = wayToPayRepository.findAll();
        assertThat(wayToPayList).hasSize(databaseSizeBeforeUpdate);
        WayToPay testWayToPay = wayToPayList.get(wayToPayList.size() - 1);
        assertThat(testWayToPay.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateWayToPayWithPatch() throws Exception {
        // Initialize the database
        wayToPayRepository.saveAndFlush(wayToPay);

        int databaseSizeBeforeUpdate = wayToPayRepository.findAll().size();

        // Update the wayToPay using partial update
        WayToPay partialUpdatedWayToPay = new WayToPay();
        partialUpdatedWayToPay.setId(wayToPay.getId());

        partialUpdatedWayToPay.description(UPDATED_DESCRIPTION);

        restWayToPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWayToPay.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWayToPay))
            )
            .andExpect(status().isOk());

        // Validate the WayToPay in the database
        List<WayToPay> wayToPayList = wayToPayRepository.findAll();
        assertThat(wayToPayList).hasSize(databaseSizeBeforeUpdate);
        WayToPay testWayToPay = wayToPayList.get(wayToPayList.size() - 1);
        assertThat(testWayToPay.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingWayToPay() throws Exception {
        int databaseSizeBeforeUpdate = wayToPayRepository.findAll().size();
        wayToPay.setId(count.incrementAndGet());

        // Create the WayToPay
        WayToPayDTO wayToPayDTO = wayToPayMapper.toDto(wayToPay);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWayToPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, wayToPayDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wayToPayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WayToPay in the database
        List<WayToPay> wayToPayList = wayToPayRepository.findAll();
        assertThat(wayToPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWayToPay() throws Exception {
        int databaseSizeBeforeUpdate = wayToPayRepository.findAll().size();
        wayToPay.setId(count.incrementAndGet());

        // Create the WayToPay
        WayToPayDTO wayToPayDTO = wayToPayMapper.toDto(wayToPay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWayToPayMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(wayToPayDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WayToPay in the database
        List<WayToPay> wayToPayList = wayToPayRepository.findAll();
        assertThat(wayToPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWayToPay() throws Exception {
        int databaseSizeBeforeUpdate = wayToPayRepository.findAll().size();
        wayToPay.setId(count.incrementAndGet());

        // Create the WayToPay
        WayToPayDTO wayToPayDTO = wayToPayMapper.toDto(wayToPay);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWayToPayMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(wayToPayDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WayToPay in the database
        List<WayToPay> wayToPayList = wayToPayRepository.findAll();
        assertThat(wayToPayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWayToPay() throws Exception {
        // Initialize the database
        wayToPayRepository.saveAndFlush(wayToPay);

        int databaseSizeBeforeDelete = wayToPayRepository.findAll().size();

        // Delete the wayToPay
        restWayToPayMockMvc
            .perform(delete(ENTITY_API_URL_ID, wayToPay.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WayToPay> wayToPayList = wayToPayRepository.findAll();
        assertThat(wayToPayList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
