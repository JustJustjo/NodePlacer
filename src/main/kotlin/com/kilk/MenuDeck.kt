package com.kilk

import javafx.scene.control.Menu
import javafx.scene.control.MenuBar
import javafx.scene.control.MenuItem

object MenuDeck: MenuBar() {
    val fileMenu = Menu("File")

    val saveItem = MenuItem("Save")
    val loadItem = MenuItem("Load")
    init {
        saveItem.setOnAction {
            NodeSaver.saveButtons(Dashboard.buttonList)
        }
        loadItem.setOnAction {
            NodeSaver.loadButtons()
        }
        fileMenu.items.addAll(saveItem, loadItem)
        menus.addAll(fileMenu)
    }
}