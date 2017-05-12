package com.github.kailx.study.controller.page

import com.github.kailx.study.model.Memo
import com.github.kailx.study.service.MemoService
import com.nhaarman.mockito_kotlin.*
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
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
    val controller: MemoController = spy(MemoController(memoService))
    val mvc = MockMvcBuilders.standaloneSetup(controller).build()

    describe("/memoにGETでアクセスした時") {
        val result = mvc.perform(get("/memo/")).andReturn()

        it("HTTPステータスコードが200で返却される") {
            assertEquals(200, result.response.status)
        }
    }

    describe("/memo/paramにGETでアクセスした時") {
        on("PathVariableが与えられていない場合") {
            val result = mvc.perform(get("/memo/param")).andReturn()

            it("return status code 200") {
                assertEquals(200, result.response.status)
            }
            it("calls MemoController#get(String, Model)") {
                verify(controller, times(1)).get(eq("param"), any())
            }
        }
        on("PathVariableに英数字のみが与えられていた場合") {
            val result = mvc.perform(get("/memo/param/variable1234")).andReturn()

            it("HTTPステータスコードが200で返却される") {
                assertEquals(200, result.response.status)
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
        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("memo", memoValue)
        params.add("author", authorName)

        it("redirect to /memo") {
            mvc.perform(post("/memo").params(params)).andExpect {
                redirectedUrl("/memo")
            }
        }
        it("calls MemoService#write with variables($memoValue, $authorName)") {
            verify(memoService, times(1)).write(memoValue, authorName)
        }
    }
})