package com.github.kailx.study.aspect

import com.github.kailx.study.config.CacheConfiguration
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * @author KaiLx
 */
@Aspect
@Component
class CacheAdvice(val cacheStoreMap: CacheConfiguration.Companion.CacheStoreMap<String, Any>) {
    companion object {
        val LOGGER: Logger = LoggerFactory.getLogger(CacheAdvice::class.java)
    }

    @Throws(Throwable::class)
    @Around(value = "com.github.kailx.study.aspect.RepositoryPointcut.findByAuthor(author)")
    fun cacheFind(proceedingJoinPoint: ProceedingJoinPoint, author: String): Any {
        LOGGER.info("signature.toLongString() ${proceedingJoinPoint.signature.toLongString()}")

        if (cacheStoreMap.containsKey(author)) {
            LOGGER.info("Cache hit")
            return cacheStoreMap[author]!!
        }

        LOGGER.info("Cache miss")
        return proceedingJoinPoint.proceed().apply {
            cacheStoreMap.put(author, this)
        }
    }
}