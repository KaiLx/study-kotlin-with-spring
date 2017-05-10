package com.github.kailx.study.controller.page

import com.github.kailx.study.model.Memo
import com.github.kailx.study.service.MemoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


/**
 * @author KaiLx
 */
@Controller
@RequestMapping("memo")
class MemoController(val memoService: MemoService) {
    @GetMapping
    fun get(model: Model): String {
        model.addAttribute("items", listOf(memoService.join("Join Memo", "Join Author")))
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
        model.addAttribute("items", listOf(memoService.join(memo, author)))
        return "memo"
    }
}