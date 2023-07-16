package com.kilk

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

        addTab.setOnSelectionChanged {
            if (addTab.isSelected) {
                tabs.remove(addTab)
                tabs.add(SavableTab())
                tabs.add(addTab)
                tabPane.selectionModel.selectNext()
            }
        }

        style = "-fx-background-color: black"
        tabPane.tabMinHeight = screenHeight/30.0
        tabPane.tabMinWidth = screenWidth/20.0
        tabPane.style = "-fx-background-color: black"
        tabPane.setPrefSize(screenWidth, screenHeight)
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



}






//fun placeNode(savedNode: SavedNode) {
//        val node = when (savedNode.nodeType) {
//            NodeType.BUTTON -> { createButton(savedNode) }
//            NodeType.TEXTFIELD -> { ActionTextField(savedNode.text, savedNode.actionType, savedNode.actionValue) }
//        }
//
//        setNodeRelocation(node)
//
//
//        pane.children.add(node)
//        node.setPrefSize(savedNode.width, savedNode.height)
//        node.translateX = snapToGrid(gridSize, savedNode.x/* - savedNode.width*/)
//        node.translateY = snapToGrid(gridSize, savedNode.y/* - savedNode.height*/)
//    }
//    fun createButton(node: SavedNode): ActionButton {
//        val button = ActionButton(node.text, node.actionType, node.actionValue)
//        button.font = Font(node.fontSize)
//        button.x = node.x
//        button.y = node.y
//        button.setOnMouseClicked {event ->
//            if (event.button == MouseButton.PRIMARY) {
//                when (button.actionType) {
//                    ActionType.PRINTLN -> println(node.actionValue)
//                    else -> println("idk what to do")
//                }
//            }
//        }
//        OptionsDialogue(button)
//        buttonList?.add(button)
//        return button
//    }
//
//    private fun setNodeRelocation(node: Control ) {
//        node.setOnMouseDragged { event ->
//            if (event.button == MouseButton.PRIMARY) {
//                if (event.x + node.width/6 > node.width || event.y + node.height/6 > node.height) {
//                    buttonResize(node, event)
//                } else {
//                    buttonRelocate(node, event)
//                }
//            }
//        }
//        node.setOnMouseMoved { event ->
//            if (event.x + node.width/6 > node.width || event.y + node.height/6 > node.height) {
//                scene.cursor = Cursor.SE_RESIZE
//            } else {
//                scene.cursor = Cursor.CROSSHAIR
//            }
//        }
//        node.setOnMouseExited {
//            scene.cursor = Cursor.DEFAULT
//        }
//    }
