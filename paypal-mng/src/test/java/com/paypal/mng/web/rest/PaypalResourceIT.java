package com.paypal.mng.web.rest;

import com.paypal.mng.PaypalmngApp;
import com.paypal.mng.domain.Paypal;
import com.paypal.mng.repository.PaypalRepository;
import com.paypal.mng.service.PaypalService;
import com.paypal.mng.service.dto.PaypalDTO;
import com.paypal.mng.service.mapper.PaypalMapper;
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
 * Integration tests for the {@link PaypalResource} REST controller.
 */
@SpringBootTest(classes = PaypalmngApp.class)
public class PaypalResourceIT {

    private static final String DEFAULT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_SECRET = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_CREATED_AT = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_UPDATED_AT = Instant.ofEpochMilli(-1L);

    @Autowired
    private PaypalRepository paypalRepository;

    @Autowired
    private PaypalMapper paypalMapper;

    @Autowired
    private PaypalService paypalService;

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

    private MockMvc restPaypalMockMvc;

    private Paypal paypal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaypalResource paypalResource = new PaypalResource(paypalService);
        this.restPaypalMockMvc = MockMvcBuilders.standaloneSetup(paypalResource)
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
    public static Paypal createEntity(EntityManager em) {
        Paypal paypal = new Paypal()
            .secret(DEFAULT_SECRET)
            .clientId(DEFAULT_CLIENT_ID)
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT);
        return paypal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paypal createUpdatedEntity(EntityManager em) {
        Paypal paypal = new Paypal()
            .secret(UPDATED_SECRET)
            .clientId(UPDATED_CLIENT_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        return paypal;
    }

    @BeforeEach
    public void initTest() {
        paypal = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaypal() throws Exception {
        int databaseSizeBeforeCreate = paypalRepository.findAll().size();

        // Create the Paypal
        PaypalDTO paypalDTO = paypalMapper.toDto(paypal);
        restPaypalMockMvc.perform(post("/api/paypals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalDTO)))
            .andExpect(status().isCreated());

        // Validate the Paypal in the database
        List<Paypal> paypalList = paypalRepository.findAll();
        assertThat(paypalList).hasSize(databaseSizeBeforeCreate + 1);
        Paypal testPaypal = paypalList.get(paypalList.size() - 1);
        assertThat(testPaypal.getSecret()).isEqualTo(DEFAULT_SECRET);
        assertThat(testPaypal.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testPaypal.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testPaypal.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
    }

    @Test
    @Transactional
    public void createPaypalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paypalRepository.findAll().size();

        // Create the Paypal with an existing ID
        paypal.setId(1L);
        PaypalDTO paypalDTO = paypalMapper.toDto(paypal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaypalMockMvc.perform(post("/api/paypals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paypal in the database
        List<Paypal> paypalList = paypalRepository.findAll();
        assertThat(paypalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSecretIsRequired() throws Exception {
        int databaseSizeBeforeTest = paypalRepository.findAll().size();
        // set the field null
        paypal.setSecret(null);

        // Create the Paypal, which fails.
        PaypalDTO paypalDTO = paypalMapper.toDto(paypal);

        restPaypalMockMvc.perform(post("/api/paypals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalDTO)))
            .andExpect(status().isBadRequest());

        List<Paypal> paypalList = paypalRepository.findAll();
        assertThat(paypalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkClientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paypalRepository.findAll().size();
        // set the field null
        paypal.setClientId(null);

        // Create the Paypal, which fails.
        PaypalDTO paypalDTO = paypalMapper.toDto(paypal);

        restPaypalMockMvc.perform(post("/api/paypals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalDTO)))
            .andExpect(status().isBadRequest());

        List<Paypal> paypalList = paypalRepository.findAll();
        assertThat(paypalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPaypals() throws Exception {
        // Initialize the database
        paypalRepository.saveAndFlush(paypal);

        // Get all the paypalList
        restPaypalMockMvc.perform(get("/api/paypals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paypal.getId().intValue())))
            .andExpect(jsonPath("$.[*].secret").value(hasItem(DEFAULT_SECRET.toString())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())));
    }
    
    @Test
    @Transactional
    public void getPaypal() throws Exception {
        // Initialize the database
        paypalRepository.saveAndFlush(paypal);

        // Get the paypal
        restPaypalMockMvc.perform(get("/api/paypals/{id}", paypal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paypal.getId().intValue()))
            .andExpect(jsonPath("$.secret").value(DEFAULT_SECRET.toString()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaypal() throws Exception {
        // Get the paypal
        restPaypalMockMvc.perform(get("/api/paypals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaypal() throws Exception {
        // Initialize the database
        paypalRepository.saveAndFlush(paypal);

        int databaseSizeBeforeUpdate = paypalRepository.findAll().size();

        // Update the paypal
        Paypal updatedPaypal = paypalRepository.findById(paypal.getId()).get();
        // Disconnect from session so that the updates on updatedPaypal are not directly saved in db
        em.detach(updatedPaypal);
        updatedPaypal
            .secret(UPDATED_SECRET)
            .clientId(UPDATED_CLIENT_ID)
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT);
        PaypalDTO paypalDTO = paypalMapper.toDto(updatedPaypal);

        restPaypalMockMvc.perform(put("/api/paypals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalDTO)))
            .andExpect(status().isOk());

        // Validate the Paypal in the database
        List<Paypal> paypalList = paypalRepository.findAll();
        assertThat(paypalList).hasSize(databaseSizeBeforeUpdate);
        Paypal testPaypal = paypalList.get(paypalList.size() - 1);
        assertThat(testPaypal.getSecret()).isEqualTo(UPDATED_SECRET);
        assertThat(testPaypal.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testPaypal.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testPaypal.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
    }

    @Test
    @Transactional
    public void updateNonExistingPaypal() throws Exception {
        int databaseSizeBeforeUpdate = paypalRepository.findAll().size();

        // Create the Paypal
        PaypalDTO paypalDTO = paypalMapper.toDto(paypal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaypalMockMvc.perform(put("/api/paypals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paypalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Paypal in the database
        List<Paypal> paypalList = paypalRepository.findAll();
        assertThat(paypalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaypal() throws Exception {
        // Initialize the database
        paypalRepository.saveAndFlush(paypal);

        int databaseSizeBeforeDelete = paypalRepository.findAll().size();

        // Delete the paypal
        restPaypalMockMvc.perform(delete("/api/paypals/{id}", paypal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paypal> paypalList = paypalRepository.findAll();
        assertThat(paypalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paypal.class);
        Paypal paypal1 = new Paypal();
        paypal1.setId(1L);
        Paypal paypal2 = new Paypal();
        paypal2.setId(paypal1.getId());
        assertThat(paypal1).isEqualTo(paypal2);
        paypal2.setId(2L);
        assertThat(paypal1).isNotEqualTo(paypal2);
        paypal1.setId(null);
        assertThat(paypal1).isNotEqualTo(paypal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaypalDTO.class);
        PaypalDTO paypalDTO1 = new PaypalDTO();
        paypalDTO1.setId(1L);
        PaypalDTO paypalDTO2 = new PaypalDTO();
        assertThat(paypalDTO1).isNotEqualTo(paypalDTO2);
        paypalDTO2.setId(paypalDTO1.getId());
        assertThat(paypalDTO1).isEqualTo(paypalDTO2);
        paypalDTO2.setId(2L);
        assertThat(paypalDTO1).isNotEqualTo(paypalDTO2);
        paypalDTO1.setId(null);
        assertThat(paypalDTO1).isNotEqualTo(paypalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paypalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paypalMapper.fromId(null)).isNull();
    }
}
