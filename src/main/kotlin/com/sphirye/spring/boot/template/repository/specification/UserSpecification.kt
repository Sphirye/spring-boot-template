package com.sphirye.spring.boot.template.repository.specification

import com.sphirye.spring.boot.template.entity.User
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class UserSpecification(private val search: String) : Specification<User> {
    override fun toPredicate(root: Root<User>, query: CriteriaQuery<*>, cb: CriteriaBuilder): Predicate? {
        val pattern = "%$search%"
        return cb.or(
            cb.like(root.get("email"), pattern),
            cb.like(root.get("username"), pattern)
        )
    }
}