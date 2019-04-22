package com.quyenbeo.shopifym.config;

import java.time.Duration;

import com.quyenbeo.shopifym.domain.ShopInfo;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.quyenbeo.shopifym.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.quyenbeo.shopifym.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.quyenbeo.shopifym.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.quyenbeo.shopifym.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.quyenbeo.shopifym.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(ShopInfo.class.getName(), jcacheConfiguration);
            cm.createCache(ShopInfo.class.getName() + ".jhi_users", jcacheConfiguration);
            cm.createCache(ShopInfo.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.quyenbeo.shopifym.domain.Product.class.getName(), jcacheConfiguration);
            cm.createCache(com.quyenbeo.shopifym.domain.Product.class.getName() + ".shop_infos", jcacheConfiguration);
            cm.createCache(com.quyenbeo.shopifym.domain.Product.class.getName() + ".shopInfos", jcacheConfiguration);
            cm.createCache(com.quyenbeo.shopifym.domain.ShopInfo.class.getName(), jcacheConfiguration);
            cm.createCache(com.quyenbeo.shopifym.domain.ShopInfo.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.quyenbeo.shopifym.domain.ShopInfo.class.getName() + ".products", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
