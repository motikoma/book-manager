package com.book.manager.application.service

import com.book.manager.domain.model.Rental
import com.book.manager.domain.repository.BookRepository
import com.book.manager.domain.repository.RentalRepository
import com.book.manager.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

// 貸出期間
private const val RENTAL_TERM_DAYS = 14L

@Service
class RentalService (
    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val rentalRepository: RentalRepository
){
    @Transactional
    fun startRental(bookId: Long, userId: Long){
        userRepository.find(userId)?: throw IllegalArgumentException("該当するユーザが存在しません。userId:${userId}")
        val book = bookRepository.findWithRental(bookId)?: throw IllegalArgumentException("該当する書籍が存在しません。bookId:${bookId}")

        // 貸出中のチェック
        if(book.isRental){
            throw IllegalStateException("書籍は貸出中です。bookId:${bookId}")
        }

        val rentalDateTime = LocalDateTime.now()
        val returnDeadline = rentalDateTime.plusDays(RENTAL_TERM_DAYS)
        val rental = Rental(bookId, userId, rentalDateTime, returnDeadline)
        rentalRepository.startRental(rental)
    }

    @Transactional
    fun endRental(bookId: Long, userId: Long){
        userRepository.find(userId)?: throw IllegalArgumentException("該当するユーザが存在しません。userId:${userId}")
        val book = bookRepository.findWithRental(bookId)?: throw IllegalArgumentException("該当する書籍が存在しません。bookId:${bookId}")

        // 貸出中のチェック
        if(!book.isRental){
            throw IllegalStateException("書籍は貸出中ではありません。bookId:${bookId}")
        }

        // 貸出者のチェック
        if(book.rental!!.userId!= userId){
            throw IllegalStateException("他のユーザーが貸出中の書籍です。bookId:${bookId}, userId:${userId}")
        }

        rentalRepository.endRental(bookId)
    }
}