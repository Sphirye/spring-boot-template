package com.sphirye.spring.boot.template.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.sql.Timestamp
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class Auditing(

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false)
    var createdBy: User? = null,

    @CreatedDate
    @Column(updatable = false)
    var createdAt: Timestamp? = null
): Serializable