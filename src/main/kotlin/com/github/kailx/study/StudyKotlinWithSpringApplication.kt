package com.github.kailx.study

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class StudyKotlinWithSpringApplication

fun main(args: Array<String>) {
    SpringApplication.run(StudyKotlinWithSpringApplication::class.java, *args)
}
