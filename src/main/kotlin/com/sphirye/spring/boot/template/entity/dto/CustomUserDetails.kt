package com.sphirye.spring.boot.template.entity.dto

import com.google.gson.annotations.JsonAdapter
import com.sphirye.spring.boot.template.service.connector.adapter.SimpleGrantedAuthorityCollectionAdapter
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class CustomUserDetails(
    private val id: Long?,
    private val username: String?,
    private val email: String?,
    private val password: String?,
    private val authorities: Collection<SimpleGrantedAuthority>?
) : UserDetails {

    override fun getUsername(): String? {
        return username
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return authorities
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    fun toCustomUserTokenDetails(): CustomUserTokenDetails {
        return CustomUserTokenDetails(id, email, authorities)
    }
}

class CustomUserTokenDetails(
    val id: Long?,
    val email: String?,
    @JsonAdapter(SimpleGrantedAuthorityCollectionAdapter::class)
    val authorities: Collection<SimpleGrantedAuthority>?
)