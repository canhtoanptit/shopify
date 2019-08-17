package com.paypal.mng.web.rest;

import com.paypal.mng.PaypalmngApp;
import com.paypal.mng.domain.Order;
import com.paypal.mng.domain.Store;
import com.paypal.mng.repository.OrderRepository;
import com.paypal.mng.service.OrderService;
import com.paypal.mng.service.dto.OrderDTO;
import com.paypal.mng.service.mapper.OrderMapper;
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
 * Integration tests for the {@link OrderResource} REST controller.
 */
@SpringBootTest(classes = PaypalmngApp.class)
public class OrderResourceIT {

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_CREATED_AT = Instant.ofEpochMilli(-1L);

    private static final Instant DEFAULT_UPDATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_UPDATED_AT = Instant.ofEpochMilli(-1L);

    private static final Integer DEFAULT_ORDER_NUMBER = 1;
    private static final Integer UPDATED_ORDER_NUMBER = 2;
    private static final Integer SMALLER_ORDER_NUMBER = 1 - 1;

    private static final Long DEFAULT_SHOPIFY_ORDER_ID = 1L;
    private static final Long UPDATED_SHOPIFY_ORDER_ID = 2L;
    private static final Long SMALLER_SHOPIFY_ORDER_ID = 1L - 1L;

    private static final String DEFAULT_ORDER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NAME = "BBBBBBBBBB";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderService orderService;

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

    private MockMvc restOrderMockMvc;

    private Order order;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderResource orderResource = new OrderResource(orderService);
        this.restOrderMockMvc = MockMvcBuilders.standaloneSetup(orderResource)
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
    public static Order createEntity(EntityManager em) {
        Order order = new Order()
            .createdAt(DEFAULT_CREATED_AT)
            .updatedAt(DEFAULT_UPDATED_AT)
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .shopifyOrderId(DEFAULT_SHOPIFY_ORDER_ID)
            .orderName(DEFAULT_ORDER_NAME);
        // Add required entity
        Store store;
        if (TestUtil.findAll(em, Store.class).isEmpty()) {
            store = StoreResourceIT.createEntity(em);
            em.persist(store);
            em.flush();
        } else {
            store = TestUtil.findAll(em, Store.class).get(0);
        }
        order.setStore(store);
        return order;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createUpdatedEntity(EntityManager em) {
        Order order = new Order()
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .orderNumber(UPDATED_ORDER_NUMBER)
            .shopifyOrderId(UPDATED_SHOPIFY_ORDER_ID)
            .orderName(UPDATED_ORDER_NAME);
        // Add required entity
        Store store;
        if (TestUtil.findAll(em, Store.class).isEmpty()) {
            store = StoreResourceIT.createUpdatedEntity(em);
            em.persist(store);
            em.flush();
        } else {
            store = TestUtil.findAll(em, Store.class).get(0);
        }
        order.setStore(store);
        return order;
    }

    @BeforeEach
    public void initTest() {
        order = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testOrder.getUpdatedAt()).isEqualTo(DEFAULT_UPDATED_AT);
        assertThat(testOrder.getOrderNumber()).isEqualTo(DEFAULT_ORDER_NUMBER);
        assertThat(testOrder.getShopifyOrderId()).isEqualTo(DEFAULT_SHOPIFY_ORDER_ID);
        assertThat(testOrder.getOrderName()).isEqualTo(DEFAULT_ORDER_NAME);
    }

    @Test
    @Transactional
    public void createOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order with an existing ID
        order.setId(1L);
        OrderDTO orderDTO = orderMapper.toDto(order);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOrderNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setOrderNumber(null);

        // Create the Order, which fails.
        OrderDTO orderDTO = orderMapper.toDto(order);

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkShopifyOrderIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = orderRepository.findAll().size();
        // set the field null
        order.setShopifyOrderId(null);

        // Create the Order, which fails.
        OrderDTO orderDTO = orderMapper.toDto(order);

        restOrderMockMvc.perform(post("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].updatedAt").value(hasItem(DEFAULT_UPDATED_AT.toString())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].shopifyOrderId").value(hasItem(DEFAULT_SHOPIFY_ORDER_ID.intValue())))
            .andExpect(jsonPath("$.[*].orderName").value(hasItem(DEFAULT_ORDER_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId().intValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.updatedAt").value(DEFAULT_UPDATED_AT.toString()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.shopifyOrderId").value(DEFAULT_SHOPIFY_ORDER_ID.intValue()))
            .andExpect(jsonPath("$.orderName").value(DEFAULT_ORDER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        Order updatedOrder = orderRepository.findById(order.getId()).get();
        // Disconnect from session so that the updates on updatedOrder are not directly saved in db
        em.detach(updatedOrder);
        updatedOrder
            .createdAt(UPDATED_CREATED_AT)
            .updatedAt(UPDATED_UPDATED_AT)
            .orderNumber(UPDATED_ORDER_NUMBER)
            .shopifyOrderId(UPDATED_SHOPIFY_ORDER_ID)
            .orderName(UPDATED_ORDER_NAME);
        OrderDTO orderDTO = orderMapper.toDto(updatedOrder);

        restOrderMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testOrder.getUpdatedAt()).isEqualTo(UPDATED_UPDATED_AT);
        assertThat(testOrder.getOrderNumber()).isEqualTo(UPDATED_ORDER_NUMBER);
        assertThat(testOrder.getShopifyOrderId()).isEqualTo(UPDATED_SHOPIFY_ORDER_ID);
        assertThat(testOrder.getOrderName()).isEqualTo(UPDATED_ORDER_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Create the Order
        OrderDTO orderDTO = orderMapper.toDto(order);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc.perform(put("/api/orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Delete the order
        restOrderMockMvc.perform(delete("/api/orders/{id}", order.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Order.class);
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(order1.getId());
        assertThat(order1).isEqualTo(order2);
        order2.setId(2L);
        assertThat(order1).isNotEqualTo(order2);
        order1.setId(null);
        assertThat(order1).isNotEqualTo(order2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderDTO.class);
        OrderDTO orderDTO1 = new OrderDTO();
        orderDTO1.setId(1L);
        OrderDTO orderDTO2 = new OrderDTO();
        assertThat(orderDTO1).isNotEqualTo(orderDTO2);
        orderDTO2.setId(orderDTO1.getId());
        assertThat(orderDTO1).isEqualTo(orderDTO2);
        orderDTO2.setId(2L);
        assertThat(orderDTO1).isNotEqualTo(orderDTO2);
        orderDTO1.setId(null);
        assertThat(orderDTO1).isNotEqualTo(orderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderMapper.fromId(null)).isNull();
    }
}
