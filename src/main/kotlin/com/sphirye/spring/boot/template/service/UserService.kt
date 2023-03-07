package com.sphirye.spring.boot.template.service

import com.sphirye.lonjas.config.exception.DuplicatedException
import com.sphirye.lonjas.config.exception.NotFoundException
import com.sphirye.spring.boot.template.config.PropertyConfig
import com.sphirye.spring.boot.template.entity.User
import com.sphirye.spring.boot.template.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService {
    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var authorityService: AuthorityService
    @Autowired lateinit var passwordEncoder: PasswordEncoder

    fun init() {
        if (userRepository.count() <= 0) {
            create(PropertyConfig.SUDO_USERNAME , PropertyConfig.SUDO_EMAIL, PropertyConfig.SUDO_PASSWORD, "SUPER_ADMIN")
        }
    }

    @Transactional
    fun create(username: String, email: String, password: String, role: String): User {
        if (existsByUsername(username)) { throw DuplicatedException("User with $username username already exists") }

        val authority = authorityService.findByRole(role)
        val user = User(
            username = username,
            email = email,
            password = passwordEncoder.encode(password),
            authorities = setOf(authority),
            enabled = true
        )

        return userRepository.save(user)
    }

    fun setStatus(id: Long, activated: Boolean): User {
        var user = findById(id)
        user.enabled = false
        return userRepository.save(user)
    }

    fun findByUsername(username: String): User {
        if (!existsByUsername(username)) { throw NotFoundException("User $username username not found") }
        return userRepository.findByUsername(username)
    }

    fun findByEmail(email: String): User {
        if (!existsByEmail(email)) { throw NotFoundException("User with $email email not found") }
        return userRepository.findByEmail(email)
    }

    fun findById(id: Long): User {
        if (!existsById(id)) { throw NotFoundException("User with $id id not found") }
        return userRepository.getReferenceById(id)
    }

    fun existsByUsername(username: String): Boolean { return userRepository.existsByUsername(username) }
    fun existsById(id: Long): Boolean { return userRepository.existsById(id) }
    fun existsByEmail(email: String): Boolean { return userRepository.existsByEmail(email) }

}