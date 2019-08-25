package com.paypal.mng.web.rest;

import com.paypal.mng.PaypalmngApp;
import com.paypal.mng.domain.PaypalHistory;
import com.paypal.mng.repository.PaypalHistoryRepository;
import com.paypal.mng.service.OrderService;
import com.paypal.mng.service.PaypalHistoryService;
import com.paypal.mng.service.dto.PaypalHistoryDTO;
import com.paypal.mng.service.mapper.PaypalHistoryMapper;
import com.paypal.mng.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.paypal.mng.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PaypalHistoryResource} REST controller.
 */
@SpringBootTest(classes = PaypalmngApp.class)
public class PaypalHistoryResourceIT {

    private static final Long DEFAULT_SHOPIFY_ORDER_ID = 1L;
    private static final Long UPDATED_SHOPIFY_ORDER_ID = 2L;
    private static final Long SMALLER_SHOPIFY_ORDER_ID = 1L - 1L;

    private static final String DEFAULT_SHOPIFY_TRACKING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SHOPIFY_TRACKING_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SHOPIFY_AUTHORIZATION_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SHOPIFY_AUTHORIZATION_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_SHOPIFY_SHIPPING_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_SHOPIFY_SHIPPING_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CARRIER = "AAAAAAAAAA";
    private static final String UPDATED_CARRIER = "BBBBBBBBBB";

    private static final Integer DEFAULT_STATUS = 1;
    private static final Integer UPDATED_STATUS = 2;
    private static final Integer SMALLER_STATUS = 1 - 1;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_CREATED_AT = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_UPDATED_AT = Instant.ofEpochMilli(-1L);

    private static final Integer DEFAULT_SHOPIFY_ORDER_NUMBER = 1;
    private static final Integer UPDATED_SHOPIFY_ORDER_NUMBER = 2;
    private static final Integer SMALLER_SHOPIFY_ORDER_NUMBER = 1 - 1;

    private static final String DEFAULT_SHOPIFY_ORDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SHOPIFY_ORDER_NAME = "BBBBBBBBBB";

    @Autowired
    private PaypalHistoryRepository paypalHistoryRepository;

    @Autowired
    private PaypalHistoryMapper paypalHistoryMapper;

    @Autowired
    private PaypalHistoryService paypalHistoryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private  OrderService orderService;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPaypalHistoryMockMvc;

