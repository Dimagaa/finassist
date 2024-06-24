package com.app.finsassist.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(
    name = "Healthcheck endpoint",
    description = "Healthcheck controller for reporting the health of app"
)
@RestController
@RequestMapping("/healthcheck")
class HealthcheckController {

    @GetMapping
    fun check(): String = "The finassist application is running stably"
}
