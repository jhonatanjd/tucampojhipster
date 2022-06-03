package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Shopping;
import co.edu.sena.repository.ShoppingRepository;
import co.edu.sena.service.dto.ShoppingDTO;
import co.edu.sena.service.mapper.ShoppingMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ShoppingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShoppingResourceIT {

    private static final String DEFAULT_NAME_PRODUCTS = "AAAAAAAAAA";
    private static final String UPDATED_NAME_PRODUCTS = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final String DEFAULT_DIRECTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTIONS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_ORDERDATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ORDERDATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATE_OF_DELIVERY = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_OF_DELIVERY = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/shoppings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private ShoppingMapper shoppingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShoppingMockMvc;

    private Shopping shopping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shopping createEntity(EntityManager em) {
        Shopping shopping = new Shopping()
            .nameProducts(DEFAULT_NAME_PRODUCTS)
            .amount(DEFAULT_AMOUNT)
            .directions(DEFAULT_DIRECTIONS)
            .city(DEFAULT_CITY)
            .orderdate(DEFAULT_ORDERDATE)
            .dateOfDelivery(DEFAULT_DATE_OF_DELIVERY);
        return shopping;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shopping createUpdatedEntity(EntityManager em) {
        Shopping shopping = new Shopping()
            .nameProducts(UPDATED_NAME_PRODUCTS)
            .amount(UPDATED_AMOUNT)
            .directions(UPDATED_DIRECTIONS)
            .city(UPDATED_CITY)
            .orderdate(UPDATED_ORDERDATE)
            .dateOfDelivery(UPDATED_DATE_OF_DELIVERY);
        return shopping;
    }

    @BeforeEach
    public void initTest() {
        shopping = createEntity(em);
    }

    @Test
    @Transactional
    void createShopping() throws Exception {
        int databaseSizeBeforeCreate = shoppingRepository.findAll().size();
        // Create the Shopping
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);
        restShoppingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isCreated());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeCreate + 1);
        Shopping testShopping = shoppingList.get(shoppingList.size() - 1);
        assertThat(testShopping.getNameProducts()).isEqualTo(DEFAULT_NAME_PRODUCTS);
        assertThat(testShopping.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testShopping.getDirections()).isEqualTo(DEFAULT_DIRECTIONS);
        assertThat(testShopping.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testShopping.getOrderdate()).isEqualTo(DEFAULT_ORDERDATE);
        assertThat(testShopping.getDateOfDelivery()).isEqualTo(DEFAULT_DATE_OF_DELIVERY);
    }

    @Test
    @Transactional
    void createShoppingWithExistingId() throws Exception {
        // Create the Shopping with an existing ID
        shopping.setId(1L);
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        int databaseSizeBeforeCreate = shoppingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShoppingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameProductsIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingRepository.findAll().size();
        // set the field null
        shopping.setNameProducts(null);

        // Create the Shopping, which fails.
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        restShoppingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isBadRequest());

        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingRepository.findAll().size();
        // set the field null
        shopping.setAmount(null);

        // Create the Shopping, which fails.
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        restShoppingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isBadRequest());

        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDirectionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingRepository.findAll().size();
        // set the field null
        shopping.setDirections(null);

        // Create the Shopping, which fails.
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        restShoppingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isBadRequest());

        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingRepository.findAll().size();
        // set the field null
        shopping.setCity(null);

        // Create the Shopping, which fails.
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        restShoppingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isBadRequest());

        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOrderdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingRepository.findAll().size();
        // set the field null
        shopping.setOrderdate(null);

        // Create the Shopping, which fails.
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        restShoppingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isBadRequest());

        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateOfDeliveryIsRequired() throws Exception {
        int databaseSizeBeforeTest = shoppingRepository.findAll().size();
        // set the field null
        shopping.setDateOfDelivery(null);

        // Create the Shopping, which fails.
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        restShoppingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isBadRequest());

        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShoppings() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);

        // Get all the shoppingList
        restShoppingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shopping.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameProducts").value(hasItem(DEFAULT_NAME_PRODUCTS)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].directions").value(hasItem(DEFAULT_DIRECTIONS)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].orderdate").value(hasItem(DEFAULT_ORDERDATE.toString())))
            .andExpect(jsonPath("$.[*].dateOfDelivery").value(hasItem(DEFAULT_DATE_OF_DELIVERY.toString())));
    }

    @Test
    @Transactional
    void getShopping() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);

        // Get the shopping
        restShoppingMockMvc
            .perform(get(ENTITY_API_URL_ID, shopping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shopping.getId().intValue()))
            .andExpect(jsonPath("$.nameProducts").value(DEFAULT_NAME_PRODUCTS))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.directions").value(DEFAULT_DIRECTIONS))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.orderdate").value(DEFAULT_ORDERDATE.toString()))
            .andExpect(jsonPath("$.dateOfDelivery").value(DEFAULT_DATE_OF_DELIVERY.toString()));
    }

    @Test
    @Transactional
    void getNonExistingShopping() throws Exception {
        // Get the shopping
        restShoppingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewShopping() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);

        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();

        // Update the shopping
        Shopping updatedShopping = shoppingRepository.findById(shopping.getId()).get();
        // Disconnect from session so that the updates on updatedShopping are not directly saved in db
        em.detach(updatedShopping);
        updatedShopping
            .nameProducts(UPDATED_NAME_PRODUCTS)
            .amount(UPDATED_AMOUNT)
            .directions(UPDATED_DIRECTIONS)
            .city(UPDATED_CITY)
            .orderdate(UPDATED_ORDERDATE)
            .dateOfDelivery(UPDATED_DATE_OF_DELIVERY);
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(updatedShopping);

        restShoppingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shoppingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingDTO))
            )
            .andExpect(status().isOk());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate);
        Shopping testShopping = shoppingList.get(shoppingList.size() - 1);
        assertThat(testShopping.getNameProducts()).isEqualTo(UPDATED_NAME_PRODUCTS);
        assertThat(testShopping.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testShopping.getDirections()).isEqualTo(UPDATED_DIRECTIONS);
        assertThat(testShopping.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testShopping.getOrderdate()).isEqualTo(UPDATED_ORDERDATE);
        assertThat(testShopping.getDateOfDelivery()).isEqualTo(UPDATED_DATE_OF_DELIVERY);
    }

    @Test
    @Transactional
    void putNonExistingShopping() throws Exception {
        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();
        shopping.setId(count.incrementAndGet());

        // Create the Shopping
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shoppingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShopping() throws Exception {
        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();
        shopping.setId(count.incrementAndGet());

        // Create the Shopping
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shoppingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShopping() throws Exception {
        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();
        shopping.setId(count.incrementAndGet());

        // Create the Shopping
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shoppingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShoppingWithPatch() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);

        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();

        // Update the shopping using partial update
        Shopping partialUpdatedShopping = new Shopping();
        partialUpdatedShopping.setId(shopping.getId());

        partialUpdatedShopping.nameProducts(UPDATED_NAME_PRODUCTS).amount(UPDATED_AMOUNT).dateOfDelivery(UPDATED_DATE_OF_DELIVERY);

        restShoppingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShopping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShopping))
            )
            .andExpect(status().isOk());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate);
        Shopping testShopping = shoppingList.get(shoppingList.size() - 1);
        assertThat(testShopping.getNameProducts()).isEqualTo(UPDATED_NAME_PRODUCTS);
        assertThat(testShopping.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testShopping.getDirections()).isEqualTo(DEFAULT_DIRECTIONS);
        assertThat(testShopping.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testShopping.getOrderdate()).isEqualTo(DEFAULT_ORDERDATE);
        assertThat(testShopping.getDateOfDelivery()).isEqualTo(UPDATED_DATE_OF_DELIVERY);
    }

    @Test
    @Transactional
    void fullUpdateShoppingWithPatch() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);

        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();

        // Update the shopping using partial update
        Shopping partialUpdatedShopping = new Shopping();
        partialUpdatedShopping.setId(shopping.getId());

        partialUpdatedShopping
            .nameProducts(UPDATED_NAME_PRODUCTS)
            .amount(UPDATED_AMOUNT)
            .directions(UPDATED_DIRECTIONS)
            .city(UPDATED_CITY)
            .orderdate(UPDATED_ORDERDATE)
            .dateOfDelivery(UPDATED_DATE_OF_DELIVERY);

        restShoppingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShopping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShopping))
            )
            .andExpect(status().isOk());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate);
        Shopping testShopping = shoppingList.get(shoppingList.size() - 1);
        assertThat(testShopping.getNameProducts()).isEqualTo(UPDATED_NAME_PRODUCTS);
        assertThat(testShopping.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testShopping.getDirections()).isEqualTo(UPDATED_DIRECTIONS);
        assertThat(testShopping.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testShopping.getOrderdate()).isEqualTo(UPDATED_ORDERDATE);
        assertThat(testShopping.getDateOfDelivery()).isEqualTo(UPDATED_DATE_OF_DELIVERY);
    }

    @Test
    @Transactional
    void patchNonExistingShopping() throws Exception {
        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();
        shopping.setId(count.incrementAndGet());

        // Create the Shopping
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShoppingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shoppingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shoppingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShopping() throws Exception {
        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();
        shopping.setId(count.incrementAndGet());

        // Create the Shopping
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shoppingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShopping() throws Exception {
        int databaseSizeBeforeUpdate = shoppingRepository.findAll().size();
        shopping.setId(count.incrementAndGet());

        // Create the Shopping
        ShoppingDTO shoppingDTO = shoppingMapper.toDto(shopping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShoppingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(shoppingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Shopping in the database
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShopping() throws Exception {
        // Initialize the database
        shoppingRepository.saveAndFlush(shopping);

        int databaseSizeBeforeDelete = shoppingRepository.findAll().size();

        // Delete the shopping
        restShoppingMockMvc
            .perform(delete(ENTITY_API_URL_ID, shopping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shopping> shoppingList = shoppingRepository.findAll();
        assertThat(shoppingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
