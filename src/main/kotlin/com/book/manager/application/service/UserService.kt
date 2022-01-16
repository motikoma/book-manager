package com.book.manager.application.service

import com.book.manager.domain.model.User
import com.book.manager.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService (
    private val userRepository: UserRepository
) {
    fun getList(): List<User> {
        return userRepository.findAll()
    }
}