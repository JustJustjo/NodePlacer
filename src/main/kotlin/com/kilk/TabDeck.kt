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

    var editMode: Boolean = true
        set(value) {
            if (value) {
                tabs.add(addTab)
            } else {
                tabs.remove(addTab)
            }
            tabs.forEach { it.isClosable = value }
            field = value
            NodeSplitPane.updateSplitPane()
        }
    var gridSize: Int = 10
    val borderWidth: String = " 10 10 10 10"

    val screenWidth = Screen.getPrimary().visualBounds.width
    val screenHeight = Screen.getPrimary().visualBounds.height

    var tabHeight = 100.0
        set(value) {
            tabPane.style = tabPane.style.replace("-fx-tab-min-height: $field; -fx-tab-max-height: $field;", "-fx-tab-min-height: $value; -fx-tab-max-height: $value;")
            println(tabPane.style)
            field = value
        }
    var tabWidth = 100.0
        set(value) {
            tabPane.style = tabPane.style.replace("-fx-tab-min-width: $field; -fx-tab-max-width: $field;", "-fx-tab-min-width: $value; -fx-tab-max-width: $value;")
            println(tabPane.style)
            field = value
        }

    init {
        println("TabDeck says hi")
        TabDeck.children.addAll(MenuDeck, tabPane)

        if (!tabPane.style.contains("-fx-tab-min-width:")) {
            tabPane.style += "-fx-tab-min-width: $tabWidth; -fx-tab-max-width: $tabWidth; -fx-tab-min-height: $tabHeight; -fx-tab-max-height: $tabHeight;"
        }

        //little dance of removing/adding tabs to make the addTab work the way I want
        addTab.setOnSelectionChanged {
            if (addTab.isSelected) {
                tabs.remove(addTab)
                tabs.add(SavableTab())
                tabs.add(addTab)
                tabPane.selectionModel.selectNext()
            }
        }


        //setting tab width/height, in the future I would like to be able to change this value
        tabPane.tabMinHeight = screenHeight/30.0
        tabPane.tabMinWidth = screenWidth/20.0
        tabPane.style += "-fx-background-color: black"
        tabPane.setPrefSize(10000.0, 10000.0)
        //if there are no tabs, create a new blank tab
        if (tabs.isEmpty()) {
            tabs.add(SavableTab())
        }
        if (editMode) {
            tabs.add(addTab)
        }
    }

    override fun getJson(): String {
        println("in TabDeck's getJson() function")
        val tabStringArray: ArrayList<String> = ArrayList()
        tabs.forEach { if (it is Savable) { tabStringArray.add(it.getJson()) } else { println("$it does not inherit from Savable") } }

        return jsonMapper.writeValueAsString(SavedTabDeck(tabStringArray, tabPane.style))
    }
    fun loadJson(file: String) {
        val savedTabDeck: SavedTabDeck = TabDeck.jsonMapper.readValue(file)
        tabPane.style = savedTabDeck.style
        loadChildren(savedTabDeck.tabs)
    }
    override fun loadChildren(childrenArray: ArrayList<String>?) {
        childrenArray?.forEach {
            val tab: SavedTab = jsonMapper.readValue(it)
            val newTab = SavableTab(tab.text, tab.style, tab.children)
            tabs.add(newTab)
            tabPane.selectionModel.select(newTab)
        }
        tabs.remove(addTab)
        tabs.add(addTab)
    }
    fun updateBorder() {
        if (!NTClient.ntInstance.isConnected) {
            style = "-fx-background-color: black; -fx-border-color: yellow; -fx-border-width: $borderWidth"
        } else {
            val color = if (NTClient.isRed) "red" else "blue"
            style = "-fx-background-color: black; -fx-border-color: $color; -fx-border-width: $borderWidth;" //-fx-border-color: #00c434;
        }
    }
}