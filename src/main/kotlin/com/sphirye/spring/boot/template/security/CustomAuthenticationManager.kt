package com.sphirye.spring.boot.template.security

import com.sphirye.spring.boot.template.entity.Authority
import com.sphirye.spring.boot.template.entity.dto.CustomUserDetails
import com.sphirye.spring.boot.template.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.stream.Collectors

class CustomAuthenticationManager : AuthenticationManager {

    @Autowired lateinit var userService: UserService

    override fun authenticate(authentication: Authentication): Authentication {

        val email = authentication.name.toString()
        if (email.isEmpty()) { throw BadCredentialsException("invalid login details") }

        val customUserDetails: CustomUserDetails

        try {
            val user = userService.findByEmail(email)
            if (!user.enabled) { throw DisabledException("User is not activated") }

            val authorities = user.authorities!!.stream()
                .map { authority: Authority -> SimpleGrantedAuthority(authority.role) }
                .collect(Collectors.toList())

            customUserDetails = CustomUserDetails(
                username = user.username,
                password = null,
                authorities = authorities,
                id = user.id,
                email = email
            )

        } catch (exception: UsernameNotFoundException) { throw BadCredentialsException("invalid login details") }

        val successfulAuthentication = createSuccessfulAuthentication(authentication, customUserDetails)
        SecurityContextHolder.getContext().authentication = successfulAuthentication
        return successfulAuthentication
    }

    private fun createSuccessfulAuthentication(authentication: Authentication, userDetails: CustomUserDetails): Authentication {
        val token = UsernamePasswordAuthenticationToken(userDetails.username, authentication.credentials, userDetails.authorities)
        token.details = userDetails.toCustomUserTokenDetails()
        return token
    }
}