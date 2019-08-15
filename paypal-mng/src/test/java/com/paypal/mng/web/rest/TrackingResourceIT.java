package com.paypal.mng.web.rest;

import com.paypal.mng.PaypalmngApp;
import com.paypal.mng.domain.Tracking;
import com.paypal.mng.domain.Order;
import com.paypal.mng.repository.TrackingRepository;
import com.paypal.mng.service.TrackingService;
import com.paypal.mng.service.dto.TrackingDTO;
import com.paypal.mng.service.mapper.TrackingMapper;
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
 * Integration tests for the {@link TrackingResource} REST controller.
 */
@SpringBootTest(classes = PaypalmngApp.class)
public class TrackingResourceIT {

    private static final String DEFAULT_TRACKING_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TRACKING_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TRACKING_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_TRACKING_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_TRACKING_URL = "AAAAAAAAAA";
    private static final String UPDATED_TRACKING_URL = "BBBBBBBBBB";

    private static final String DEFAULT_PAYPAL_TRACKER_ID = "AAAAAAAAAA";
    private static final String UPDATED_PAYPAL_TRACKER_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_CREATED_AT = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_UPDATED_AT = Instant.ofEpochMilli(-1L);

    @Autowired
    private TrackingRepository trackingRepository;

    @Autowired
    private TrackingMapper trackingMapper;

    @Autowired
    private TrackingService trackingService;

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

    private MockMvc restTrackingMockMvc;

