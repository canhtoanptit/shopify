package com.quyenbeo.shopifym.web.rest;

import com.quyenbeo.shopifym.ShopifymApp;

import com.quyenbeo.shopifym.domain.ShopInfo;
import com.quyenbeo.shopifym.repository.ShopinfoRepository;
import com.quyenbeo.shopifym.service.ShopinfoService;
import com.quyenbeo.shopifym.service.dto.ShopinfoDTO;
import com.quyenbeo.shopifym.service.mapper.ShopinfoMapper;
import com.quyenbeo.shopifym.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static com.quyenbeo.shopifym.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ShopinfoResource REST controller.
 *
 * @see ShopinfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShopifymApp.class)
public class ShopInfoResourceIntTest {

    private static final String DEFAULT_API_KEY = "AAAAAAAAAA";
    private static final String UPDATED_API_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private ShopinfoRepository shopinfoRepository;

    @Mock
    private ShopinfoRepository shopinfoRepositoryMock;

    @Autowired
    private ShopinfoMapper shopinfoMapper;

    @Mock
    private ShopinfoService shopinfoServiceMock;

    @Autowired
    private ShopinfoService shopinfoService;

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

    private MockMvc restShopinfoMockMvc;

    private ShopInfo shopinfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShopinfoResource shopinfoResource = new ShopinfoResource(shopinfoService);
        this.restShopinfoMockMvc = MockMvcBuilders.standaloneSetup(shopinfoResource)
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
    public static ShopInfo createEntity(EntityManager em) {
        ShopInfo shopinfo = new ShopInfo()
            .apiKey(DEFAULT_API_KEY)
            .password(DEFAULT_PASSWORD)
            .url(DEFAULT_URL);
        return shopinfo;
    }

