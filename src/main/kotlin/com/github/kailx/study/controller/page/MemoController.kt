package com.github.kailx.study.controller.page

import com.github.kailx.study.model.Memo
import java.util.*
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author KaiLx
 */
@Controller
@RequestMapping("memo")
class MemoController {
    
    @GetMapping
    fun get(model: Model): String {
        val items = listOf(
                mapOf(
                        "memo" to "Empty Memo",
                        "author" to "Empty Author"
                )
        )
        model.addAttribute("items", items)
        return "memo"
    }

    @PostMapping
    fun post(@ModelAttribute item: Memo, model: Model): String {
        model.addAttribute("items", listOf(item))
        return "memo"
    }
}