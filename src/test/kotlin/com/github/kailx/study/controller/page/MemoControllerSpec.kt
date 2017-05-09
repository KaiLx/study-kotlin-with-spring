package com.github.kailx.study.controller.page

import com.github.kailx.study.model.Memo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

/**
 * @author KaiLx
 */
class MemoControllerSpec : Spek({

    // beforeSpec的なイメージ
    val mvc = MockMvcBuilders.standaloneSetup(MemoController()).build()

    describe("/memoにGETでアクセスした時") {
        val result = mvc.perform(MockMvcRequestBuilders.get("/memo/"))

        it("HTTPステータスコードが200で返却される") {
            result.andExpect(MockMvcResultMatchers.status().isOk)
        }
    }

    describe("/memoにPOSTでアクセスした時") {
        val result = mvc.perform(
                MockMvcRequestBuilders
                        .post("/memo/")
                        .param("memo", "memo")
                        .param("author", "author")
        )

        it("HTTPステータスコードが200で返却される") {
            result.andExpect(MockMvcResultMatchers.status().isOk)
        }
        it ("期待通りの値がMemoオブジェクトに格納される") {
            Assert.assertNotNull(result.andReturn().modelAndView.modelMap["items"])

            val items = result.andReturn().modelAndView.modelMap["items"] as List<*>
            assert(items.size == 1)
            
            val item = items[0] as Memo
            assert(item.memo == "memo")
            assert(item.author == "author")
        }
    }
})