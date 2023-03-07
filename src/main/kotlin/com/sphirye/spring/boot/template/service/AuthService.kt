package com.sphirye.spring.boot.template.service

import com.sphirye.lonjas.config.exception.BadRequestException
import com.sphirye.spring.boot.template.entity.dto.LoginResponse
import com.sphirye.spring.boot.template.security.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService {

    @Autowired lateinit var userService: UserService
    @Autowired lateinit var authenticationManager: AuthenticationManager
    @Autowired lateinit var jwtTokenUtil: JwtTokenUtil
    @Autowired lateinit var passwordEncoder: PasswordEncoder

    fun login(email: String, password: String): LoginResponse {
        val user = userService.findByEmail(email)
        if (!passwordEncoder.matches(password, user.password)) { throw BadRequestException("Bad credentials") }
        val authenticate = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(email, password))
        return LoginResponse(jwtTokenUtil.generateToken(authenticate), user, user.authorities)
    }

}