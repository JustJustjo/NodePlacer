package com.kilk

import com.kilk.nodes.*
import javafx.stage.FileChooser

object NodeSaver {
    val fileChooser = FileChooser()
    val savedNodeList: HashMap<String, Any> = HashMap()

    init {
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("NodePlacer files", "*.json"))

        savedNodeList["ActionButton"]        = ActionButton::class.java
        savedNodeList["ActionToggleButton"]  = ActionToggleButton::class.java
        savedNodeList["ActionTextBox"]       = ActionTextBox::class.java
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