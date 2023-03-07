package com.sphirye.spring.boot.template.repository

import com.sphirye.spring.boot.template.entity.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepository : JpaRepository<Authority?, String?> {

    fun existsByRole(role: String): Boolean
    fun findByRole(role: String): Authority

}