    private PaypalHistory paypalHistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaypalHistoryResource paypalHistoryResource = new PaypalHistoryResource(paypalHistoryService, orderService);
        this.restPaypalHistoryMockMvc = MockMvcBuilders.standaloneSetup(paypalHistoryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaypalHistory createEntity(EntityManager em) {
        PaypalHistory paypalHistory = new PaypalHistory()
            .shopifyOrderId(DEFAULT_SHOPIFY_ORDER_ID)
            .shopifyTrackingNumber(DEFAULT_SHOPIFY_TRACKING_NUMBER)
            .shopifyAuthorizationKey(DEFAULT_SHOPIFY_AUTHORIZATION_KEY)
            .shopifyShippingStatus(DEFAULT_SHOPIFY_SHIPPING_STATUS)
            .carrier(DEFAULT_CARRIER)
            .status(DEFAULT_STATUS)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .shopifyOrderNumber(DEFAULT_SHOPIFY_ORDER_NUMBER)
            .shopifyOrderName(DEFAULT_SHOPIFY_ORDER_NAME);
        return paypalHistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaypalHistory createUpdatedEntity(EntityManager em) {
        PaypalHistory paypalHistory = new PaypalHistory()
            .shopifyOrderId(UPDATED_SHOPIFY_ORDER_ID)
            .shopifyTrackingNumber(UPDATED_SHOPIFY_TRACKING_NUMBER)
            .shopifyAuthorizationKey(UPDATED_SHOPIFY_AUTHORIZATION_KEY)
            .shopifyShippingStatus(UPDATED_SHOPIFY_SHIPPING_STATUS)
            .carrier(UPDATED_CARRIER)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .shopifyOrderNumber(UPDATED_SHOPIFY_ORDER_NUMBER)
            .shopifyOrderName(UPDATED_SHOPIFY_ORDER_NAME);
        return paypalHistory;
    }

    @BeforeEach
    public void initTest() {
        paypalHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaypalHistory() throws Exception {
        int databaseSizeBeforeCreate = paypalHistoryRepository.findAll().size();

        // Create the PaypalHistory
        PaypalHistoryDTO paypalHistoryDTO = paypalHistoryMapper.toDto(paypalHistory);
        restPaypalHistoryMockMvc.perform(post("/api/paypal-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalHistoryDTO)))
            .andExpect(status().isCreated());

        // Validate the PaypalHistory in the database
        List<PaypalHistory> paypalHistoryList = paypalHistoryRepository.findAll();
        assertThat(paypalHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        PaypalHistory testPaypalHistory = paypalHistoryList.get(paypalHistoryList.size() - 1);
        assertThat(testPaypalHistory.getShopifyOrderId()).isEqualTo(DEFAULT_SHOPIFY_ORDER_ID);
        assertThat(testPaypalHistory.getShopifyTrackingNumber()).isEqualTo(DEFAULT_SHOPIFY_TRACKING_NUMBER);
        assertThat(testPaypalHistory.getShopifyAuthorizationKey()).isEqualTo(DEFAULT_SHOPIFY_AUTHORIZATION_KEY);
        assertThat(testPaypalHistory.getShopifyShippingStatus()).isEqualTo(DEFAULT_SHOPIFY_SHIPPING_STATUS);
        assertThat(testPaypalHistory.getCarrier()).isEqualTo(DEFAULT_CARRIER);
        assertThat(testPaypalHistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaypalHistory.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPaypalHistory.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testPaypalHistory.getShopifyOrderNumber()).isEqualTo(DEFAULT_SHOPIFY_ORDER_NUMBER);
        assertThat(testPaypalHistory.getShopifyOrderName()).isEqualTo(DEFAULT_SHOPIFY_ORDER_NAME);
    }

    @Test
    @Transactional
    public void createPaypalHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paypalHistoryRepository.findAll().size();

        // Create the PaypalHistory with an existing ID
        paypalHistory.setId(1L);
        PaypalHistoryDTO paypalHistoryDTO = paypalHistoryMapper.toDto(paypalHistory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaypalHistoryMockMvc.perform(post("/api/paypal-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaypalHistory in the database
        List<PaypalHistory> paypalHistoryList = paypalHistoryRepository.findAll();
        assertThat(paypalHistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkShopifyOrderIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paypalHistoryRepository.findAll().size();
        // set the field null
        paypalHistory.setShopifyOrderId(null);

        // Create the PaypalHistory, which fails.
        PaypalHistoryDTO paypalHistoryDTO = paypalHistoryMapper.toDto(paypalHistory);

        restPaypalHistoryMockMvc.perform(post("/api/paypal-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<PaypalHistory> paypalHistoryList = paypalHistoryRepository.findAll();
        assertThat(paypalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShopifyTrackingNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = paypalHistoryRepository.findAll().size();
        // set the field null
        paypalHistory.setShopifyTrackingNumber(null);

        // Create the PaypalHistory, which fails.
        PaypalHistoryDTO paypalHistoryDTO = paypalHistoryMapper.toDto(paypalHistory);

        restPaypalHistoryMockMvc.perform(post("/api/paypal-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<PaypalHistory> paypalHistoryList = paypalHistoryRepository.findAll();
        assertThat(paypalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShopifyAuthorizationKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = paypalHistoryRepository.findAll().size();
        // set the field null
        paypalHistory.setShopifyAuthorizationKey(null);

        // Create the PaypalHistory, which fails.
        PaypalHistoryDTO paypalHistoryDTO = paypalHistoryMapper.toDto(paypalHistory);

        restPaypalHistoryMockMvc.perform(post("/api/paypal-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<PaypalHistory> paypalHistoryList = paypalHistoryRepository.findAll();
        assertThat(paypalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShopifyShippingStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = paypalHistoryRepository.findAll().size();
        // set the field null
        paypalHistory.setShopifyShippingStatus(null);

        // Create the PaypalHistory, which fails.
        PaypalHistoryDTO paypalHistoryDTO = paypalHistoryMapper.toDto(paypalHistory);

        restPaypalHistoryMockMvc.perform(post("/api/paypal-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<PaypalHistory> paypalHistoryList = paypalHistoryRepository.findAll();
        assertThat(paypalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCarrierIsRequired() throws Exception {
        int databaseSizeBeforeTest = paypalHistoryRepository.findAll().size();
        // set the field null
        paypalHistory.setCarrier(null);

        // Create the PaypalHistory, which fails.
        PaypalHistoryDTO paypalHistoryDTO = paypalHistoryMapper.toDto(paypalHistory);

        restPaypalHistoryMockMvc.perform(post("/api/paypal-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<PaypalHistory> paypalHistoryList = paypalHistoryRepository.findAll();
        assertThat(paypalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = paypalHistoryRepository.findAll().size();
        // set the field null
        paypalHistory.setStatus(null);

        // Create the PaypalHistory, which fails.
        PaypalHistoryDTO paypalHistoryDTO = paypalHistoryMapper.toDto(paypalHistory);

        restPaypalHistoryMockMvc.perform(post("/api/paypal-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<PaypalHistory> paypalHistoryList = paypalHistoryRepository.findAll();
        assertThat(paypalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShopifyOrderNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = paypalHistoryRepository.findAll().size();
        // set the field null
        paypalHistory.setShopifyOrderNumber(null);

        // Create the PaypalHistory, which fails.
        PaypalHistoryDTO paypalHistoryDTO = paypalHistoryMapper.toDto(paypalHistory);

        restPaypalHistoryMockMvc.perform(post("/api/paypal-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalHistoryDTO)))
            .andExpect(status().isBadRequest());

        List<PaypalHistory> paypalHistoryList = paypalHistoryRepository.findAll();
        assertThat(paypalHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaypalHistories() throws Exception {
        // Initialize the database
        paypalHistoryRepository.saveAndFlush(paypalHistory);

        // Get all the paypalHistoryList
        restPaypalHistoryMockMvc.perform(get("/api/paypal-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paypalHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].shopifyOrderId").value(hasItem(DEFAULT_SHOPIFY_ORDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].shopifyTrackingNumber").value(hasItem(DEFAULT_SHOPIFY_TRACKING_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].shopifyAuthorizationKey").value(hasItem(DEFAULT_SHOPIFY_AUTHORIZATION_KEY.toString())))
            .andExpect(jsonPath("$.[*].shopifyShippingStatus").value(hasItem(DEFAULT_SHOPIFY_SHIPPING_STATUS.toString())))
            .andExpect(jsonPath("$.[*].carrier").value(hasItem(DEFAULT_CARRIER.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].shopifyOrderNumber").value(hasItem(DEFAULT_SHOPIFY_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].shopifyOrderName").value(hasItem(DEFAULT_SHOPIFY_ORDER_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPaypalHistory() throws Exception {
        // Initialize the database
        paypalHistoryRepository.saveAndFlush(paypalHistory);

        // Get the paypalHistory
        restPaypalHistoryMockMvc.perform(get("/api/paypal-histories/{id}", paypalHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paypalHistory.getId().intValue()))
            .andExpect(jsonPath("$.shopifyOrderId").value(DEFAULT_SHOPIFY_ORDER_ID.intValue()))
            .andExpect(jsonPath("$.shopifyTrackingNumber").value(DEFAULT_SHOPIFY_TRACKING_NUMBER.toString()))
            .andExpect(jsonPath("$.shopifyAuthorizationKey").value(DEFAULT_SHOPIFY_AUTHORIZATION_KEY.toString()))
            .andExpect(jsonPath("$.shopifyShippingStatus").value(DEFAULT_SHOPIFY_SHIPPING_STATUS.toString()))
            .andExpect(jsonPath("$.carrier").value(DEFAULT_CARRIER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.shopifyOrderNumber").value(DEFAULT_SHOPIFY_ORDER_NUMBER))
            .andExpect(jsonPath("$.shopifyOrderName").value(DEFAULT_SHOPIFY_ORDER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaypalHistory() throws Exception {
        // Get the paypalHistory
        restPaypalHistoryMockMvc.perform(get("/api/paypal-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaypalHistory() throws Exception {
        // Initialize the database
        paypalHistoryRepository.saveAndFlush(paypalHistory);

        int databaseSizeBeforeUpdate = paypalHistoryRepository.findAll().size();

        // Update the paypalHistory
        PaypalHistory updatedPaypalHistory = paypalHistoryRepository.findById(paypalHistory.getId()).get();
        // Disconnect from session so that the updates on updatedPaypalHistory are not directly saved in db
        em.detach(updatedPaypalHistory);
        updatedPaypalHistory
            .shopifyOrderId(UPDATED_SHOPIFY_ORDER_ID)
            .shopifyTrackingNumber(UPDATED_SHOPIFY_TRACKING_NUMBER)
            .shopifyAuthorizationKey(UPDATED_SHOPIFY_AUTHORIZATION_KEY)
            .shopifyShippingStatus(UPDATED_SHOPIFY_SHIPPING_STATUS)
            .carrier(UPDATED_CARRIER)
            .status(UPDATED_STATUS)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .shopifyOrderNumber(UPDATED_SHOPIFY_ORDER_NUMBER)
            .shopifyOrderName(UPDATED_SHOPIFY_ORDER_NAME);
        PaypalHistoryDTO paypalHistoryDTO = paypalHistoryMapper.toDto(updatedPaypalHistory);

        restPaypalHistoryMockMvc.perform(put("/api/paypal-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalHistoryDTO)))
            .andExpect(status().isOk());

        // Validate the PaypalHistory in the database
        List<PaypalHistory> paypalHistoryList = paypalHistoryRepository.findAll();
        assertThat(paypalHistoryList).hasSize(databaseSizeBeforeUpdate);
        PaypalHistory testPaypalHistory = paypalHistoryList.get(paypalHistoryList.size() - 1);
        assertThat(testPaypalHistory.getShopifyOrderId()).isEqualTo(UPDATED_SHOPIFY_ORDER_ID);
        assertThat(testPaypalHistory.getShopifyTrackingNumber()).isEqualTo(UPDATED_SHOPIFY_TRACKING_NUMBER);
        assertThat(testPaypalHistory.getShopifyAuthorizationKey()).isEqualTo(UPDATED_SHOPIFY_AUTHORIZATION_KEY);
        assertThat(testPaypalHistory.getShopifyShippingStatus()).isEqualTo(UPDATED_SHOPIFY_SHIPPING_STATUS);
        assertThat(testPaypalHistory.getCarrier()).isEqualTo(UPDATED_CARRIER);
        assertThat(testPaypalHistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaypalHistory.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPaypalHistory.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testPaypalHistory.getShopifyOrderNumber()).isEqualTo(UPDATED_SHOPIFY_ORDER_NUMBER);
        assertThat(testPaypalHistory.getShopifyOrderName()).isEqualTo(UPDATED_SHOPIFY_ORDER_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingPaypalHistory() throws Exception {
        int databaseSizeBeforeUpdate = paypalHistoryRepository.findAll().size();

        // Create the PaypalHistory
        PaypalHistoryDTO paypalHistoryDTO = paypalHistoryMapper.toDto(paypalHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaypalHistoryMockMvc.perform(put("/api/paypal-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalHistoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaypalHistory in the database
        List<PaypalHistory> paypalHistoryList = paypalHistoryRepository.findAll();
        assertThat(paypalHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaypalHistory() throws Exception {
        // Initialize the database
        paypalHistoryRepository.saveAndFlush(paypalHistory);

        int databaseSizeBeforeDelete = paypalHistoryRepository.findAll().size();

        // Delete the paypalHistory
        restPaypalHistoryMockMvc.perform(delete("/api/paypal-histories/{id}", paypalHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaypalHistory> paypalHistoryList = paypalHistoryRepository.findAll();
        assertThat(paypalHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaypalHistory.class);
        PaypalHistory paypalHistory1 = new PaypalHistory();
        paypalHistory1.setId(1L);
        PaypalHistory paypalHistory2 = new PaypalHistory();
        paypalHistory2.setId(paypalHistory1.getId());
        assertThat(paypalHistory1).isEqualTo(paypalHistory2);
        paypalHistory2.setId(2L);
        assertThat(paypalHistory1).isNotEqualTo(paypalHistory2);
        paypalHistory1.setId(null);
        assertThat(paypalHistory1).isNotEqualTo(paypalHistory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaypalHistoryDTO.class);
        PaypalHistoryDTO paypalHistoryDTO1 = new PaypalHistoryDTO();
        paypalHistoryDTO1.setId(1L);
        PaypalHistoryDTO paypalHistoryDTO2 = new PaypalHistoryDTO();
        assertThat(paypalHistoryDTO1).isNotEqualTo(paypalHistoryDTO2);
        paypalHistoryDTO2.setId(paypalHistoryDTO1.getId());
        assertThat(paypalHistoryDTO1).isEqualTo(paypalHistoryDTO2);
        paypalHistoryDTO2.setId(2L);
        assertThat(paypalHistoryDTO1).isNotEqualTo(paypalHistoryDTO2);
        paypalHistoryDTO1.setId(null);
        assertThat(paypalHistoryDTO1).isNotEqualTo(paypalHistoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paypalHistoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paypalHistoryMapper.fromId(null)).isNull();
    }
}
