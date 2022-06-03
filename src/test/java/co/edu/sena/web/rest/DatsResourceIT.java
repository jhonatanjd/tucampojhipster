package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Dats;
import co.edu.sena.repository.DatsRepository;
import co.edu.sena.service.dto.DatsDTO;
import co.edu.sena.service.mapper.DatsMapper;
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
 * Integration tests for the {@link DatsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DatsResourceIT {

    private static final String DEFAULT_NAMES = "AAAAAAAAAA";
    private static final String UPDATED_NAMES = "BBBBBBBBBB";

    private static final String DEFAULT_SURNAMES = "AAAAAAAAAA";
    private static final String UPDATED_SURNAMES = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTIONS = "BBBBBBBBBB";

    private static final Integer DEFAULT_TELEPHONE = 1;
    private static final Integer UPDATED_TELEPHONE = 2;

    private static final Integer DEFAULT_CELL_PHONE = 1;
    private static final Integer UPDATED_CELL_PHONE = 2;

    private static final String DEFAULT_MAIL = "AAAAAAAAAA";
    private static final String UPDATED_MAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dats";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DatsRepository datsRepository;

    @Autowired
    private DatsMapper datsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDatsMockMvc;

    private Dats dats;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dats createEntity(EntityManager em) {
        Dats dats = new Dats()
            .names(DEFAULT_NAMES)
            .surnames(DEFAULT_SURNAMES)
            .directions(DEFAULT_DIRECTIONS)
            .telephone(DEFAULT_TELEPHONE)
            .cellPhone(DEFAULT_CELL_PHONE)
            .mail(DEFAULT_MAIL)
            .city(DEFAULT_CITY);
        return dats;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dats createUpdatedEntity(EntityManager em) {
        Dats dats = new Dats()
            .names(UPDATED_NAMES)
            .surnames(UPDATED_SURNAMES)
            .directions(UPDATED_DIRECTIONS)
            .telephone(UPDATED_TELEPHONE)
            .cellPhone(UPDATED_CELL_PHONE)
            .mail(UPDATED_MAIL)
            .city(UPDATED_CITY);
        return dats;
    }

    @BeforeEach
    public void initTest() {
        dats = createEntity(em);
    }

    @Test
    @Transactional
    void createDats() throws Exception {
        int databaseSizeBeforeCreate = datsRepository.findAll().size();
        // Create the Dats
        DatsDTO datsDTO = datsMapper.toDto(dats);
        restDatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(datsDTO)))
            .andExpect(status().isCreated());

        // Validate the Dats in the database
        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeCreate + 1);
        Dats testDats = datsList.get(datsList.size() - 1);
        assertThat(testDats.getNames()).isEqualTo(DEFAULT_NAMES);
        assertThat(testDats.getSurnames()).isEqualTo(DEFAULT_SURNAMES);
        assertThat(testDats.getDirections()).isEqualTo(DEFAULT_DIRECTIONS);
        assertThat(testDats.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testDats.getCellPhone()).isEqualTo(DEFAULT_CELL_PHONE);
        assertThat(testDats.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testDats.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    @Transactional
    void createDatsWithExistingId() throws Exception {
        // Create the Dats with an existing ID
        dats.setId(1L);
        DatsDTO datsDTO = datsMapper.toDto(dats);

        int databaseSizeBeforeCreate = datsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(datsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Dats in the database
        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNamesIsRequired() throws Exception {
        int databaseSizeBeforeTest = datsRepository.findAll().size();
        // set the field null
        dats.setNames(null);

        // Create the Dats, which fails.
        DatsDTO datsDTO = datsMapper.toDto(dats);

        restDatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(datsDTO)))
            .andExpect(status().isBadRequest());

        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSurnamesIsRequired() throws Exception {
        int databaseSizeBeforeTest = datsRepository.findAll().size();
        // set the field null
        dats.setSurnames(null);

        // Create the Dats, which fails.
        DatsDTO datsDTO = datsMapper.toDto(dats);

        restDatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(datsDTO)))
            .andExpect(status().isBadRequest());

        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDirectionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = datsRepository.findAll().size();
        // set the field null
        dats.setDirections(null);

        // Create the Dats, which fails.
        DatsDTO datsDTO = datsMapper.toDto(dats);

        restDatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(datsDTO)))
            .andExpect(status().isBadRequest());

        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCellPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = datsRepository.findAll().size();
        // set the field null
        dats.setCellPhone(null);

        // Create the Dats, which fails.
        DatsDTO datsDTO = datsMapper.toDto(dats);

        restDatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(datsDTO)))
            .andExpect(status().isBadRequest());

        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMailIsRequired() throws Exception {
        int databaseSizeBeforeTest = datsRepository.findAll().size();
        // set the field null
        dats.setMail(null);

        // Create the Dats, which fails.
        DatsDTO datsDTO = datsMapper.toDto(dats);

        restDatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(datsDTO)))
            .andExpect(status().isBadRequest());

        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = datsRepository.findAll().size();
        // set the field null
        dats.setCity(null);

        // Create the Dats, which fails.
        DatsDTO datsDTO = datsMapper.toDto(dats);

        restDatsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(datsDTO)))
            .andExpect(status().isBadRequest());

        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDats() throws Exception {
        // Initialize the database
        datsRepository.saveAndFlush(dats);

        // Get all the datsList
        restDatsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dats.getId().intValue())))
            .andExpect(jsonPath("$.[*].names").value(hasItem(DEFAULT_NAMES)))
            .andExpect(jsonPath("$.[*].surnames").value(hasItem(DEFAULT_SURNAMES)))
            .andExpect(jsonPath("$.[*].directions").value(hasItem(DEFAULT_DIRECTIONS)))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE)))
            .andExpect(jsonPath("$.[*].cellPhone").value(hasItem(DEFAULT_CELL_PHONE)))
            .andExpect(jsonPath("$.[*].mail").value(hasItem(DEFAULT_MAIL)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)));
    }

    @Test
    @Transactional
    void getDats() throws Exception {
        // Initialize the database
        datsRepository.saveAndFlush(dats);

        // Get the dats
        restDatsMockMvc
            .perform(get(ENTITY_API_URL_ID, dats.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dats.getId().intValue()))
            .andExpect(jsonPath("$.names").value(DEFAULT_NAMES))
            .andExpect(jsonPath("$.surnames").value(DEFAULT_SURNAMES))
            .andExpect(jsonPath("$.directions").value(DEFAULT_DIRECTIONS))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE))
            .andExpect(jsonPath("$.cellPhone").value(DEFAULT_CELL_PHONE))
            .andExpect(jsonPath("$.mail").value(DEFAULT_MAIL))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY));
    }

    @Test
    @Transactional
    void getNonExistingDats() throws Exception {
        // Get the dats
        restDatsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDats() throws Exception {
        // Initialize the database
        datsRepository.saveAndFlush(dats);

        int databaseSizeBeforeUpdate = datsRepository.findAll().size();

        // Update the dats
        Dats updatedDats = datsRepository.findById(dats.getId()).get();
        // Disconnect from session so that the updates on updatedDats are not directly saved in db
        em.detach(updatedDats);
        updatedDats
            .names(UPDATED_NAMES)
            .surnames(UPDATED_SURNAMES)
            .directions(UPDATED_DIRECTIONS)
            .telephone(UPDATED_TELEPHONE)
            .cellPhone(UPDATED_CELL_PHONE)
            .mail(UPDATED_MAIL)
            .city(UPDATED_CITY);
        DatsDTO datsDTO = datsMapper.toDto(updatedDats);

        restDatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, datsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(datsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Dats in the database
        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeUpdate);
        Dats testDats = datsList.get(datsList.size() - 1);
        assertThat(testDats.getNames()).isEqualTo(UPDATED_NAMES);
        assertThat(testDats.getSurnames()).isEqualTo(UPDATED_SURNAMES);
        assertThat(testDats.getDirections()).isEqualTo(UPDATED_DIRECTIONS);
        assertThat(testDats.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDats.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testDats.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testDats.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    void putNonExistingDats() throws Exception {
        int databaseSizeBeforeUpdate = datsRepository.findAll().size();
        dats.setId(count.incrementAndGet());

        // Create the Dats
        DatsDTO datsDTO = datsMapper.toDto(dats);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, datsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(datsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dats in the database
        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDats() throws Exception {
        int databaseSizeBeforeUpdate = datsRepository.findAll().size();
        dats.setId(count.incrementAndGet());

        // Create the Dats
        DatsDTO datsDTO = datsMapper.toDto(dats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(datsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dats in the database
        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDats() throws Exception {
        int databaseSizeBeforeUpdate = datsRepository.findAll().size();
        dats.setId(count.incrementAndGet());

        // Create the Dats
        DatsDTO datsDTO = datsMapper.toDto(dats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(datsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dats in the database
        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDatsWithPatch() throws Exception {
        // Initialize the database
        datsRepository.saveAndFlush(dats);

        int databaseSizeBeforeUpdate = datsRepository.findAll().size();

        // Update the dats using partial update
        Dats partialUpdatedDats = new Dats();
        partialUpdatedDats.setId(dats.getId());

        partialUpdatedDats.surnames(UPDATED_SURNAMES).telephone(UPDATED_TELEPHONE).cellPhone(UPDATED_CELL_PHONE).city(UPDATED_CITY);

        restDatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDats))
            )
            .andExpect(status().isOk());

        // Validate the Dats in the database
        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeUpdate);
        Dats testDats = datsList.get(datsList.size() - 1);
        assertThat(testDats.getNames()).isEqualTo(DEFAULT_NAMES);
        assertThat(testDats.getSurnames()).isEqualTo(UPDATED_SURNAMES);
        assertThat(testDats.getDirections()).isEqualTo(DEFAULT_DIRECTIONS);
        assertThat(testDats.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDats.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testDats.getMail()).isEqualTo(DEFAULT_MAIL);
        assertThat(testDats.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    void fullUpdateDatsWithPatch() throws Exception {
        // Initialize the database
        datsRepository.saveAndFlush(dats);

        int databaseSizeBeforeUpdate = datsRepository.findAll().size();

        // Update the dats using partial update
        Dats partialUpdatedDats = new Dats();
        partialUpdatedDats.setId(dats.getId());

        partialUpdatedDats
            .names(UPDATED_NAMES)
            .surnames(UPDATED_SURNAMES)
            .directions(UPDATED_DIRECTIONS)
            .telephone(UPDATED_TELEPHONE)
            .cellPhone(UPDATED_CELL_PHONE)
            .mail(UPDATED_MAIL)
            .city(UPDATED_CITY);

        restDatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDats.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDats))
            )
            .andExpect(status().isOk());

        // Validate the Dats in the database
        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeUpdate);
        Dats testDats = datsList.get(datsList.size() - 1);
        assertThat(testDats.getNames()).isEqualTo(UPDATED_NAMES);
        assertThat(testDats.getSurnames()).isEqualTo(UPDATED_SURNAMES);
        assertThat(testDats.getDirections()).isEqualTo(UPDATED_DIRECTIONS);
        assertThat(testDats.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDats.getCellPhone()).isEqualTo(UPDATED_CELL_PHONE);
        assertThat(testDats.getMail()).isEqualTo(UPDATED_MAIL);
        assertThat(testDats.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    void patchNonExistingDats() throws Exception {
        int databaseSizeBeforeUpdate = datsRepository.findAll().size();
        dats.setId(count.incrementAndGet());

        // Create the Dats
        DatsDTO datsDTO = datsMapper.toDto(dats);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, datsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(datsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dats in the database
        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDats() throws Exception {
        int databaseSizeBeforeUpdate = datsRepository.findAll().size();
        dats.setId(count.incrementAndGet());

        // Create the Dats
        DatsDTO datsDTO = datsMapper.toDto(dats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(datsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dats in the database
        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDats() throws Exception {
        int databaseSizeBeforeUpdate = datsRepository.findAll().size();
        dats.setId(count.incrementAndGet());

        // Create the Dats
        DatsDTO datsDTO = datsMapper.toDto(dats);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDatsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(datsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dats in the database
        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDats() throws Exception {
        // Initialize the database
        datsRepository.saveAndFlush(dats);

        int databaseSizeBeforeDelete = datsRepository.findAll().size();

        // Delete the dats
        restDatsMockMvc
            .perform(delete(ENTITY_API_URL_ID, dats.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dats> datsList = datsRepository.findAll();
        assertThat(datsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
