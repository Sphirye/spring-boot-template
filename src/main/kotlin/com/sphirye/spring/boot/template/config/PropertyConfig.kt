package com.sphirye.spring.boot.template.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class PropertyConfig {

    companion object {
        lateinit var SUDO_USERNAME: String
        lateinit var SUDO_EMAIL: String
        lateinit var SUDO_PASSWORD: String
    }

    @Value("\${custom.sudo-username}") lateinit var sudoUsername: String
    @Value("\${custom.sudo-password}") lateinit var sudoPassword: String
    @Value("\${custom.sudo-email}") lateinit var sudoEmail: String

    fun init() {
        SUDO_USERNAME = sudoUsername
        SUDO_PASSWORD = sudoPassword
        SUDO_EMAIL = sudoEmail
    }
}