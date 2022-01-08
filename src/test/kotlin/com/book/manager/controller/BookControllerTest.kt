package com.book.manager.controller

import com.book.manager.application.service.BookService
import com.book.manager.domain.model.Book
import com.book.manager.domain.model.BookWithRental
import com.book.manager.presentation.controller.BookController
import com.book.manager.presentation.controller.BookInfo
import com.book.manager.presentation.controller.GetBookDetailResponse
import com.book.manager.presentation.controller.GetBookListResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.nio.charset.StandardCharsets
import java.time.LocalDate


internal class BookControllerTest {
    private val bookService = mock<BookService>()
    private val bookController = BookController(bookService)

    @Test
    fun `getListが成功した場合、書籍情報のリストが返却される`(){
        val bookId = 100L
        val book = Book(bookId, "Kotlin", "太郎", LocalDate.now())
        val bookList = listOf(BookWithRental(book, null))

        whenever(bookService.getList()).thenReturn(bookList)

        val expectedResponse = GetBookListResponse(listOf(BookInfo(bookId, "Kotlin","太郎", false)))
        val expected = ObjectMapper().registerKotlinModule().writeValueAsString(expectedResponse)
        val mockMVC = MockMvcBuilders.standaloneSetup(bookController).build()
        val resultResponse = mockMVC.perform(get("/book/list")).andExpect(status().isOk).andReturn().response
        val result = resultResponse.getContentAsString(StandardCharsets.UTF_8)

        assertThat(expected).isEqualTo(result)
    }

    @Test
    fun `getDetailが成功した場合、bookIdに紐づく書籍情報が返却される`(){
        val bookId = 100L
        val book = Book(bookId, "Kotlin", "太郎", LocalDate.now())

        whenever(bookService.getDetail(bookId)).thenReturn(BookWithRental(book, null))

        val expectedResponse = GetBookDetailResponse(bookId, "Kotlin","太郎", LocalDate.now(), null)
        val expected = ObjectMapper().registerModule(JavaTimeModule()).registerKotlinModule().writeValueAsString(expectedResponse)
        val mockMVC = MockMvcBuilders.standaloneSetup(bookController).build()
        val resultResponse = mockMVC.perform(get("/book/detail/$bookId")).andExpect(status().isOk).andReturn().response
        val result = resultResponse.getContentAsString(StandardCharsets.UTF_8)

        assertThat(expected).isEqualTo(result)
    }
}