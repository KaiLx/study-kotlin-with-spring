package com.github.kailx.study.controller.page

import com.github.kailx.study.model.Memo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertNotNull
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

/**
 * @author KaiLx
 */
class MemoControllerSpec : Spek({

    // beforeSpec的なイメージ
    val mvc = MockMvcBuilders.standaloneSetup(MemoController()).build()

    describe("/memoにGETでアクセスした時") {
        val result = mvc.perform(get("/memo/"))

        it("HTTPステータスコードが200で返却される") {
            result.andExpect(status().isOk)
        }
    }

    describe("/memo/subjectにGETでアクセスした時") {
        val result = mvc.perform(get("/memo/subject"))

        it("'subject'memoとして使われる") {
            TODO()
        }
        on("authorがクエリパラメータで与えられなかった場合") {
            it("'Default Author'がauthorとして使われる") {
                TODO()
            }
        }
        on("authorがクエリパラメータで与えられた場合") {
            val author = "author"
            it("'$author'がauthorとして使われる") {
                TODO()
            }
        }
    }

    describe("/memoにPOSTでアクセスした時") {
        val result = mvc.perform(
                post("/memo/")
                        .param("memo", "memo")
                        .param("author", "author")
        )

        it("HTTPステータスコードが200で返却される") {
            result.andExpect(status().isOk)
        }
        it ("期待通りの値がMemoオブジェクトに格納される") {
            assertNotNull(result.andReturn().modelAndView.modelMap["items"])

            val items = result.andReturn().modelAndView.modelMap["items"] as List<*>
            assert(items.size == 1)
            
            val item = items[0] as Memo
            assert(item.memo == "memo")
            assert(item.author == "author")
        }
    }
})