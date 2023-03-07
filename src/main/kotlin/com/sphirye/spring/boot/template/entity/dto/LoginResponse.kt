package com.sphirye.spring.boot.template.entity.dto

import com.sphirye.spring.boot.template.entity.Authority
import com.sphirye.spring.boot.template.entity.User

data class LoginResponse(
    var token: String? = null,
    var user: User? = null,
    var authorities: Set<Authority>? = null
)