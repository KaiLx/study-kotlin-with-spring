package com.github.kailx.study.aspect

import com.github.kailx.study.model.Memo
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


/**
 * @author KaiLx
 */
@ControllerAdvice
class ExceptionControllerAdvice {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice::class.java)
    }

    @ResponseBody
    @ExceptionHandler(ExceptionControllerAdvice.MemoException::class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    fun handleException(e: MemoException): String {
        LOGGER.error("MemoException", e)
        return "error!!"
    }

    @ModelAttribute
    fun modelAttribute(model: Memo) {
        LOGGER.info("ModelAttribute")
    }

    class MemoException : RuntimeException()
}