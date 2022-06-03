package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Units;
import co.edu.sena.repository.UnitsRepository;
import co.edu.sena.service.dto.UnitsDTO;
import co.edu.sena.service.mapper.UnitsMapper;
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
 * Integration tests for the {@link UnitsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UnitsResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UnitsRepository unitsRepository;

    @Autowired
    private UnitsMapper unitsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUnitsMockMvc;

    private Units units;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Units createEntity(EntityManager em) {
        Units units = new Units().description(DEFAULT_DESCRIPTION);
        return units;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Units createUpdatedEntity(EntityManager em) {
        Units units = new Units().description(UPDATED_DESCRIPTION);
        return units;
    }

    @BeforeEach
    public void initTest() {
        units = createEntity(em);
    }

    @Test
    @Transactional
    void createUnits() throws Exception {
        int databaseSizeBeforeCreate = unitsRepository.findAll().size();
        // Create the Units
        UnitsDTO unitsDTO = unitsMapper.toDto(units);
        restUnitsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unitsDTO)))
            .andExpect(status().isCreated());

        // Validate the Units in the database
        List<Units> unitsList = unitsRepository.findAll();
        assertThat(unitsList).hasSize(databaseSizeBeforeCreate + 1);
        Units testUnits = unitsList.get(unitsList.size() - 1);
        assertThat(testUnits.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createUnitsWithExistingId() throws Exception {
        // Create the Units with an existing ID
        units.setId(1L);
        UnitsDTO unitsDTO = unitsMapper.toDto(units);

        int databaseSizeBeforeCreate = unitsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unitsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Units in the database
        List<Units> unitsList = unitsRepository.findAll();
        assertThat(unitsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUnits() throws Exception {
        // Initialize the database
        unitsRepository.saveAndFlush(units);

        // Get all the unitsList
        restUnitsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(units.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getUnits() throws Exception {
        // Initialize the database
        unitsRepository.saveAndFlush(units);

        // Get the units
        restUnitsMockMvc
            .perform(get(ENTITY_API_URL_ID, units.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(units.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingUnits() throws Exception {
        // Get the units
        restUnitsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUnits() throws Exception {
        // Initialize the database
        unitsRepository.saveAndFlush(units);

        int databaseSizeBeforeUpdate = unitsRepository.findAll().size();

        // Update the units
        Units updatedUnits = unitsRepository.findById(units.getId()).get();
        // Disconnect from session so that the updates on updatedUnits are not directly saved in db
        em.detach(updatedUnits);
        updatedUnits.description(UPDATED_DESCRIPTION);
        UnitsDTO unitsDTO = unitsMapper.toDto(updatedUnits);

        restUnitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unitsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unitsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Units in the database
        List<Units> unitsList = unitsRepository.findAll();
        assertThat(unitsList).hasSize(databaseSizeBeforeUpdate);
        Units testUnits = unitsList.get(unitsList.size() - 1);
        assertThat(testUnits.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingUnits() throws Exception {
        int databaseSizeBeforeUpdate = unitsRepository.findAll().size();
        units.setId(count.incrementAndGet());

        // Create the Units
        UnitsDTO unitsDTO = unitsMapper.toDto(units);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, unitsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Units in the database
        List<Units> unitsList = unitsRepository.findAll();
        assertThat(unitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUnits() throws Exception {
        int databaseSizeBeforeUpdate = unitsRepository.findAll().size();
        units.setId(count.incrementAndGet());

        // Create the Units
        UnitsDTO unitsDTO = unitsMapper.toDto(units);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(unitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Units in the database
        List<Units> unitsList = unitsRepository.findAll();
        assertThat(unitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUnits() throws Exception {
        int databaseSizeBeforeUpdate = unitsRepository.findAll().size();
        units.setId(count.incrementAndGet());

        // Create the Units
        UnitsDTO unitsDTO = unitsMapper.toDto(units);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(unitsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Units in the database
        List<Units> unitsList = unitsRepository.findAll();
        assertThat(unitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUnitsWithPatch() throws Exception {
        // Initialize the database
        unitsRepository.saveAndFlush(units);

        int databaseSizeBeforeUpdate = unitsRepository.findAll().size();

        // Update the units using partial update
        Units partialUpdatedUnits = new Units();
        partialUpdatedUnits.setId(units.getId());

        partialUpdatedUnits.description(UPDATED_DESCRIPTION);

        restUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnits.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnits))
            )
            .andExpect(status().isOk());

        // Validate the Units in the database
        List<Units> unitsList = unitsRepository.findAll();
        assertThat(unitsList).hasSize(databaseSizeBeforeUpdate);
        Units testUnits = unitsList.get(unitsList.size() - 1);
        assertThat(testUnits.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateUnitsWithPatch() throws Exception {
        // Initialize the database
        unitsRepository.saveAndFlush(units);

        int databaseSizeBeforeUpdate = unitsRepository.findAll().size();

        // Update the units using partial update
        Units partialUpdatedUnits = new Units();
        partialUpdatedUnits.setId(units.getId());

        partialUpdatedUnits.description(UPDATED_DESCRIPTION);

        restUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUnits.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUnits))
            )
            .andExpect(status().isOk());

        // Validate the Units in the database
        List<Units> unitsList = unitsRepository.findAll();
        assertThat(unitsList).hasSize(databaseSizeBeforeUpdate);
        Units testUnits = unitsList.get(unitsList.size() - 1);
        assertThat(testUnits.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingUnits() throws Exception {
        int databaseSizeBeforeUpdate = unitsRepository.findAll().size();
        units.setId(count.incrementAndGet());

        // Create the Units
        UnitsDTO unitsDTO = unitsMapper.toDto(units);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, unitsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Units in the database
        List<Units> unitsList = unitsRepository.findAll();
        assertThat(unitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUnits() throws Exception {
        int databaseSizeBeforeUpdate = unitsRepository.findAll().size();
        units.setId(count.incrementAndGet());

        // Create the Units
        UnitsDTO unitsDTO = unitsMapper.toDto(units);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(unitsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Units in the database
        List<Units> unitsList = unitsRepository.findAll();
        assertThat(unitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUnits() throws Exception {
        int databaseSizeBeforeUpdate = unitsRepository.findAll().size();
        units.setId(count.incrementAndGet());

        // Create the Units
        UnitsDTO unitsDTO = unitsMapper.toDto(units);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUnitsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(unitsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Units in the database
        List<Units> unitsList = unitsRepository.findAll();
        assertThat(unitsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUnits() throws Exception {
        // Initialize the database
        unitsRepository.saveAndFlush(units);

        int databaseSizeBeforeDelete = unitsRepository.findAll().size();

        // Delete the units
        restUnitsMockMvc
            .perform(delete(ENTITY_API_URL_ID, units.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Units> unitsList = unitsRepository.findAll();
        assertThat(unitsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
