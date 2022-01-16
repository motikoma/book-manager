package com.book.manager.presentation.controller

import com.book.manager.application.service.UserService
import com.book.manager.presentation.response.GetUserListResponse
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("user")
@CrossOrigin
class UserController(
    private val userService: UserService
){
    @GetMapping("/list")
    fun getList(): GetUserListResponse {
        val userList = userService.getList()
        return GetUserListResponse(userList)
    }
}