    @Before
    public void initTest() {
        shopinfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createShopinfo() throws Exception {
        int databaseSizeBeforeCreate = shopinfoRepository.findAll().size();

        // Create the ShopInfo
        ShopinfoDTO shopinfoDTO = shopinfoMapper.toDto(shopinfo);
        restShopinfoMockMvc.perform(post("/api/shopinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopinfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopinfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ShopInfo testShopInfo = shopInfoList.get(shopInfoList.size() - 1);
        assertThat(testShopInfo.getApiKey()).isEqualTo(DEFAULT_API_KEY);
        assertThat(testShopInfo.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testShopInfo.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createShopinfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shopinfoRepository.findAll().size();

        // Create the ShopInfo with an existing ID
        shopinfo.setId(1L);
        ShopinfoDTO shopinfoDTO = shopinfoMapper.toDto(shopinfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShopinfoMockMvc.perform(post("/api/shopinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopinfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopinfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkApiKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopinfoRepository.findAll().size();
        // set the field null
        shopinfo.setApiKey(null);

        // Create the ShopInfo, which fails.
        ShopinfoDTO shopinfoDTO = shopinfoMapper.toDto(shopinfo);

        restShopinfoMockMvc.perform(post("/api/shopinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopinfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShopInfo> shopInfoList = shopinfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopinfoRepository.findAll().size();
        // set the field null
        shopinfo.setPassword(null);

        // Create the ShopInfo, which fails.
        ShopinfoDTO shopinfoDTO = shopinfoMapper.toDto(shopinfo);

        restShopinfoMockMvc.perform(post("/api/shopinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopinfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShopInfo> shopInfoList = shopinfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopinfoRepository.findAll().size();
        // set the field null
        shopinfo.setUrl(null);

        // Create the ShopInfo, which fails.
        ShopinfoDTO shopinfoDTO = shopinfoMapper.toDto(shopinfo);

        restShopinfoMockMvc.perform(post("/api/shopinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopinfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShopInfo> shopInfoList = shopinfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllShopinfos() throws Exception {
        // Initialize the database
        shopinfoRepository.saveAndFlush(shopinfo);

        // Get all the shopinfoList
        restShopinfoMockMvc.perform(get("/api/shopinfos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shopinfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].apiKey").value(hasItem(DEFAULT_API_KEY.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllShopinfosWithEagerRelationshipsIsEnabled() throws Exception {
        ShopinfoResource shopinfoResource = new ShopinfoResource(shopinfoServiceMock);
        when(shopinfoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restShopinfoMockMvc = MockMvcBuilders.standaloneSetup(shopinfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restShopinfoMockMvc.perform(get("/api/shopinfos?eagerload=true"))
        .andExpect(status().isOk());

        verify(shopinfoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllShopinfosWithEagerRelationshipsIsNotEnabled() throws Exception {
        ShopinfoResource shopinfoResource = new ShopinfoResource(shopinfoServiceMock);
            when(shopinfoServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restShopinfoMockMvc = MockMvcBuilders.standaloneSetup(shopinfoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restShopinfoMockMvc.perform(get("/api/shopinfos?eagerload=true"))
        .andExpect(status().isOk());

            verify(shopinfoServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getShopinfo() throws Exception {
        // Initialize the database
        shopinfoRepository.saveAndFlush(shopinfo);

        // Get the shopinfo
        restShopinfoMockMvc.perform(get("/api/shopinfos/{id}", shopinfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shopinfo.getId().intValue()))
            .andExpect(jsonPath("$.apiKey").value(DEFAULT_API_KEY.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingShopinfo() throws Exception {
        // Get the shopinfo
        restShopinfoMockMvc.perform(get("/api/shopinfos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShopinfo() throws Exception {
        // Initialize the database
        shopinfoRepository.saveAndFlush(shopinfo);

        int databaseSizeBeforeUpdate = shopinfoRepository.findAll().size();

        // Update the shopinfo
        ShopInfo updatedShopInfo = shopinfoRepository.findById(shopinfo.getId()).get();
        // Disconnect from session so that the updates on updatedShopInfo are not directly saved in db
        em.detach(updatedShopInfo);
        updatedShopInfo
            .apiKey(UPDATED_API_KEY)
            .password(UPDATED_PASSWORD)
            .url(UPDATED_URL);
        ShopinfoDTO shopinfoDTO = shopinfoMapper.toDto(updatedShopInfo);

        restShopinfoMockMvc.perform(put("/api/shopinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopinfoDTO)))
            .andExpect(status().isOk());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopinfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate);
        ShopInfo testShopInfo = shopInfoList.get(shopInfoList.size() - 1);
        assertThat(testShopInfo.getApiKey()).isEqualTo(UPDATED_API_KEY);
        assertThat(testShopInfo.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testShopInfo.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingShopinfo() throws Exception {
        int databaseSizeBeforeUpdate = shopinfoRepository.findAll().size();

        // Create the ShopInfo
        ShopinfoDTO shopinfoDTO = shopinfoMapper.toDto(shopinfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShopinfoMockMvc.perform(put("/api/shopinfos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shopinfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopinfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShopinfo() throws Exception {
        // Initialize the database
        shopinfoRepository.saveAndFlush(shopinfo);

        int databaseSizeBeforeDelete = shopinfoRepository.findAll().size();

        // Delete the shopinfo
        restShopinfoMockMvc.perform(delete("/api/shopinfos/{id}", shopinfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ShopInfo> shopInfoList = shopinfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShopInfo.class);
        ShopInfo shopInfo1 = new ShopInfo();
        shopInfo1.setId(1L);
        ShopInfo shopInfo2 = new ShopInfo();
        shopInfo2.setId(shopInfo1.getId());
        assertThat(shopInfo1).isEqualTo(shopInfo2);
        shopInfo2.setId(2L);
        assertThat(shopInfo1).isNotEqualTo(shopInfo2);
        shopInfo1.setId(null);
        assertThat(shopInfo1).isNotEqualTo(shopInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShopinfoDTO.class);
        ShopinfoDTO shopinfoDTO1 = new ShopinfoDTO();
        shopinfoDTO1.setId(1L);
        ShopinfoDTO shopinfoDTO2 = new ShopinfoDTO();
        assertThat(shopinfoDTO1).isNotEqualTo(shopinfoDTO2);
        shopinfoDTO2.setId(shopinfoDTO1.getId());
        assertThat(shopinfoDTO1).isEqualTo(shopinfoDTO2);
        shopinfoDTO2.setId(2L);
        assertThat(shopinfoDTO1).isNotEqualTo(shopinfoDTO2);
        shopinfoDTO1.setId(null);
        assertThat(shopinfoDTO1).isNotEqualTo(shopinfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(shopinfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(shopinfoMapper.fromId(null)).isNull();
    }
}
