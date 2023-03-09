package com.sphirye.spring.boot.template.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Post(

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    var text: String? = null

): Auditing()