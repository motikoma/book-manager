package com.book.manager.application.service

import org.assertj.core.api.Assertions
import com.book.manager.domain.model.Book
import com.book.manager.domain.model.BookWithRental
import com.book.manager.domain.repository.BookRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import java.lang.IllegalStateException
import java.time.LocalDate

internal class BookServiceTest {
    private val bookRepository = mock<BookRepository>()
    private val bookService = BookService(bookRepository)

    @Test
    fun `getList when book list is exist then return list`(){
        val book = Book(1, "Kotlin入門", "太郎", LocalDate.now())
        val bookWithRental = BookWithRental(book, null)
        val expected = listOf(bookWithRental)

        whenever(bookRepository.findAllWithRental()).thenReturn(expected)

        val result = bookService.getList()
        Assertions.assertThat(expected).isEqualTo(result)
    }

    @Test
    fun `getDetail when book is exist then return book`(){
        val bookId = 1L
        val book = Book(1, "Kotlin入門", "太郎", LocalDate.now())
        val bookWithRental = BookWithRental(book, null)
        val expected = bookWithRental

        whenever(bookRepository.findWithRental(bookId)).thenReturn(expected)

        val result = bookService.getDetail(bookId)
        Assertions.assertThat(expected).isEqualTo(result)
    }

    @Test
    fun `getDetail when book is not exist then throw Error`(){
        val bookId = 1L
        val book = Book(1, "Kotlin入門", "太郎", LocalDate.now())
        val bookWithRental = BookWithRental(book, null)

        whenever(bookRepository.findWithRental(bookId)).thenReturn(null)

        val exception = org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException::class.java){
            bookService.getDetail(bookId)
        }

        Assertions.assertThat(exception.message).isEqualTo("存在しない書籍ID: $bookId")
    }
}