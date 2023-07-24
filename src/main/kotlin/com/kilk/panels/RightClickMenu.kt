package com.kilk.panels

import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.Pane

class RightClickMenu(node: Any, content: Node): ContextMenu() {
    //creates a "dialog window"
    val settingsWindow = Dialog<String>()

    //right-click options
    val settingsItem = MenuItem("Settings")
    val deleteItem = MenuItem("Remove")

    init {
        //set action to open the settingsWindow
        settingsItem.setOnAction {
            settingsWindow.showAndWait()
        }
        //set action to remove this button from the parent Pane
        deleteItem.setOnAction {
            if (node is Node) {
                if (node.parent is Pane) {
                    (node.parent as Pane).children.remove(node)
                }
            } else if (node is Tab) {
                node.tabPane.tabs.remove(node)
            }
        }

        //adding the MenuItems to the right click menu
        this.items.addAll(settingsItem, deleteItem)

        settingsWindow.dialogPane.buttonTypes.add(ButtonType("Ok", ButtonBar.ButtonData.OK_DONE))
        settingsWindow.headerText = "Settings for ${node.javaClass.name.removePrefix(node.javaClass.`package`.toString().removePrefix("package ") + ".")}"
        println(Pair(node.javaClass.`package`.toString().removePrefix("package"), node.javaClass.name))
        println("${node.javaClass.`package`}")
        //sets the content of the dialog window
        settingsWindow.dialogPane.content = content
    }
}