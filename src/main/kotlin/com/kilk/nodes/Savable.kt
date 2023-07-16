package com.kilk.nodes

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

interface Savable {
    val jsonMapper: ObjectMapper
        get() = jacksonObjectMapper()

    fun getJson(): String { return "getJson() function not implemented" }
    fun loadJson(json: String) {}
}