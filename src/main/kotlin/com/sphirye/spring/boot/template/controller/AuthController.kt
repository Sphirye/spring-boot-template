package com.sphirye.spring.boot.template.controller

import com.sphirye.spring.boot.template.entity.dto.LoginResponse
import com.sphirye.spring.boot.template.security.SessionManager
import com.sphirye.spring.boot.template.service.AuthService
import com.sphirye.spring.boot.template.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @Autowired lateinit var  authService: AuthService
    @Autowired lateinit var userService: UserService
    @Autowired lateinit var sessionManager: SessionManager

    @GetMapping("/api/test")
    fun bruh(): ResponseEntity<Authentication> {
        return ResponseEntity.status(HttpStatus.OK).body(SecurityContextHolder.getContext().authentication)
    }

    @PostMapping("/public/auth/login")
    fun login(@RequestParam("email") email: String, @RequestParam("password") password: String): ResponseEntity<LoginResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(email, password))
    }

    @GetMapping("/api/auth/check")
    fun authCheck(): ResponseEntity<Void> {
        return ResponseEntity.status(HttpStatus.OK).body(null)
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/api/super-admin-test")
    fun superAdminTest(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body("super admin")
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api/admin-test")
    fun adminTest(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body("admin")
    }

    @PreAuthorize("hasAuthority('MOD')")
    @GetMapping("/api/mod-test")
    fun modTest(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body("mod")
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/api/user-test")
    fun userTest(): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.OK).body("user")
    }
}