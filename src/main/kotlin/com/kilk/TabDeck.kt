package com.kilk

import com.fasterxml.jackson.module.kotlin.readValue
import com.kilk.nodes.*
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.stage.Screen

object TabDeck: VBox(), Savable {
    val tabPane = TabPane()
    val tabs = tabPane.tabs

    val addTab = Tab("+")

    var editMode: Boolean = false
        set(value) {
            if (value) {
                tabs.add(addTab)
            } else {
                tabs.remove(addTab)
            }
            tabs.forEach { it.isClosable = value }
            field = value
        }
    var gridSize: Int = 10

    val screenWidth = Screen.getPrimary().visualBounds.width
    val screenHeight = Screen.getPrimary().visualBounds.height

    init {
        println("TabDeck says hi")
        TabDeck.children.addAll(MenuDeck, tabPane)

        //little dance of removing/adding tabs to make the addTab work the way I want
        addTab.setOnSelectionChanged {
            if (addTab.isSelected) {
                tabs.remove(addTab)
                tabs.add(SavableTab())
                tabs.add(addTab)
                tabPane.selectionModel.selectNext()
            }
        }

        style = "-fx-background-color: black"

        //setting tab width/height, in the future I would like to be able to change this value
        tabPane.tabMinHeight = screenHeight/30.0
        tabPane.tabMinWidth = screenWidth/20.0
        tabPane.style = "-fx-background-color: black"
        tabPane.setPrefSize(screenWidth, screenHeight)
        //if there are no tabs, create a new blank tab
        if (tabs.isEmpty()) {
            tabs.add(SavableTab())
        }
    }

    override fun getJson(): String {
        println("in TabDeck's getJson() function")
        val tabStringArray: ArrayList<String> = ArrayList()
        tabs.forEach { if (it is Savable) { tabStringArray.add(it.getJson()) } else { println("$it does not inherit from Savable") } }

        return jsonMapper.writeValueAsString(SavedTabDeck(tabStringArray))
    }
    override fun loadChildren(childrenArray: ArrayList<String>?) {
        childrenArray?.forEach {
            val tab: SavedTab = jsonMapper.readValue(it)
            val newTab = SavableTab(tab.text, tab.style, tab.children)
            tabs.add(newTab)
            tabPane.selectionModel.select(newTab)
        }
    }
}