    private Tracking tracking;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrackingResource trackingResource = new TrackingResource(trackingService);
        this.restTrackingMockMvc = MockMvcBuilders.standaloneSetup(trackingResource)
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
    public static Tracking createEntity(EntityManager em) {
        Tracking tracking = new Tracking()
            .trackingNumber(DEFAULT_TRACKING_NUMBER)
            .trackingCompany(DEFAULT_TRACKING_COMPANY)
            .trackingUrl(DEFAULT_TRACKING_URL)
            .paypalTrackerId(DEFAULT_PAYPAL_TRACKER_ID)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        // Add required entity
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createEntity(em);
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        tracking.setOrder(order);
        return tracking;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tracking createUpdatedEntity(EntityManager em) {
        Tracking tracking = new Tracking()
            .trackingNumber(UPDATED_TRACKING_NUMBER)
            .trackingCompany(UPDATED_TRACKING_COMPANY)
            .trackingUrl(UPDATED_TRACKING_URL)
            .paypalTrackerId(UPDATED_PAYPAL_TRACKER_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        // Add required entity
        Order order;
        if (TestUtil.findAll(em, Order.class).isEmpty()) {
            order = OrderResourceIT.createUpdatedEntity(em);
            em.persist(order);
            em.flush();
        } else {
            order = TestUtil.findAll(em, Order.class).get(0);
        }
        tracking.setOrder(order);
        return tracking;
    }

    @BeforeEach
    public void initTest() {
        tracking = createEntity(em);
    }

    @Test
    @Transactional
    public void createTracking() throws Exception {
        int databaseSizeBeforeCreate = trackingRepository.findAll().size();

        // Create the Tracking
        TrackingDTO trackingDTO = trackingMapper.toDto(tracking);
        restTrackingMockMvc.perform(post("/api/trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackingDTO)))
            .andExpect(status().isCreated());

        // Validate the Tracking in the database
        List<Tracking> trackingList = trackingRepository.findAll();
        assertThat(trackingList).hasSize(databaseSizeBeforeCreate + 1);
        Tracking testTracking = trackingList.get(trackingList.size() - 1);
        assertThat(testTracking.getTrackingNumber()).isEqualTo(DEFAULT_TRACKING_NUMBER);
        assertThat(testTracking.getTrackingCompany()).isEqualTo(DEFAULT_TRACKING_COMPANY);
        assertThat(testTracking.getTrackingUrl()).isEqualTo(DEFAULT_TRACKING_URL);
        assertThat(testTracking.getPaypalTrackerId()).isEqualTo(DEFAULT_PAYPAL_TRACKER_ID);
        assertThat(testTracking.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTracking.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createTrackingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trackingRepository.findAll().size();

        // Create the Tracking with an existing ID
        tracking.setId(1L);
        TrackingDTO trackingDTO = trackingMapper.toDto(tracking);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrackingMockMvc.perform(post("/api/trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tracking in the database
        List<Tracking> trackingList = trackingRepository.findAll();
        assertThat(trackingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTrackingNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackingRepository.findAll().size();
        // set the field null
        tracking.setTrackingNumber(null);

        // Create the Tracking, which fails.
        TrackingDTO trackingDTO = trackingMapper.toDto(tracking);

        restTrackingMockMvc.perform(post("/api/trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackingDTO)))
            .andExpect(status().isBadRequest());

        List<Tracking> trackingList = trackingRepository.findAll();
        assertThat(trackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrackingCompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackingRepository.findAll().size();
        // set the field null
        tracking.setTrackingCompany(null);

        // Create the Tracking, which fails.
        TrackingDTO trackingDTO = trackingMapper.toDto(tracking);

        restTrackingMockMvc.perform(post("/api/trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackingDTO)))
            .andExpect(status().isBadRequest());

        List<Tracking> trackingList = trackingRepository.findAll();
        assertThat(trackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrackingUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = trackingRepository.findAll().size();
        // set the field null
        tracking.setTrackingUrl(null);

        // Create the Tracking, which fails.
        TrackingDTO trackingDTO = trackingMapper.toDto(tracking);

        restTrackingMockMvc.perform(post("/api/trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackingDTO)))
            .andExpect(status().isBadRequest());

        List<Tracking> trackingList = trackingRepository.findAll();
        assertThat(trackingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTrackings() throws Exception {
        // Initialize the database
        trackingRepository.saveAndFlush(tracking);

        // Get all the trackingList
        restTrackingMockMvc.perform(get("/api/trackings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tracking.getId().intValue())))
            .andExpect(jsonPath("$.[*].trackingNumber").value(hasItem(DEFAULT_TRACKING_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].trackingCompany").value(hasItem(DEFAULT_TRACKING_COMPANY.toString())))
            .andExpect(jsonPath("$.[*].trackingUrl").value(hasItem(DEFAULT_TRACKING_URL.toString())))
            .andExpect(jsonPath("$.[*].paypalTrackerId").value(hasItem(DEFAULT_PAYPAL_TRACKER_ID.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getTracking() throws Exception {
        // Initialize the database
        trackingRepository.saveAndFlush(tracking);

        // Get the tracking
        restTrackingMockMvc.perform(get("/api/trackings/{id}", tracking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tracking.getId().intValue()))
            .andExpect(jsonPath("$.trackingNumber").value(DEFAULT_TRACKING_NUMBER.toString()))
            .andExpect(jsonPath("$.trackingCompany").value(DEFAULT_TRACKING_COMPANY.toString()))
            .andExpect(jsonPath("$.trackingUrl").value(DEFAULT_TRACKING_URL.toString()))
            .andExpect(jsonPath("$.paypalTrackerId").value(DEFAULT_PAYPAL_TRACKER_ID.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTracking() throws Exception {
        // Get the tracking
        restTrackingMockMvc.perform(get("/api/trackings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTracking() throws Exception {
        // Initialize the database
        trackingRepository.saveAndFlush(tracking);

        int databaseSizeBeforeUpdate = trackingRepository.findAll().size();

        // Update the tracking
        Tracking updatedTracking = trackingRepository.findById(tracking.getId()).get();
        // Disconnect from session so that the updates on updatedTracking are not directly saved in db
        em.detach(updatedTracking);
        updatedTracking
            .trackingNumber(UPDATED_TRACKING_NUMBER)
            .trackingCompany(UPDATED_TRACKING_COMPANY)
            .trackingUrl(UPDATED_TRACKING_URL)
            .paypalTrackerId(UPDATED_PAYPAL_TRACKER_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        TrackingDTO trackingDTO = trackingMapper.toDto(updatedTracking);

        restTrackingMockMvc.perform(put("/api/trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackingDTO)))
            .andExpect(status().isOk());

        // Validate the Tracking in the database
        List<Tracking> trackingList = trackingRepository.findAll();
        assertThat(trackingList).hasSize(databaseSizeBeforeUpdate);
        Tracking testTracking = trackingList.get(trackingList.size() - 1);
        assertThat(testTracking.getTrackingNumber()).isEqualTo(UPDATED_TRACKING_NUMBER);
        assertThat(testTracking.getTrackingCompany()).isEqualTo(UPDATED_TRACKING_COMPANY);
        assertThat(testTracking.getTrackingUrl()).isEqualTo(UPDATED_TRACKING_URL);
        assertThat(testTracking.getPaypalTrackerId()).isEqualTo(UPDATED_PAYPAL_TRACKER_ID);
        assertThat(testTracking.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTracking.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingTracking() throws Exception {
        int databaseSizeBeforeUpdate = trackingRepository.findAll().size();

        // Create the Tracking
        TrackingDTO trackingDTO = trackingMapper.toDto(tracking);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrackingMockMvc.perform(put("/api/trackings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trackingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tracking in the database
        List<Tracking> trackingList = trackingRepository.findAll();
        assertThat(trackingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTracking() throws Exception {
        // Initialize the database
        trackingRepository.saveAndFlush(tracking);

        int databaseSizeBeforeDelete = trackingRepository.findAll().size();

        // Delete the tracking
        restTrackingMockMvc.perform(delete("/api/trackings/{id}", tracking.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tracking> trackingList = trackingRepository.findAll();
        assertThat(trackingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tracking.class);
        Tracking tracking1 = new Tracking();
        tracking1.setId(1L);
        Tracking tracking2 = new Tracking();
        tracking2.setId(tracking1.getId());
        assertThat(tracking1).isEqualTo(tracking2);
        tracking2.setId(2L);
        assertThat(tracking1).isNotEqualTo(tracking2);
        tracking1.setId(null);
        assertThat(tracking1).isNotEqualTo(tracking2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrackingDTO.class);
        TrackingDTO trackingDTO1 = new TrackingDTO();
        trackingDTO1.setId(1L);
        TrackingDTO trackingDTO2 = new TrackingDTO();
        assertThat(trackingDTO1).isNotEqualTo(trackingDTO2);
        trackingDTO2.setId(trackingDTO1.getId());
        assertThat(trackingDTO1).isEqualTo(trackingDTO2);
        trackingDTO2.setId(2L);
        assertThat(trackingDTO1).isNotEqualTo(trackingDTO2);
        trackingDTO1.setId(null);
        assertThat(trackingDTO1).isNotEqualTo(trackingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(trackingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(trackingMapper.fromId(null)).isNull();
    }
}
