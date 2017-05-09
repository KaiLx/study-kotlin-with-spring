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
        val items = arrayListOf<MutableMap<String, Any>>()
        val item = hashMapOf<String, Any>()
        item.put("memo", "Empty Memo")
        item.put("author", "Empty Author")
        items.add(item)

        model.addAttribute("items", items)
        return "memo"
    }
}