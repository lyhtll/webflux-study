package com.example.fluxstudy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class FluxStudyApplication

fun main(args: Array<String>) {
    runApplication<FluxStudyApplication>(*args)
}
