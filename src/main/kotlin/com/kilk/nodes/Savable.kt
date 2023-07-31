package com.kilk.nodes

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.kilk.TabDeck
import javafx.scene.Node
import kotlin.math.round

//interface that all NodePlacer custom nodes have
interface Savable {
    val jsonMapper: ObjectMapper
        get() = jacksonObjectMapper()

    fun getJson(): String { return "getJson() function not implemented" }
    fun loadChildren(childrenArray: ArrayList<String>?) { println("loadJson() function not implemented") }

    fun snapToGrid(value: Double, gridSize: Int = TabDeck.gridSize) : Double { //Rounds past 1 ex: Input: (666.0, gridSize = 100) Output: 700.0
        return (round(value / gridSize) * gridSize)
    }
    fun createSelf(data: String): Node? {
        return null
    }
}