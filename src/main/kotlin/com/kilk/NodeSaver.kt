package com.kilk

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
        println("Attempting to load from json")
        val importFile = fileChooser.showOpenDialog(NodePlacer.stage)
        println("File path: ${importFile.absolutePath}")
        println(importFile.readText())
        TabDeck.loadJson(importFile.readText())
        println("Finished loading")
    }
}