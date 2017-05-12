package com.github.kailx.study.aspect

import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut

/**
 * @author KaiLx
 */
@Aspect
class RepositoryPointcut {
    @Pointcut("execution(* com.github.kailx.study.repository..MemoRepository+.save(..))")
    fun save() {}

    @Pointcut("execution(* com.github.kailx.study.repository..MemoRepository+.findByAuthor(..)) && args(author)")
    fun findByAuthor(author: String) {}
}