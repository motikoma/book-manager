package com.book.manager.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 * @param id 書籍ID
 */
data class InlineObject(

    @field:JsonProperty("id") var id: kotlin.Int? = null
) {

}

