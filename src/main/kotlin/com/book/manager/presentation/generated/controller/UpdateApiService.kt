package com.book.manager.controller

import com.book.manager.model.Book

interface UpdateApiService {

    fun update(book: Book): Unit
}
