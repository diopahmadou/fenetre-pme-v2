package com.solenboutique.fenetre_pme_v2.config;

import java.time.Duration;

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
            cm.createCache(com.solenboutique.fenetre_pme_v2.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.Pme.class.getName(), jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.Pme.class.getName() + ".personneContacts", jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.Pme.class.getName() + ".prets", jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.Pme.class.getName() + ".projets", jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.Pret.class.getName(), jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.Pret.class.getName() + ".fichiers", jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.Projet.class.getName(), jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.Fichier.class.getName(), jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.Imf.class.getName(), jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.Imf.class.getName() + ".pmeGeres", jcacheConfiguration);
            cm.createCache(com.solenboutique.fenetre_pme_v2.domain.ContactPme.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
