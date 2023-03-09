package com.sphirye.spring.boot.template.controller

import com.sphirye.spring.boot.template.entity.User
import com.sphirye.spring.boot.template.repository.UserRepository
import com.sphirye.spring.boot.template.repository.specification.UserSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController {

    @Autowired lateinit var userRepository: UserRepository

    @GetMapping("/api/users")
    fun searchUsers(
        @RequestParam search: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<MutableList<User>> {
        val pageable = PageRequest.of(page, size, Sort.Direction.DESC, "id")
        val spec = UserSpecification(search)
        val result = userRepository.findAll(spec, pageable)
        return ResponseEntity.status(HttpStatus.OK).body(result.content)
    }

}