package com.sphirye.spring.boot.template.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.io.Serializable
import java.util.stream.Collectors
import javax.persistence.*
import javax.validation.constraints.Email

@Entity
@Table(
    name = "`USER`",
    uniqueConstraints = [UniqueConstraint(columnNames = [User_.EMAIL])]
)
class User (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Email(message = "Invalid email")
    var email: String? = null,
    var username: String? = null,
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @JsonIgnore
    var password: String? = null,
    var enabled: Boolean = false,
    @ManyToMany @JoinTable(
        name = "user_authority",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role", referencedColumnName = "role")]
    )
    @JsonIgnore
    var authorities: Set<Authority>? = null
): Serializable