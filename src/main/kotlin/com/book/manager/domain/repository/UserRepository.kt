package com.book.manager.domain.repository

import com.book.manager.domain.model.User

interface UserRepository {
    fun findAll(): List<User>
    fun find(email: String): User?
    fun find(id: Long): User?
}