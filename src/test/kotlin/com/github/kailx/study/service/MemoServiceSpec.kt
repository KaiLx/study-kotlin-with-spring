package com.github.kailx.study.service

import com.github.kailx.study.repository.MemoRepository
import com.nhaarman.mockito_kotlin.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.jetbrains.spek.data_driven.data
import org.jetbrains.spek.data_driven.on
import kotlin.test.assertTrue

/**
 * @author KaiLx
 */
class MemoServiceSpec : Spek({
    val memoRepository: MemoRepository = mock()
    val service = MemoService(memoRepository)

    on("実引数を渡してjoinを実行した時") {
        val memoValue = "memo"
        val authorName = "author"
        val memo = service.join(memoValue, authorName)

        it("渡した値通りのMemoオブジェクトが作成される") {
            assertTrue {
                memo.author == authorName && memo.memo == memoValue
            }
        }
    }

    describe("when called readAll") {
        service.readAll()

        it("calls memoRepository#findAll once") {
            verify(memoRepository, times(1)).find()
        }
    }

    describe("when called readByAuthor") {
        on("author of parameter is %s", data("user", expected = "user")) { author, expect ->
            service.readByAuthor(author)

            it("calls memoRepository#findByAuthor once with expected variable") {
                verify(memoRepository, times(1)).findByAuthor(expect)
            }
        }
    }

    describe("when called write") {
        on("%s save memo to %s", data("writer", "subject", expected = "writer" to "subject")) { author, memo, expect ->
            service.write(memo, author)

            it("calls memoRepository#save once with expected variables") {
                verify(memoRepository).save(check {
                    it.author == expect.first && it.memo == expect.second
                })
            }
        }
    }
})