package com.sphirye.spring.boot.template

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.stereotype.Repository

@SpringBootApplication
@EnableWebSecurity
@EnableAsync
@EnableCaching
@EnableScheduling
@EnableJpaRepositories(includeFilters = [ComponentScan.Filter(Repository::class)])
class SpringBootTemplateApplication

fun main(args: Array<String>) {
	runApplication<SpringBootTemplateApplication>(*args)
}
