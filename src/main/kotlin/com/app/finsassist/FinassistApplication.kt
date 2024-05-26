package com.app.finsassist

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class FinassistApplication

fun main(args: Array<String>) {
    runApplication<FinassistApplication>(*args)
}
