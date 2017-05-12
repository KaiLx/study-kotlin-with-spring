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
        model.addAttribute("items", memoService.readAll())
        return "memo"
    }

    @GetMapping("{author}")
    fun get(@PathVariable("author") author: String, model: Model): String {
        model.addAttribute("items", memoService.readByAuthor(author))
        return "memo"
    }

    @PostMapping
    fun post(@ModelAttribute item: Memo, model: Model): String {
        item.apply {
            if (memo.isNullOrEmpty() || author.isNullOrEmpty()) return "redirect:/memo"
        }
        memoService.write(item.memo!!, item.author!!)
        return "redirect:/memo"
    }

    @GetMapping("param/{memo:[a-zA-Z0-9]+}")
    fun getParams(@PathVariable("memo") memo: String,
                  @RequestParam(required = false, defaultValue = "Default Author", value = "author") author: String,
                  model: Model): String {
        model.addAttribute("items", listOf(memoService.join(memo, author)))
        return "memo"
    }
}