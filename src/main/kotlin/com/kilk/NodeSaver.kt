package com.kilk

import com.fasterxml.jackson.module.kotlin.readValue
import com.kilk.nodes.SavedTabDeck
import javafx.stage.FileChooser

object NodeSaver {
    val fileChooser = FileChooser()

    init {
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("NodePlacer files", "*.json"))
    }

    fun saveNodes() {
        println("Attempting to save nodes")
        val exportFile = fileChooser.showSaveDialog(NodePlacer.stage)
        exportFile.writeText(TabDeck.getJson())
        println(exportFile.readText())
        println("Saved json to: ${exportFile.absolutePath}")
    }

    fun loadNodes() {
        val importFile = fileChooser.showOpenDialog(NodePlacer.stage)
        println(importFile.readText())
        val savedTabDeck: SavedTabDeck = TabDeck.jsonMapper.readValue(importFile.readText())
        TabDeck.loadChildren(savedTabDeck.tabs)

    }
}