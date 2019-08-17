package com.paypal.mng.web.rest;

import com.paypal.mng.PaypalmngApp;
import com.paypal.mng.domain.Store;
import com.paypal.mng.domain.Paypal;
import com.paypal.mng.repository.StoreRepository;
import com.paypal.mng.service.StoreService;
import com.paypal.mng.service.dto.StoreDTO;
import com.paypal.mng.service.mapper.StoreMapper;
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
 * Integration tests for the {@link StoreResource} REST controller.
 */
@SpringBootTest(classes = PaypalmngApp.class)
public class StoreResourceIT {

    private static final String DEFAULT_SHOPIFY_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SHOPIFY_API_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_SHOPIFY_API_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_SHOPIFY_API_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_STORE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_STORE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_CREATED_AT = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_UPDATED_AT = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_SHOPIFY_API_URL = "AAAAAAAAAA";
    private static final String UPDATED_SHOPIFY_API_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AUTOMATION_STATUS = false;
    private static final Boolean UPDATED_AUTOMATION_STATUS = true;

    private static final Long DEFAULT_SINCE_ID = 1L;
    private static final Long UPDATED_SINCE_ID = 2L;
    private static final Long SMALLER_SINCE_ID = 1L - 1L;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private StoreService storeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restStoreMockMvc;

