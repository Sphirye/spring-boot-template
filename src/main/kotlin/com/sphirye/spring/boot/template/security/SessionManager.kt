package com.sphirye.spring.boot.template.security

import com.sphirye.spring.boot.template.entity.dto.CustomUserTokenDetails
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class SessionManager {

    fun getUserDetails(): CustomUserTokenDetails { return getAuthentication().details as CustomUserTokenDetails }

    fun getAuthentication(): Authentication { return SecurityContextHolder.getContext().authentication }

    fun isAuthenticated(): Boolean {
        return if (SecurityContextHolder.getContext().authentication == null) {
            false
        } else SecurityContextHolder.getContext().authentication.details is CustomUserTokenDetails
    }

}