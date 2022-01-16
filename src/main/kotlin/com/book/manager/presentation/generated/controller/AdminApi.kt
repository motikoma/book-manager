package com.book.manager.controller

import com.book.manager.model.Book
import com.book.manager.model.InlineObject
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
class AdminApiController(@Autowired(required = true) val service: AdminApiService) {


    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/admin/book/register"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun adminBookRegister(  @RequestBody book: Book
): ResponseEntity<kotlin.Any> {
        return ResponseEntity(service.adminBookRegister(book), HttpStatus.valueOf(200))
    }


    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/admin/book/delete"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun delete(  @RequestBody inlineObject: InlineObject
): ResponseEntity<kotlin.Any> {
        return ResponseEntity(service.delete(inlineObject), HttpStatus.valueOf(200))
    }


    @RequestMapping(
        method = [RequestMethod.PUT],
        value = ["/admin/book/update"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun update(  @RequestBody book: Book
): ResponseEntity<kotlin.Any> {
        return ResponseEntity(service.update(book), HttpStatus.valueOf(200))
    }
}
