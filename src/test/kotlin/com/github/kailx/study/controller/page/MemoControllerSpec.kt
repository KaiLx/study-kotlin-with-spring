package com.github.kailx.study.controller.page

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
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
})