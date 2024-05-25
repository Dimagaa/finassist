package com.app.finsassist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FinservApplication

fun main(args: Array<String>) {
    runApplication<FinservApplication>(*args)
}
