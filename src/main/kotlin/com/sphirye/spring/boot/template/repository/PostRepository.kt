package com.sphirye.spring.boot.template.repository

import com.sphirye.spring.boot.template.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {

}