package com.github.kailx.study.controller.page

import com.github.kailx.study.model.Memo
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


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

    @GetMapping("param/{memo:[a-zA-Z0-9]+}")
    fun getParams(@PathVariable memo: String,
                  @RequestParam(required = false, defaultValue = "Default Author") author: String,
                  model: Model): String {
        model.addAttribute("items", listOf(Memo(memo, author, null)))
        return "memo"
    }
}