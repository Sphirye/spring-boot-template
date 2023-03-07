package com.sphirye.spring.boot.template.config

import com.sphirye.spring.boot.template.service.AuthorityService
import com.sphirye.spring.boot.template.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class PopulateConfig {

    @Autowired lateinit var propertyConfig: PropertyConfig
    @Autowired lateinit var authorityService: AuthorityService
    @Autowired lateinit var userService: UserService

    @PostConstruct
    fun initDatabase() {
        propertyConfig.init()

        authorityService.init()
        userService.init()
    }
}