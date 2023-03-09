package com.sphirye.spring.boot.template.service

import com.sphirye.spring.boot.template.entity.Post
import com.sphirye.spring.boot.template.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostService {

    @Autowired lateinit var postRepository: PostRepository

    fun create(text: String): Post {
        val post = Post()
        post.text = text
        return postRepository.save(post)
    }

    fun existsById(id: Long): Boolean { return postRepository.existsById(id) }

}