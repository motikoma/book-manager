package com.book.manager.controller

import com.book.manager.application.service.UserService
import com.book.manager.domain.enum.RoleType
import com.book.manager.domain.model.User
import com.book.manager.presentation.controller.UserController
import com.book.manager.presentation.response.GetUserListResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.nio.charset.StandardCharsets

internal class UserControllerTest {
    private val UserService = mock<UserService>()
    private val userController = UserController(UserService)

    @Test
    fun `getUserListが成功した場合、登録ユーザーの一覧が返却される`(){
        val user = User(1, "hoge@gmail.com", "password", "name", RoleType.USER)
        val userList = listOf(user)

        whenever(UserService.getList()).thenReturn(userList)

        val expectedResponse = GetUserListResponse(userList)
        val expected = ObjectMapper().registerKotlinModule().writeValueAsString(expectedResponse)

        val mockMVC = MockMvcBuilders.standaloneSetup(userController).build()
        val resultResponse = mockMVC.perform(get("/user/list")).andExpect(status().isOk).andReturn().response
        val result = resultResponse.getContentAsString(StandardCharsets.UTF_8)

        assertThat(expected).isEqualTo(result)
    }
}