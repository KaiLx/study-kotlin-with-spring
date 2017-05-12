package com.github.kailx.study.repository.jdbc

import com.github.kailx.study.createJdbcTemplate
import com.github.kailx.study.doFlywayMigration
import com.github.kailx.study.model.Memo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.data_driven.data
import org.jetbrains.spek.data_driven.on
import java.util.*
import kotlin.test.assertTrue

/**
 * @author KaiLx
 */
class JdbcMemoRepositorySpec : Spek({

    doFlywayMigration()

    val jdbcTemplate = createJdbcTemplate()
    val repository = JdbcMemoRepository(jdbcTemplate)

    given("全てのメモを取得する") {
        val allMemos = repository.find()

        on("件数を取得する") {
            it("登録件数は4件") {
                assert(allMemos.size == 4)
            }
        }

        on("データの中身を確認する") {
            it("作成日時の昇順になっている") {
                assert(listOf("Springを学ぶ", "Thymeleafを学ぶ", "Flywayを学ぶ", "AspectJを生麩") ==
                        allMemos.map { it.memo }.toList())
            }
        }

    }


    given("authorを指定してメモを取得する") {
        on("authorが%sの時",
                data("金次郎", expected = listOf("Springを学ぶ", "Thymeleafを学ぶ", "Flywayを学ぶ")),
                data("金字塔", expected = listOf("AspectJを生麩"))) { author, expect ->
            val memos = repository.findByAuthor(author)

            it("取得件数は${expect.size}件") {
                assert(expect.size == memos.size)
            }

            it("期待通りのものが昇順で取得される") {
                assert(expect == memos.map { it.memo }.toList())
            }
        }
    }

    given("メモを保存する") {
        val item = Memo("memo", "author", Date())
        repository.save(item)

        on("メモの内容を$item とする") {
            it("入力どおりの内容で保存される") {
                assertTrue {
                    val lastSaved = repository.find().last()
                    lastSaved.memo == item.memo && lastSaved.author == item.author
                }
            }
        }
    }
})