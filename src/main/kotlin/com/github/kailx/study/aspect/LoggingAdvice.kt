package com.github.kailx.study.aspect

import com.github.kailx.study.model.Memo
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * @author KaiLx
 */
@Aspect
@Component
class LoggingAdvice {
    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(LoggingAdvice::class.java)
    }

    @Before("com.github.kailx.study.aspect.RepositoryPointcut.save()")
    fun beforeSave(joinPoint: JoinPoint) {
        LOGGER.info(joinPoint.signature.toLongString())

        joinPoint.args.firstOrNull().let {
            if (it !is Memo) return LOGGER.error("Args is invalid.")

            LOGGER.info("arg.memo   : ${it.memo}")
            LOGGER.info("arg.author : ${it.author}")
        }
    }
}