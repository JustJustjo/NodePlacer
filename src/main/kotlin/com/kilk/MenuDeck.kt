package com.kilk

import javafx.scene.control.CheckMenuItem
import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem
import javafx.scene.control.SeparatorMenuItem
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination

object MenuDeck: MenuBar() {
    val fileMenu = Menu("File")

    val saveItem = MenuItem("Save As...")
    val loadItem = MenuItem("Load")
    val editItem = CheckMenuItem("Edit Mode")
    init {
        saveItem.accelerator = KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN)
        saveItem.setOnAction {
            println("Save")
            NodeSaver.saveNodes()
        }
        loadItem.accelerator = KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_DOWN)
        loadItem.setOnAction {
            println("load")
            NodeSaver.loadNodes()
        }
        editItem.accelerator = KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN)
        editItem.setOnAction {
            println("Edit Toggle")
            TabDeck.editMode = !TabDeck.editMode
        }
        fileMenu.items.addAll(saveItem, loadItem, SeparatorMenuItem(), editItem)

        this.menus.addAll(fileMenu)
    }
}