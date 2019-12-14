package com.paypal.mng.service;

import com.paypal.mng.domain.Store;
import com.paypal.mng.repository.StoreRepository;
import com.paypal.mng.service.dto.shopify.OrderList;
import com.paypal.mng.service.external.ShopifyApiClient;
import com.paypal.mng.service.external.ShopifyApiClientImpl;
import com.paypal.mng.service.impl.ShopifyServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@ExtendWith({MockitoExtension.class})
public class ShopifyServiceTest {

    @Mock
    StoreRepository storeRepository;

    ShopifyApiClient shopifyApiClient;

    ShopifyService shopifyService;

    @BeforeEach
    void init() {
        shopifyApiClient = new ShopifyApiClientImpl(new RestTemplate());
//        shopifyService = new ShopifyServiceImpl(shopifyApiClient, storeRepository);
    }

    //    @Test
    void testGetListOrderDaily() {
        Store store = new Store();
        store.setShopifyApiUrl("https://actimazo.myshopify.com/admin/api/2019-07/");
        store.setShopifyApiKey("e79e8ddd6e77536093f7f4931c5f141e");
        store.setShopifyApiPassword("f8cc5732099fd4e52d92709f3cdd8fae");
        List<Store> allStore = Arrays.asList(store);
        Mockito.when(storeRepository.findAll()).thenReturn(allStore);
//        OrderList rs = shopifyService.getOrderDaily();
//        System.out.println(rs);
    }
}
