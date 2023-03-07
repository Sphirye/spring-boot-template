package com.sphirye.spring.boot.template.security

import com.sphirye.lonjas.config.exception.CustomException
import com.sphirye.spring.boot.template.security.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter : OncePerRequestFilter() {

    @Autowired lateinit var jwtTokenUtil: JwtTokenUtil

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val token: String? = jwtTokenUtil.resolveToken(request)

        try {
            if (token != null && jwtTokenUtil.validateToken(token)) {
                val auth: Authentication = jwtTokenUtil.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = auth
            }
        } catch (ex: CustomException) {
            SecurityContextHolder.clearContext()
            response.sendError(ex.httpStatus.value(), ex.message)
            return
        }

        chain.doFilter(request, response)
    }
}

