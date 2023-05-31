package com.kilk

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Cursor
import javafx.scene.control.*
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.stage.Screen
import kotlin.math.round

object Dashboard: VBox() {
    val pane = Pane()
    const val gridSize = 20
    val buttonList: ObservableList<ActionButton>? = FXCollections.observableArrayList()
    val screen = Screen.getPrimary()

    init {
        println("Dashboard says hi")
        Dashboard.children.addAll(MenuDeck, pane)
        setPrefSize(screen.visualBounds.width, screen.visualBounds.height)
        pane.setPrefSize(screen.visualBounds.width, screen.visualBounds.height)
        pane.style = "-fx-background-color: #451c1c"
        style = "-fx-background-color: black"
        pane.setOnMousePressed { event -> placeNode(SavedNode(NodeType.BUTTON, ActionType.PRINTLN, 0.0, event.x, event.y)) }
    }
    fun placeNode(savedNode: SavedNode) {
        val node = when (savedNode.nodeType) {
            NodeType.BUTTON -> { createButton(savedNode) }
            NodeType.TEXTFIELD -> { ActionTextField(savedNode.text, savedNode.actionType, savedNode.actionValue) }
        }

        setNodeRelocation(node)


        pane.children.add(node)
        node.setPrefSize(savedNode.width, savedNode.height)
        node.translateX = snapToGrid(gridSize, savedNode.x/* - savedNode.width*/)
        node.translateY = snapToGrid(gridSize, savedNode.y/* - savedNode.height*/)
    }
    fun createTextField() {

    }
    fun createButton(node: SavedNode): ActionButton {
        val button = ActionButton(node.text, node.actionType, node.actionValue)
        button.font = Font(node.fontSize)
        button.x = node.x
        button.y = node.y
        button.setOnMouseClicked {event ->
            if (event.button == MouseButton.PRIMARY) {
                when (button.actionType) {
                    ActionType.PRINTLN -> println(node.actionValue)
                    else -> println("idk what to do")
                }
            }
        }
        OptionsDialogue(button)
        buttonList?.add(button)
        return button
    }

    private fun buttonResize(node: Control, event: MouseEvent) {
        node.setPrefSize(snapToGrid(gridSize, node.prefWidth + (event.x - node.prefWidth)), snapToGrid(gridSize, node.prefHeight +(event.y - node.prefHeight)))
    }
    private fun buttonRelocate(node: Control, event: MouseEvent) {
//        println("${event.x}, ${event.y}....${translate.x}, ${translate.y}")
        node.translateX = snapToGrid(gridSize, node.translateX + (event.x - node.width/2))
        node.translateY = snapToGrid(gridSize, node.translateY + (event.y - node.height/2))
    }
    private fun setNodeRelocation(node: Control ) {
        node.setOnMouseDragged { event ->
            if (event.button == MouseButton.PRIMARY) {
                if (event.x + node.width/6 > node.width || event.y + node.height/6 > node.height) {
                    buttonResize(node, event)
                } else {
                    buttonRelocate(node, event)
                }
            }
        }
        node.setOnMouseMoved { event ->
            if (event.x + node.width/6 > node.width || event.y + node.height/6 > node.height) {
                scene.cursor = Cursor.SE_RESIZE
            } else {
                scene.cursor = Cursor.CROSSHAIR
            }
        }
        node.setOnMouseExited {
            scene.cursor = Cursor.DEFAULT
        }
    }
    private fun snapToGrid(gridSize: Int, value: Double) : Double { //Rounds past 1 ex: Input: (100, 666.0) Output: 700.0
        return (round(value / gridSize) * gridSize)
    }
}