package com.github.kailx.study.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.ConcurrentHashMap

/**
 * @author KaiLx
 */
@Configuration
class CacheConfiguration {
    companion object {
        class CacheStoreMap<K, V>: ConcurrentHashMap<K,V>()
    }

    @Bean
    fun cacheStoreMap(): CacheStoreMap<String, Any> = CacheStoreMap()
}