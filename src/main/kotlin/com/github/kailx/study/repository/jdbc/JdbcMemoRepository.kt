package com.github.kailx.study.repository.jdbc

import com.github.kailx.study.model.Memo
import com.github.kailx.study.repository.MemoRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository


/**
 * @author KaiLx
 */
@Repository
class JdbcMemoRepository(val jdbcTemplate: JdbcTemplate) : MemoRepository {
    override fun find(): List<Memo> {
        return jdbcTemplate.query("SELECT MEMO, AUTHOR FROM MEMO ORDER BY CREATED ASC",
                { resultSet, _ ->
                    Memo(resultSet.getString("MEMO"), resultSet.getString("AUTHOR"), null)
                })
    }

    override fun findByAuthor(author: String): List<Memo> {
        return jdbcTemplate.query("SELECT MEMO, AUTHOR FROM MEMO WHERE AUTHOR = ? ORDER BY CREATED ASC",
                RowMapper{ resultSet, _ ->
                    Memo(resultSet.getString("MEMO"), resultSet.getString("AUTHOR"), null)
                },
                author)
    }

    override fun save(memo: Memo) {
        jdbcTemplate.update(
                "INSERT INTO MEMO (MEMO, AUTHOR, CREATED) VALUES (?, ?, CURRENT_TIMESTAMP)",
                memo.memo, memo.author)
    }
}