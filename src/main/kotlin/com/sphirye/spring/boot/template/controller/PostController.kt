package com.sphirye.spring.boot.template.controller

import com.sphirye.spring.boot.template.entity.Post
import com.sphirye.spring.boot.template.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {

    @Autowired lateinit var postService: PostService

    @PostMapping("/api/post")
    fun createPost(@RequestParam text: String): ResponseEntity<Post> {
        return ResponseEntity.status(HttpStatus.OK).body(postService.create(text))
    }
}