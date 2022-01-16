package com.book.manager.presentation.response

import com.book.manager.domain.model.User

data class GetUserListResponse (
    val userList: List<User>
)