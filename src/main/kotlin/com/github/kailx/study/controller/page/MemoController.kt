package com.github.kailx.study.controller.page

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

/**
 * @author KaiLx
 */
@Controller
@RequestMapping("memo")
class MemoController {
    
    @RequestMapping("")
    fun get(model: Model): String {
        val items: MutableList<MutableMap<String, Any>> = mutableListOf()
        val item: MutableMap<String, Any> = mutableMapOf()
        item["memo"] = "Empty Memo"
        item["author"] = "Empty Author"
        items += item

        model.addAttribute("items", items)
        return "memo"
    }
}