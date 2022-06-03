package co.edu.sena.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.edu.sena.IntegrationTest;
import co.edu.sena.domain.Sale;
import co.edu.sena.domain.enumeration.Transportations;
import co.edu.sena.repository.SaleRepository;
import co.edu.sena.service.dto.SaleDTO;
import co.edu.sena.service.mapper.SaleMapper;
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
 * Integration tests for the {@link SaleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SaleResourceIT {

    private static final String DEFAULT_NAME_PRODUCTS = "AAAAAAAAAA";
    private static final String UPDATED_NAME_PRODUCTS = "BBBBBBBBBB";

    private static final Double DEFAULT_AMOUNT_KILO = 1D;
    private static final Double UPDATED_AMOUNT_KILO = 2D;

    private static final Double DEFAULT_PRICE_KILO = 1D;
    private static final Double UPDATED_PRICE_KILO = 2D;

    private static final Double DEFAULT_PRICE_TOTAL = 1D;
    private static final Double UPDATED_PRICE_TOTAL = 2D;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_AVAILABLE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AVAILABLE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Transportations DEFAULT_STATE_TRANSPORTATIONS = Transportations.NO;
    private static final Transportations UPDATED_STATE_TRANSPORTATIONS = Transportations.SI;

    private static final String DEFAULT_DESCRIPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sales";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleMapper saleMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaleMockMvc;

    private Sale sale;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sale createEntity(EntityManager em) {
        Sale sale = new Sale()
            .nameProducts(DEFAULT_NAME_PRODUCTS)
            .amountKilo(DEFAULT_AMOUNT_KILO)
            .priceKilo(DEFAULT_PRICE_KILO)
            .priceTotal(DEFAULT_PRICE_TOTAL)
            .city(DEFAULT_CITY)
            .availableDate(DEFAULT_AVAILABLE_DATE)
            .stateTransportations(DEFAULT_STATE_TRANSPORTATIONS)
            .descriptions(DEFAULT_DESCRIPTIONS);
        return sale;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sale createUpdatedEntity(EntityManager em) {
        Sale sale = new Sale()
            .nameProducts(UPDATED_NAME_PRODUCTS)
            .amountKilo(UPDATED_AMOUNT_KILO)
            .priceKilo(UPDATED_PRICE_KILO)
            .priceTotal(UPDATED_PRICE_TOTAL)
            .city(UPDATED_CITY)
            .availableDate(UPDATED_AVAILABLE_DATE)
            .stateTransportations(UPDATED_STATE_TRANSPORTATIONS)
            .descriptions(UPDATED_DESCRIPTIONS);
        return sale;
    }

    @BeforeEach
    public void initTest() {
        sale = createEntity(em);
    }

    @Test
    @Transactional
    void createSale() throws Exception {
        int databaseSizeBeforeCreate = saleRepository.findAll().size();
        // Create the Sale
        SaleDTO saleDTO = saleMapper.toDto(sale);
        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isCreated());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeCreate + 1);
        Sale testSale = saleList.get(saleList.size() - 1);
        assertThat(testSale.getNameProducts()).isEqualTo(DEFAULT_NAME_PRODUCTS);
        assertThat(testSale.getAmountKilo()).isEqualTo(DEFAULT_AMOUNT_KILO);
        assertThat(testSale.getPriceKilo()).isEqualTo(DEFAULT_PRICE_KILO);
        assertThat(testSale.getPriceTotal()).isEqualTo(DEFAULT_PRICE_TOTAL);
        assertThat(testSale.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testSale.getAvailableDate()).isEqualTo(DEFAULT_AVAILABLE_DATE);
        assertThat(testSale.getStateTransportations()).isEqualTo(DEFAULT_STATE_TRANSPORTATIONS);
        assertThat(testSale.getDescriptions()).isEqualTo(DEFAULT_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void createSaleWithExistingId() throws Exception {
        // Create the Sale with an existing ID
        sale.setId(1L);
        SaleDTO saleDTO = saleMapper.toDto(sale);

        int databaseSizeBeforeCreate = saleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameProductsIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleRepository.findAll().size();
        // set the field null
        sale.setNameProducts(null);

        // Create the Sale, which fails.
        SaleDTO saleDTO = saleMapper.toDto(sale);

        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isBadRequest());

        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountKiloIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleRepository.findAll().size();
        // set the field null
        sale.setAmountKilo(null);

        // Create the Sale, which fails.
        SaleDTO saleDTO = saleMapper.toDto(sale);

        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isBadRequest());

        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceKiloIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleRepository.findAll().size();
        // set the field null
        sale.setPriceKilo(null);

        // Create the Sale, which fails.
        SaleDTO saleDTO = saleMapper.toDto(sale);

        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isBadRequest());

        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceTotalIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleRepository.findAll().size();
        // set the field null
        sale.setPriceTotal(null);

        // Create the Sale, which fails.
        SaleDTO saleDTO = saleMapper.toDto(sale);

        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isBadRequest());

        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleRepository.findAll().size();
        // set the field null
        sale.setCity(null);

        // Create the Sale, which fails.
        SaleDTO saleDTO = saleMapper.toDto(sale);

        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isBadRequest());

        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAvailableDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = saleRepository.findAll().size();
        // set the field null
        sale.setAvailableDate(null);

        // Create the Sale, which fails.
        SaleDTO saleDTO = saleMapper.toDto(sale);

        restSaleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isBadRequest());

        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSales() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        // Get all the saleList
        restSaleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sale.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameProducts").value(hasItem(DEFAULT_NAME_PRODUCTS)))
            .andExpect(jsonPath("$.[*].amountKilo").value(hasItem(DEFAULT_AMOUNT_KILO.doubleValue())))
            .andExpect(jsonPath("$.[*].priceKilo").value(hasItem(DEFAULT_PRICE_KILO.doubleValue())))
            .andExpect(jsonPath("$.[*].priceTotal").value(hasItem(DEFAULT_PRICE_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].availableDate").value(hasItem(DEFAULT_AVAILABLE_DATE.toString())))
            .andExpect(jsonPath("$.[*].stateTransportations").value(hasItem(DEFAULT_STATE_TRANSPORTATIONS.toString())))
            .andExpect(jsonPath("$.[*].descriptions").value(hasItem(DEFAULT_DESCRIPTIONS)));
    }

    @Test
    @Transactional
    void getSale() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        // Get the sale
        restSaleMockMvc
            .perform(get(ENTITY_API_URL_ID, sale.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sale.getId().intValue()))
            .andExpect(jsonPath("$.nameProducts").value(DEFAULT_NAME_PRODUCTS))
            .andExpect(jsonPath("$.amountKilo").value(DEFAULT_AMOUNT_KILO.doubleValue()))
            .andExpect(jsonPath("$.priceKilo").value(DEFAULT_PRICE_KILO.doubleValue()))
            .andExpect(jsonPath("$.priceTotal").value(DEFAULT_PRICE_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.availableDate").value(DEFAULT_AVAILABLE_DATE.toString()))
            .andExpect(jsonPath("$.stateTransportations").value(DEFAULT_STATE_TRANSPORTATIONS.toString()))
            .andExpect(jsonPath("$.descriptions").value(DEFAULT_DESCRIPTIONS));
    }

    @Test
    @Transactional
    void getNonExistingSale() throws Exception {
        // Get the sale
        restSaleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSale() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        int databaseSizeBeforeUpdate = saleRepository.findAll().size();

        // Update the sale
        Sale updatedSale = saleRepository.findById(sale.getId()).get();
        // Disconnect from session so that the updates on updatedSale are not directly saved in db
        em.detach(updatedSale);
        updatedSale
            .nameProducts(UPDATED_NAME_PRODUCTS)
            .amountKilo(UPDATED_AMOUNT_KILO)
            .priceKilo(UPDATED_PRICE_KILO)
            .priceTotal(UPDATED_PRICE_TOTAL)
            .city(UPDATED_CITY)
            .availableDate(UPDATED_AVAILABLE_DATE)
            .stateTransportations(UPDATED_STATE_TRANSPORTATIONS)
            .descriptions(UPDATED_DESCRIPTIONS);
        SaleDTO saleDTO = saleMapper.toDto(updatedSale);

        restSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
        Sale testSale = saleList.get(saleList.size() - 1);
        assertThat(testSale.getNameProducts()).isEqualTo(UPDATED_NAME_PRODUCTS);
        assertThat(testSale.getAmountKilo()).isEqualTo(UPDATED_AMOUNT_KILO);
        assertThat(testSale.getPriceKilo()).isEqualTo(UPDATED_PRICE_KILO);
        assertThat(testSale.getPriceTotal()).isEqualTo(UPDATED_PRICE_TOTAL);
        assertThat(testSale.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSale.getAvailableDate()).isEqualTo(UPDATED_AVAILABLE_DATE);
        assertThat(testSale.getStateTransportations()).isEqualTo(UPDATED_STATE_TRANSPORTATIONS);
        assertThat(testSale.getDescriptions()).isEqualTo(UPDATED_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void putNonExistingSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();
        sale.setId(count.incrementAndGet());

        // Create the Sale
        SaleDTO saleDTO = saleMapper.toDto(sale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, saleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();
        sale.setId(count.incrementAndGet());

        // Create the Sale
        SaleDTO saleDTO = saleMapper.toDto(sale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(saleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();
        sale.setId(count.incrementAndGet());

        // Create the Sale
        SaleDTO saleDTO = saleMapper.toDto(sale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSaleWithPatch() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        int databaseSizeBeforeUpdate = saleRepository.findAll().size();

        // Update the sale using partial update
        Sale partialUpdatedSale = new Sale();
        partialUpdatedSale.setId(sale.getId());

        partialUpdatedSale
            .amountKilo(UPDATED_AMOUNT_KILO)
            .priceKilo(UPDATED_PRICE_KILO)
            .priceTotal(UPDATED_PRICE_TOTAL)
            .city(UPDATED_CITY)
            .availableDate(UPDATED_AVAILABLE_DATE)
            .stateTransportations(UPDATED_STATE_TRANSPORTATIONS);

        restSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSale))
            )
            .andExpect(status().isOk());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
        Sale testSale = saleList.get(saleList.size() - 1);
        assertThat(testSale.getNameProducts()).isEqualTo(DEFAULT_NAME_PRODUCTS);
        assertThat(testSale.getAmountKilo()).isEqualTo(UPDATED_AMOUNT_KILO);
        assertThat(testSale.getPriceKilo()).isEqualTo(UPDATED_PRICE_KILO);
        assertThat(testSale.getPriceTotal()).isEqualTo(UPDATED_PRICE_TOTAL);
        assertThat(testSale.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSale.getAvailableDate()).isEqualTo(UPDATED_AVAILABLE_DATE);
        assertThat(testSale.getStateTransportations()).isEqualTo(UPDATED_STATE_TRANSPORTATIONS);
        assertThat(testSale.getDescriptions()).isEqualTo(DEFAULT_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void fullUpdateSaleWithPatch() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        int databaseSizeBeforeUpdate = saleRepository.findAll().size();

        // Update the sale using partial update
        Sale partialUpdatedSale = new Sale();
        partialUpdatedSale.setId(sale.getId());

        partialUpdatedSale
            .nameProducts(UPDATED_NAME_PRODUCTS)
            .amountKilo(UPDATED_AMOUNT_KILO)
            .priceKilo(UPDATED_PRICE_KILO)
            .priceTotal(UPDATED_PRICE_TOTAL)
            .city(UPDATED_CITY)
            .availableDate(UPDATED_AVAILABLE_DATE)
            .stateTransportations(UPDATED_STATE_TRANSPORTATIONS)
            .descriptions(UPDATED_DESCRIPTIONS);

        restSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSale.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSale))
            )
            .andExpect(status().isOk());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
        Sale testSale = saleList.get(saleList.size() - 1);
        assertThat(testSale.getNameProducts()).isEqualTo(UPDATED_NAME_PRODUCTS);
        assertThat(testSale.getAmountKilo()).isEqualTo(UPDATED_AMOUNT_KILO);
        assertThat(testSale.getPriceKilo()).isEqualTo(UPDATED_PRICE_KILO);
        assertThat(testSale.getPriceTotal()).isEqualTo(UPDATED_PRICE_TOTAL);
        assertThat(testSale.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testSale.getAvailableDate()).isEqualTo(UPDATED_AVAILABLE_DATE);
        assertThat(testSale.getStateTransportations()).isEqualTo(UPDATED_STATE_TRANSPORTATIONS);
        assertThat(testSale.getDescriptions()).isEqualTo(UPDATED_DESCRIPTIONS);
    }

    @Test
    @Transactional
    void patchNonExistingSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();
        sale.setId(count.incrementAndGet());

        // Create the Sale
        SaleDTO saleDTO = saleMapper.toDto(sale);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, saleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();
        sale.setId(count.incrementAndGet());

        // Create the Sale
        SaleDTO saleDTO = saleMapper.toDto(sale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(saleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSale() throws Exception {
        int databaseSizeBeforeUpdate = saleRepository.findAll().size();
        sale.setId(count.incrementAndGet());

        // Create the Sale
        SaleDTO saleDTO = saleMapper.toDto(sale);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSaleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(saleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sale in the database
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSale() throws Exception {
        // Initialize the database
        saleRepository.saveAndFlush(sale);

        int databaseSizeBeforeDelete = saleRepository.findAll().size();

        // Delete the sale
        restSaleMockMvc
            .perform(delete(ENTITY_API_URL_ID, sale.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sale> saleList = saleRepository.findAll();
        assertThat(saleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
