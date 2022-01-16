package com.book.manager.controller

import com.book.manager.model.Book
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.beans.factory.annotation.Autowired


import kotlin.collections.List
import kotlin.collections.Map

@RestController
@RequestMapping("\${api.base-path:/api/v1}")
class RegisterApiController(@Autowired(required = true) val service: RegisterApiService) {


    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/register"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun adminBookRegister(  @RequestBody book: Book
): ResponseEntity<Unit> {
        return ResponseEntity(service.adminBookRegister(book), HttpStatus.valueOf(200))
    }
}
