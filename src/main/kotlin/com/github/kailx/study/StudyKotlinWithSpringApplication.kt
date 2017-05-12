package com.github.kailx.study

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class StudyKotlinWithSpringApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(StudyKotlinWithSpringApplication::class.java, *args)
        }
    }
}