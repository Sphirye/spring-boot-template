package com.sphirye.spring.boot.template.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SessionManager {

    fun getUser(): Any { return SecurityContextHolder.getContext().authentication.principal }

    fun getAuthentication(): Any { return SecurityContextHolder.getContext().authentication }

}