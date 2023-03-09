package com.sphirye.spring.boot.template.repository

import com.sphirye.spring.boot.template.entity.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    fun findByUsername(username: String): User
    fun existsByUsername(username: String): Boolean

    fun findByEmail(email: String): User
    fun existsByEmail(email: String): Boolean

    @EntityGraph(attributePaths = ["authorities"])
    fun findOneWithAuthoritiesByUsername(username: String): Optional<User>

    @EntityGraph(attributePaths = ["authorities"])
    fun findOneWithAuthoritiesByEmail(email: String): Optional<User>

    override fun findAll(spec: Specification<User>?, pageable: Pageable): Page<User>

}