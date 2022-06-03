package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.DocumenType;
import co.edu.sena.domain.enumeration.State;
import co.edu.sena.repository.DocumenTypeRepository;
import co.edu.sena.service.dto.DocumenTypeDTO;
import co.edu.sena.service.mapper.DocumenTypeMapper;
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
 * Integration tests for the {@link DocumenTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DocumenTypeResourceIT {

    private static final String DEFAULT_INITIALS = "AAAAAAAAAA";
    private static final String UPDATED_INITIALS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPEDOCUMENT = "AAAAAAAAAA";
    private static final String UPDATED_TYPEDOCUMENT = "BBBBBBBBBB";

    private static final State DEFAULT_STATE_DOCUMENT = State.ACTIVE;
    private static final State UPDATED_STATE_DOCUMENT = State.INACTIVE;

    private static final String ENTITY_API_URL = "/api/documen-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DocumenTypeRepository documenTypeRepository;

    @Autowired
    private DocumenTypeMapper documenTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDocumenTypeMockMvc;

    private DocumenType documenType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumenType createEntity(EntityManager em) {
        DocumenType documenType = new DocumenType()
            .initials(DEFAULT_INITIALS)
            .typedocument(DEFAULT_TYPEDOCUMENT)
            .stateDocument(DEFAULT_STATE_DOCUMENT);
        return documenType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DocumenType createUpdatedEntity(EntityManager em) {
        DocumenType documenType = new DocumenType()
            .initials(UPDATED_INITIALS)
            .typedocument(UPDATED_TYPEDOCUMENT)
            .stateDocument(UPDATED_STATE_DOCUMENT);
        return documenType;
    }

    @BeforeEach
    public void initTest() {
        documenType = createEntity(em);
    }

    @Test
    @Transactional
    void createDocumenType() throws Exception {
        int databaseSizeBeforeCreate = documenTypeRepository.findAll().size();
        // Create the DocumenType
        DocumenTypeDTO documenTypeDTO = documenTypeMapper.toDto(documenType);
        restDocumenTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documenTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DocumenType in the database
        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DocumenType testDocumenType = documenTypeList.get(documenTypeList.size() - 1);
        assertThat(testDocumenType.getInitials()).isEqualTo(DEFAULT_INITIALS);
        assertThat(testDocumenType.getTypedocument()).isEqualTo(DEFAULT_TYPEDOCUMENT);
        assertThat(testDocumenType.getStateDocument()).isEqualTo(DEFAULT_STATE_DOCUMENT);
    }

    @Test
    @Transactional
    void createDocumenTypeWithExistingId() throws Exception {
        // Create the DocumenType with an existing ID
        documenType.setId(1L);
        DocumenTypeDTO documenTypeDTO = documenTypeMapper.toDto(documenType);

        int databaseSizeBeforeCreate = documenTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocumenTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documenTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumenType in the database
        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkInitialsIsRequired() throws Exception {
        int databaseSizeBeforeTest = documenTypeRepository.findAll().size();
        // set the field null
        documenType.setInitials(null);

        // Create the DocumenType, which fails.
        DocumenTypeDTO documenTypeDTO = documenTypeMapper.toDto(documenType);

        restDocumenTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documenTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypedocumentIsRequired() throws Exception {
        int databaseSizeBeforeTest = documenTypeRepository.findAll().size();
        // set the field null
        documenType.setTypedocument(null);

        // Create the DocumenType, which fails.
        DocumenTypeDTO documenTypeDTO = documenTypeMapper.toDto(documenType);

        restDocumenTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documenTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDocumenTypes() throws Exception {
        // Initialize the database
        documenTypeRepository.saveAndFlush(documenType);

        // Get all the documenTypeList
        restDocumenTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(documenType.getId().intValue())))
            .andExpect(jsonPath("$.[*].initials").value(hasItem(DEFAULT_INITIALS)))
            .andExpect(jsonPath("$.[*].typedocument").value(hasItem(DEFAULT_TYPEDOCUMENT)))
            .andExpect(jsonPath("$.[*].stateDocument").value(hasItem(DEFAULT_STATE_DOCUMENT.toString())));
    }

    @Test
    @Transactional
    void getDocumenType() throws Exception {
        // Initialize the database
        documenTypeRepository.saveAndFlush(documenType);

        // Get the documenType
        restDocumenTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, documenType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(documenType.getId().intValue()))
            .andExpect(jsonPath("$.initials").value(DEFAULT_INITIALS))
            .andExpect(jsonPath("$.typedocument").value(DEFAULT_TYPEDOCUMENT))
            .andExpect(jsonPath("$.stateDocument").value(DEFAULT_STATE_DOCUMENT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDocumenType() throws Exception {
        // Get the documenType
        restDocumenTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDocumenType() throws Exception {
        // Initialize the database
        documenTypeRepository.saveAndFlush(documenType);

        int databaseSizeBeforeUpdate = documenTypeRepository.findAll().size();

        // Update the documenType
        DocumenType updatedDocumenType = documenTypeRepository.findById(documenType.getId()).get();
        // Disconnect from session so that the updates on updatedDocumenType are not directly saved in db
        em.detach(updatedDocumenType);
        updatedDocumenType.initials(UPDATED_INITIALS).typedocument(UPDATED_TYPEDOCUMENT).stateDocument(UPDATED_STATE_DOCUMENT);
        DocumenTypeDTO documenTypeDTO = documenTypeMapper.toDto(updatedDocumenType);

        restDocumenTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documenTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documenTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the DocumenType in the database
        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeUpdate);
        DocumenType testDocumenType = documenTypeList.get(documenTypeList.size() - 1);
        assertThat(testDocumenType.getInitials()).isEqualTo(UPDATED_INITIALS);
        assertThat(testDocumenType.getTypedocument()).isEqualTo(UPDATED_TYPEDOCUMENT);
        assertThat(testDocumenType.getStateDocument()).isEqualTo(UPDATED_STATE_DOCUMENT);
    }

    @Test
    @Transactional
    void putNonExistingDocumenType() throws Exception {
        int databaseSizeBeforeUpdate = documenTypeRepository.findAll().size();
        documenType.setId(count.incrementAndGet());

        // Create the DocumenType
        DocumenTypeDTO documenTypeDTO = documenTypeMapper.toDto(documenType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumenTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, documenTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documenTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumenType in the database
        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDocumenType() throws Exception {
        int databaseSizeBeforeUpdate = documenTypeRepository.findAll().size();
        documenType.setId(count.incrementAndGet());

        // Create the DocumenType
        DocumenTypeDTO documenTypeDTO = documenTypeMapper.toDto(documenType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumenTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(documenTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumenType in the database
        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDocumenType() throws Exception {
        int databaseSizeBeforeUpdate = documenTypeRepository.findAll().size();
        documenType.setId(count.incrementAndGet());

        // Create the DocumenType
        DocumenTypeDTO documenTypeDTO = documenTypeMapper.toDto(documenType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumenTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(documenTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumenType in the database
        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDocumenTypeWithPatch() throws Exception {
        // Initialize the database
        documenTypeRepository.saveAndFlush(documenType);

        int databaseSizeBeforeUpdate = documenTypeRepository.findAll().size();

        // Update the documenType using partial update
        DocumenType partialUpdatedDocumenType = new DocumenType();
        partialUpdatedDocumenType.setId(documenType.getId());

        partialUpdatedDocumenType.initials(UPDATED_INITIALS).stateDocument(UPDATED_STATE_DOCUMENT);

        restDocumenTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumenType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumenType))
            )
            .andExpect(status().isOk());

        // Validate the DocumenType in the database
        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeUpdate);
        DocumenType testDocumenType = documenTypeList.get(documenTypeList.size() - 1);
        assertThat(testDocumenType.getInitials()).isEqualTo(UPDATED_INITIALS);
        assertThat(testDocumenType.getTypedocument()).isEqualTo(DEFAULT_TYPEDOCUMENT);
        assertThat(testDocumenType.getStateDocument()).isEqualTo(UPDATED_STATE_DOCUMENT);
    }

    @Test
    @Transactional
    void fullUpdateDocumenTypeWithPatch() throws Exception {
        // Initialize the database
        documenTypeRepository.saveAndFlush(documenType);

        int databaseSizeBeforeUpdate = documenTypeRepository.findAll().size();

        // Update the documenType using partial update
        DocumenType partialUpdatedDocumenType = new DocumenType();
        partialUpdatedDocumenType.setId(documenType.getId());

        partialUpdatedDocumenType.initials(UPDATED_INITIALS).typedocument(UPDATED_TYPEDOCUMENT).stateDocument(UPDATED_STATE_DOCUMENT);

        restDocumenTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDocumenType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDocumenType))
            )
            .andExpect(status().isOk());

        // Validate the DocumenType in the database
        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeUpdate);
        DocumenType testDocumenType = documenTypeList.get(documenTypeList.size() - 1);
        assertThat(testDocumenType.getInitials()).isEqualTo(UPDATED_INITIALS);
        assertThat(testDocumenType.getTypedocument()).isEqualTo(UPDATED_TYPEDOCUMENT);
        assertThat(testDocumenType.getStateDocument()).isEqualTo(UPDATED_STATE_DOCUMENT);
    }

    @Test
    @Transactional
    void patchNonExistingDocumenType() throws Exception {
        int databaseSizeBeforeUpdate = documenTypeRepository.findAll().size();
        documenType.setId(count.incrementAndGet());

        // Create the DocumenType
        DocumenTypeDTO documenTypeDTO = documenTypeMapper.toDto(documenType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocumenTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, documenTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documenTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumenType in the database
        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDocumenType() throws Exception {
        int databaseSizeBeforeUpdate = documenTypeRepository.findAll().size();
        documenType.setId(count.incrementAndGet());

        // Create the DocumenType
        DocumenTypeDTO documenTypeDTO = documenTypeMapper.toDto(documenType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumenTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(documenTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DocumenType in the database
        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDocumenType() throws Exception {
        int databaseSizeBeforeUpdate = documenTypeRepository.findAll().size();
        documenType.setId(count.incrementAndGet());

        // Create the DocumenType
        DocumenTypeDTO documenTypeDTO = documenTypeMapper.toDto(documenType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDocumenTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(documenTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DocumenType in the database
        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDocumenType() throws Exception {
        // Initialize the database
        documenTypeRepository.saveAndFlush(documenType);

        int databaseSizeBeforeDelete = documenTypeRepository.findAll().size();

        // Delete the documenType
        restDocumenTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, documenType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DocumenType> documenTypeList = documenTypeRepository.findAll();
        assertThat(documenTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