    private Store store;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StoreResource storeResource = new StoreResource(storeService);
        this.restStoreMockMvc = MockMvcBuilders.standaloneSetup(storeResource)
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
    public static Store createEntity(EntityManager em) {
        Store store = new Store()
            .shopifyApiKey(DEFAULT_SHOPIFY_API_KEY)
            .shopifyApiPassword(DEFAULT_SHOPIFY_API_PASSWORD)
            .storeName(DEFAULT_STORE_NAME)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .shopifyApiUrl(DEFAULT_SHOPIFY_API_URL)
            .automationStatus(DEFAULT_AUTOMATION_STATUS)
            .sinceId(DEFAULT_SINCE_ID);
        // Add required entity
        Paypal paypal;
        if (TestUtil.findAll(em, Paypal.class).isEmpty()) {
            paypal = PaypalResourceIT.createEntity(em);
            em.persist(paypal);
            em.flush();
        } else {
            paypal = TestUtil.findAll(em, Paypal.class).get(0);
        }
        store.setPaypal(paypal);
        return store;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Store createUpdatedEntity(EntityManager em) {
        Store store = new Store()
            .shopifyApiKey(UPDATED_SHOPIFY_API_KEY)
            .shopifyApiPassword(UPDATED_SHOPIFY_API_PASSWORD)
            .storeName(UPDATED_STORE_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .shopifyApiUrl(UPDATED_SHOPIFY_API_URL)
            .automationStatus(UPDATED_AUTOMATION_STATUS)
            .sinceId(UPDATED_SINCE_ID);
        // Add required entity
        Paypal paypal;
        if (TestUtil.findAll(em, Paypal.class).isEmpty()) {
            paypal = PaypalResourceIT.createUpdatedEntity(em);
            em.persist(paypal);
            em.flush();
        } else {
            paypal = TestUtil.findAll(em, Paypal.class).get(0);
        }
        store.setPaypal(paypal);
        return store;
    }

    @BeforeEach
    public void initTest() {
        store = createEntity(em);
    }

    @Test
    @Transactional
    public void createStore() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);
        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isCreated());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeCreate + 1);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getShopifyApiKey()).isEqualTo(DEFAULT_SHOPIFY_API_KEY);
        assertThat(testStore.getShopifyApiPassword()).isEqualTo(DEFAULT_SHOPIFY_API_PASSWORD);
        assertThat(testStore.getStoreName()).isEqualTo(DEFAULT_STORE_NAME);
        assertThat(testStore.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testStore.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testStore.getShopifyApiUrl()).isEqualTo(DEFAULT_SHOPIFY_API_URL);
        assertThat(testStore.isAutomationStatus()).isEqualTo(DEFAULT_AUTOMATION_STATUS);
        assertThat(testStore.getSinceId()).isEqualTo(DEFAULT_SINCE_ID);
    }

    @Test
    @Transactional
    public void createStoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // Create the Store with an existing ID
        store.setId(1L);
        StoreDTO storeDTO = storeMapper.toDto(store);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkShopifyApiKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setShopifyApiKey(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShopifyApiPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setShopifyApiPassword(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStoreNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setStoreName(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShopifyApiUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = storeRepository.findAll().size();
        // set the field null
        store.setShopifyApiUrl(null);

        // Create the Store, which fails.
        StoreDTO storeDTO = storeMapper.toDto(store);

        restStoreMockMvc.perform(post("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStores() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList
        restStoreMockMvc.perform(get("/api/stores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(store.getId().intValue())))
            .andExpect(jsonPath("$.[*].shopifyApiKey").value(hasItem(DEFAULT_SHOPIFY_API_KEY.toString())))
            .andExpect(jsonPath("$.[*].shopifyApiPassword").value(hasItem(DEFAULT_SHOPIFY_API_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].storeName").value(hasItem(DEFAULT_STORE_NAME.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].shopifyApiUrl").value(hasItem(DEFAULT_SHOPIFY_API_URL.toString())))
            .andExpect(jsonPath("$.[*].automationStatus").value(hasItem(DEFAULT_AUTOMATION_STATUS.booleanValue())))
            .andExpect(jsonPath("$.[*].sinceId").value(hasItem(DEFAULT_SINCE_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", store.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(store.getId().intValue()))
            .andExpect(jsonPath("$.shopifyApiKey").value(DEFAULT_SHOPIFY_API_KEY.toString()))
            .andExpect(jsonPath("$.shopifyApiPassword").value(DEFAULT_SHOPIFY_API_PASSWORD.toString()))
            .andExpect(jsonPath("$.storeName").value(DEFAULT_STORE_NAME.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.shopifyApiUrl").value(DEFAULT_SHOPIFY_API_URL.toString()))
            .andExpect(jsonPath("$.automationStatus").value(DEFAULT_AUTOMATION_STATUS.booleanValue()))
            .andExpect(jsonPath("$.sinceId").value(DEFAULT_SINCE_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStore() throws Exception {
        // Get the store
        restStoreMockMvc.perform(get("/api/stores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store
        Store updatedStore = storeRepository.findById(store.getId()).get();
        // Disconnect from session so that the updates on updatedStore are not directly saved in db
        em.detach(updatedStore);
        updatedStore
            .shopifyApiKey(UPDATED_SHOPIFY_API_KEY)
            .shopifyApiPassword(UPDATED_SHOPIFY_API_PASSWORD)
            .storeName(UPDATED_STORE_NAME)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .shopifyApiUrl(UPDATED_SHOPIFY_API_URL)
            .automationStatus(UPDATED_AUTOMATION_STATUS)
            .sinceId(UPDATED_SINCE_ID);
        StoreDTO storeDTO = storeMapper.toDto(updatedStore);

        restStoreMockMvc.perform(put("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isOk());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getShopifyApiKey()).isEqualTo(UPDATED_SHOPIFY_API_KEY);
        assertThat(testStore.getShopifyApiPassword()).isEqualTo(UPDATED_SHOPIFY_API_PASSWORD);
        assertThat(testStore.getStoreName()).isEqualTo(UPDATED_STORE_NAME);
        assertThat(testStore.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testStore.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testStore.getShopifyApiUrl()).isEqualTo(UPDATED_SHOPIFY_API_URL);
        assertThat(testStore.isAutomationStatus()).isEqualTo(UPDATED_AUTOMATION_STATUS);
        assertThat(testStore.getSinceId()).isEqualTo(UPDATED_SINCE_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreMockMvc.perform(put("/api/stores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeDelete = storeRepository.findAll().size();

        // Delete the store
        restStoreMockMvc.perform(delete("/api/stores/{id}", store.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Store.class);
        Store store1 = new Store();
        store1.setId(1L);
        Store store2 = new Store();
        store2.setId(store1.getId());
        assertThat(store1).isEqualTo(store2);
        store2.setId(2L);
        assertThat(store1).isNotEqualTo(store2);
        store1.setId(null);
        assertThat(store1).isNotEqualTo(store2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StoreDTO.class);
        StoreDTO storeDTO1 = new StoreDTO();
        storeDTO1.setId(1L);
        StoreDTO storeDTO2 = new StoreDTO();
        assertThat(storeDTO1).isNotEqualTo(storeDTO2);
        storeDTO2.setId(storeDTO1.getId());
        assertThat(storeDTO1).isEqualTo(storeDTO2);
        storeDTO2.setId(2L);
        assertThat(storeDTO1).isNotEqualTo(storeDTO2);
        storeDTO1.setId(null);
        assertThat(storeDTO1).isNotEqualTo(storeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(storeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(storeMapper.fromId(null)).isNull();
    }
}
