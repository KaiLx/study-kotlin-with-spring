package com.github.kailx.study.controller.page

import com.github.kailx.study.model.Memo
import com.github.kailx.study.service.MemoService
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.mock
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*
import kotlin.test.assertEquals

/**
 * @author KaiLx
 */
class MemoControllerSpec : Spek({

    // beforeSpec的なイメージ
    val memoService: MemoService = mock {
        on { join(any(), any()) }.doAnswer { invocationOnMock ->
            val memo = invocationOnMock.arguments[0] as String?
            val author = invocationOnMock.arguments[1] as String?
            Memo(memo, author, Date())
        }
    }
    val mvc = MockMvcBuilders.standaloneSetup(MemoController(memoService)).build()

    describe("/memoにGETでアクセスした時") {
        val result = mvc.perform(get("/memo/")).andReturn()

        it("HTTPステータスコードが200で返却される") {
            assertEquals(200, result.response.status)
        }
    }

    describe("/memo/paramにGETでアクセスした時") {
        on("PathVariableが与えられていない場合") {
            val result = mvc.perform(get("/memo/param")).andReturn()

            it("404エラーとなる") {
                assertEquals(404, result.response.status)
            }
        }
        on("PathVariableに英数字のみが与えられていた場合") {
            val pathVariable = "variable1234"
            val result = mvc.perform(get("/memo/param/$pathVariable")).andReturn()

            it("HTTPステータスコードが200で返却される") {
                assertEquals(200, result.response.status)
            }
            it("画面に描画されるmemoは$pathVariable となる") {
                val items = result.modelAndView.modelMap["items"]!! as List<*>
                assert(items.size == 1)

                val item = items[0] as Memo
                assert(item.memo == pathVariable)
            }
        }
        on("PathVariableに記号を含む文字列が与えられていた場合") {
            val result = mvc.perform(get("/memo/param/variable_1234")).andReturn()

            it("404エラーが返却される") {
                assertEquals(404, result.response.status)
            }
        }

        on("QueryParamにauthorが与えられていない場合") {
            val result = mvc.perform(get("/memo/param/value")).andReturn()

            it("HTTPステータスコードが200で返却される") {
                assertEquals(200, result.response.status)
            }
            it("'Default Author'がauthorとして使われる") {
                val items = result.modelAndView.modelMap["items"]!! as List<*>
                assert(items.size == 1)

                val item = items[0] as Memo
                assert(item.author == "Default Author")
            }
        }
        on("QueryParamにauthorが与えられていた場合") {
            val authorName = "authorName"
            val result = mvc.perform(get("/memo/param/value").param("author", authorName)).andReturn()

            it("HTTPステータスコードが200で返却される") {
                assertEquals(200, result.response.status)
            }
            it("'$authorName'がauthorとして使われる") {
                val items = result.modelAndView.modelMap["items"]!! as List<*>
                assert(items.size == 1)

                val item = items[0] as Memo
                assert(item.author == authorName)
            }
        }
    }

    describe("/memoにPOSTでアクセスした時") {
        val memoValue = "subject"
        val authorName = "authorName"
        val result = mvc.perform(post("/memo/").param("memo", memoValue).param("author", authorName)).andReturn()

        it("HTTPステータスコードが200で返却される") {
            assertEquals(200, result.response.status)
        }
        it("期待通りの値がMemoオブジェクトに格納される") {
            val items = result.modelAndView.modelMap["items"]!! as List<*>
            assert(items.size == 1)

            val item = items[0] as Memo
            assertEquals(item.memo, memoValue)
            assertEquals(item.author, authorName)
        }
    }
})