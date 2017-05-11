package com.github.kailx.study.repository

import com.github.kailx.study.model.Memo

/**
 * @author KaiLx
 */
interface MemoRepository {
    fun find(): List<Memo>

    fun findByAuthor(author: String): List<Memo>

    fun save(memo: Memo)
}