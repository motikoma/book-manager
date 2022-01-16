package com.book.manager.controller

import com.book.manager.model.Book
import com.book.manager.model.InlineObject

interface AdminApiService {

    fun adminBookRegister(book: Book): kotlin.Any

    fun delete(inlineObject: InlineObject): kotlin.Any

    fun update(book: Book): kotlin.Any
}
