package com.kilk

import com.fasterxml.jackson.module.kotlin.readValue
import com.kilk.TabDeck.jsonMapper
import com.kilk.nodes.*
import javafx.scene.Node
import javafx.stage.FileChooser

object NodeSaver {
    val fileChooser = FileChooser()
    val savedNodeList: HashMap<String, Class<out Savable>> = HashMap()

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
    fun returnNode(nodeName: String, data: String): Node? {
        return when (nodeName) {
            "ActionButton" -> createActionButton(data)
            "ActionTextBox" -> createActionTextBox(data)
            "ActionToggleButton" -> createActionToggleButton(data)
            else -> null
        }
    }

    fun createActionButton(data: String): ActionButton {
        val b: ActionButton.SavedActionButton = jsonMapper.readValue(data)
        return ActionButton(b.x, b.y, b.width, b.height, b.text, b.buttonType, b.publishAction, b.defaultValue, b.actionValue, b.style, b.showValueAsText, b.entryKey)
    }
    fun createActionTextBox(data: String): ActionTextBox {
        val d: ActionTextBox.SavedTextBox = jsonMapper.readValue(data)
        return ActionTextBox(d.x, d.y, d.width, d.height, d.text, d.textBoxType, d.publishAction, d.style, d.entryKey)
    }
    fun createActionToggleButton(data: String): ActionToggleButton {
        val d: ActionToggleButton.SavedActionToggleButton = jsonMapper.readValue(data)
        return ActionToggleButton(d.x, d.y, d.width, d.height, d.text, d.publishAction, d.defaultValue, d.tstyle, d.fstyle, d.showValueAsText, d.entryKey)
    }
}