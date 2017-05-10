package com.github.kailx.study.service

import com.github.kailx.study.model.Memo
import org.springframework.stereotype.Service
import java.util.*


/**
 * @author KaiLx
 */
@Service
class MemoService {
    fun join(memo: String, author: String): Memo = Memo(memo, author, Date())
}