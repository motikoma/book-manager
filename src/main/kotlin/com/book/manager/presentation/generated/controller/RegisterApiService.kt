package com.book.manager.controller

import com.book.manager.model.Book

interface RegisterApiService {

    fun adminBookRegister(book: Book): Unit
}
