package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.OrderDetai;
import co.edu.sena.repository.OrderDetaiRepository;
import co.edu.sena.service.dto.OrderDetaiDTO;
import co.edu.sena.service.mapper.OrderDetaiMapper;
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
 * Integration tests for the {@link OrderDetaiResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrderDetaiResourceIT {

    private static final String ENTITY_API_URL = "/api/order-detais";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OrderDetaiRepository orderDetaiRepository;

    @Autowired
    private OrderDetaiMapper orderDetaiMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderDetaiMockMvc;

    private OrderDetai orderDetai;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderDetai createEntity(EntityManager em) {
        OrderDetai orderDetai = new OrderDetai();
        return orderDetai;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderDetai createUpdatedEntity(EntityManager em) {
        OrderDetai orderDetai = new OrderDetai();
        return orderDetai;
    }

    @BeforeEach
    public void initTest() {
        orderDetai = createEntity(em);
    }

    @Test
    @Transactional
    void createOrderDetai() throws Exception {
        int databaseSizeBeforeCreate = orderDetaiRepository.findAll().size();
        // Create the OrderDetai
        OrderDetaiDTO orderDetaiDTO = orderDetaiMapper.toDto(orderDetai);
        restOrderDetaiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDetaiDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderDetai in the database
        List<OrderDetai> orderDetaiList = orderDetaiRepository.findAll();
        assertThat(orderDetaiList).hasSize(databaseSizeBeforeCreate + 1);
        OrderDetai testOrderDetai = orderDetaiList.get(orderDetaiList.size() - 1);
    }

    @Test
    @Transactional
    void createOrderDetaiWithExistingId() throws Exception {
        // Create the OrderDetai with an existing ID
        orderDetai.setId(1L);
        OrderDetaiDTO orderDetaiDTO = orderDetaiMapper.toDto(orderDetai);

        int databaseSizeBeforeCreate = orderDetaiRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderDetaiMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDetaiDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderDetai in the database
        List<OrderDetai> orderDetaiList = orderDetaiRepository.findAll();
        assertThat(orderDetaiList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOrderDetais() throws Exception {
        // Initialize the database
        orderDetaiRepository.saveAndFlush(orderDetai);

        // Get all the orderDetaiList
        restOrderDetaiMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderDetai.getId().intValue())));
    }

    @Test
    @Transactional
    void getOrderDetai() throws Exception {
        // Initialize the database
        orderDetaiRepository.saveAndFlush(orderDetai);

        // Get the orderDetai
        restOrderDetaiMockMvc
            .perform(get(ENTITY_API_URL_ID, orderDetai.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderDetai.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingOrderDetai() throws Exception {
        // Get the orderDetai
        restOrderDetaiMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOrderDetai() throws Exception {
        // Initialize the database
        orderDetaiRepository.saveAndFlush(orderDetai);

        int databaseSizeBeforeUpdate = orderDetaiRepository.findAll().size();

        // Update the orderDetai
        OrderDetai updatedOrderDetai = orderDetaiRepository.findById(orderDetai.getId()).get();
        // Disconnect from session so that the updates on updatedOrderDetai are not directly saved in db
        em.detach(updatedOrderDetai);
        OrderDetaiDTO orderDetaiDTO = orderDetaiMapper.toDto(updatedOrderDetai);

        restOrderDetaiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderDetaiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDetaiDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrderDetai in the database
        List<OrderDetai> orderDetaiList = orderDetaiRepository.findAll();
        assertThat(orderDetaiList).hasSize(databaseSizeBeforeUpdate);
        OrderDetai testOrderDetai = orderDetaiList.get(orderDetaiList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingOrderDetai() throws Exception {
        int databaseSizeBeforeUpdate = orderDetaiRepository.findAll().size();
        orderDetai.setId(count.incrementAndGet());

        // Create the OrderDetai
        OrderDetaiDTO orderDetaiDTO = orderDetaiMapper.toDto(orderDetai);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderDetaiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, orderDetaiDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDetaiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderDetai in the database
        List<OrderDetai> orderDetaiList = orderDetaiRepository.findAll();
        assertThat(orderDetaiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrderDetai() throws Exception {
        int databaseSizeBeforeUpdate = orderDetaiRepository.findAll().size();
        orderDetai.setId(count.incrementAndGet());

        // Create the OrderDetai
        OrderDetaiDTO orderDetaiDTO = orderDetaiMapper.toDto(orderDetai);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderDetaiMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(orderDetaiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderDetai in the database
        List<OrderDetai> orderDetaiList = orderDetaiRepository.findAll();
        assertThat(orderDetaiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrderDetai() throws Exception {
        int databaseSizeBeforeUpdate = orderDetaiRepository.findAll().size();
        orderDetai.setId(count.incrementAndGet());

        // Create the OrderDetai
        OrderDetaiDTO orderDetaiDTO = orderDetaiMapper.toDto(orderDetai);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderDetaiMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(orderDetaiDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderDetai in the database
        List<OrderDetai> orderDetaiList = orderDetaiRepository.findAll();
        assertThat(orderDetaiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrderDetaiWithPatch() throws Exception {
        // Initialize the database
        orderDetaiRepository.saveAndFlush(orderDetai);

        int databaseSizeBeforeUpdate = orderDetaiRepository.findAll().size();

        // Update the orderDetai using partial update
        OrderDetai partialUpdatedOrderDetai = new OrderDetai();
        partialUpdatedOrderDetai.setId(orderDetai.getId());

        restOrderDetaiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderDetai.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderDetai))
            )
            .andExpect(status().isOk());

        // Validate the OrderDetai in the database
        List<OrderDetai> orderDetaiList = orderDetaiRepository.findAll();
        assertThat(orderDetaiList).hasSize(databaseSizeBeforeUpdate);
        OrderDetai testOrderDetai = orderDetaiList.get(orderDetaiList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateOrderDetaiWithPatch() throws Exception {
        // Initialize the database
        orderDetaiRepository.saveAndFlush(orderDetai);

        int databaseSizeBeforeUpdate = orderDetaiRepository.findAll().size();

        // Update the orderDetai using partial update
        OrderDetai partialUpdatedOrderDetai = new OrderDetai();
        partialUpdatedOrderDetai.setId(orderDetai.getId());

        restOrderDetaiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrderDetai.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOrderDetai))
            )
            .andExpect(status().isOk());

        // Validate the OrderDetai in the database
        List<OrderDetai> orderDetaiList = orderDetaiRepository.findAll();
        assertThat(orderDetaiList).hasSize(databaseSizeBeforeUpdate);
        OrderDetai testOrderDetai = orderDetaiList.get(orderDetaiList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingOrderDetai() throws Exception {
        int databaseSizeBeforeUpdate = orderDetaiRepository.findAll().size();
        orderDetai.setId(count.incrementAndGet());

        // Create the OrderDetai
        OrderDetaiDTO orderDetaiDTO = orderDetaiMapper.toDto(orderDetai);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderDetaiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, orderDetaiDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderDetaiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderDetai in the database
        List<OrderDetai> orderDetaiList = orderDetaiRepository.findAll();
        assertThat(orderDetaiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrderDetai() throws Exception {
        int databaseSizeBeforeUpdate = orderDetaiRepository.findAll().size();
        orderDetai.setId(count.incrementAndGet());

        // Create the OrderDetai
        OrderDetaiDTO orderDetaiDTO = orderDetaiMapper.toDto(orderDetai);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderDetaiMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(orderDetaiDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrderDetai in the database
        List<OrderDetai> orderDetaiList = orderDetaiRepository.findAll();
        assertThat(orderDetaiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrderDetai() throws Exception {
        int databaseSizeBeforeUpdate = orderDetaiRepository.findAll().size();
        orderDetai.setId(count.incrementAndGet());

        // Create the OrderDetai
        OrderDetaiDTO orderDetaiDTO = orderDetaiMapper.toDto(orderDetai);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrderDetaiMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(orderDetaiDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrderDetai in the database
        List<OrderDetai> orderDetaiList = orderDetaiRepository.findAll();
        assertThat(orderDetaiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrderDetai() throws Exception {
        // Initialize the database
        orderDetaiRepository.saveAndFlush(orderDetai);

        int databaseSizeBeforeDelete = orderDetaiRepository.findAll().size();

        // Delete the orderDetai
        restOrderDetaiMockMvc
            .perform(delete(ENTITY_API_URL_ID, orderDetai.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderDetai> orderDetaiList = orderDetaiRepository.findAll();
        assertThat(orderDetaiList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
