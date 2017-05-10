package com.github.kailx.study.service

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertTrue

/**
 * @author KaiLx
 */
class MemoServiceSpec : Spek({
    val service = MemoService()

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
})