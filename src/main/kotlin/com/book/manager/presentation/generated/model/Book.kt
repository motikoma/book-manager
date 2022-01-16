package com.book.manager.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 * @param id 書籍ID
 * @param title 書籍名
 * @param author 著者
 * @param releaseDate 発売日
 */
data class Book(

    @field:JsonProperty("id") var id: kotlin.Int? = null,

    @field:JsonProperty("title") var title: kotlin.String? = null,

    @field:JsonProperty("author") var author: kotlin.String? = null,

    @field:JsonProperty("releaseDate") var releaseDate: kotlin.String? = null
) {

}

