package com.sphirye.spring.boot.template.security

import com.sphirye.spring.boot.template.entity.Authority
import com.sphirye.spring.boot.template.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.stream.Collectors

@Component("userDetailsService")
class CustomUserDetailsService : UserDetailsService {

    @Autowired lateinit var userRepository: UserRepository

    @Transactional
    override fun loadUserByUsername(email: String): UserDetails {
        return userRepository.findOneWithAuthoritiesByEmail(email)
            .map { user: com.sphirye.spring.boot.template.entity.User -> createUser(email, user) }
            .orElseThrow { UsernameNotFoundException("$email -> not found in database.") }
    }

    private fun createUser(email: String, user: com.sphirye.spring.boot.template.entity.User): User {
        if (!user.enabled) { throw RuntimeException("$email -> Is not activated.") }

        val grantedAuthorities = user.authorities!!.stream()
            .map { authority: Authority -> SimpleGrantedAuthority(authority.role) }
            .collect(Collectors.toList())

        return User(user.email, user.password, grantedAuthorities)
    }
}