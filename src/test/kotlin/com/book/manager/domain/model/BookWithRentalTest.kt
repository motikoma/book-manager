package com.book.manager.domain.model

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

internal class BookWithRentalTest {
    @Test
    fun `isRental when rental is null then return false`() {
        val book = Book(
            id = 1,
            title = "title",
            author = "author",
            releaseDate = LocalDate.now()
        )
        val bookWithRental = BookWithRental(book, null)
        Assertions.assertThat(bookWithRental.isRental).isEqualTo(false)
    }

    @Test
    fun `isRental when rental is not null then return true`(){
        val book = Book(1, "title", "author", LocalDate.now())
        val rental = Rental(1, 1, LocalDateTime.now(), LocalDateTime.MAX)
        val bookWithRental = BookWithRental(book, rental)
        Assertions.assertThat(bookWithRental.isRental).isEqualTo(true)
    }
}