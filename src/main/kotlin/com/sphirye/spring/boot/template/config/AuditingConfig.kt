package com.sphirye.spring.boot.template.config

import com.sphirye.spring.boot.template.entity.User
import com.sphirye.spring.boot.template.security.SessionManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditingConfig")
class AuditingConfig : AuditorAware<User> {

    @Autowired lateinit var sessionManager: SessionManager

    override fun getCurrentAuditor(): Optional<User> {
        if (sessionManager.isAuthenticated()) {
            val user = User()
            user.id = sessionManager.getUserDetails().id
            return Optional.of(user)
        } else {
            return Optional.empty()
        }
    }
}