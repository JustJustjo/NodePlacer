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
        pane.style = "-fx-background-color: darkslategrey"
        style = "-fx-background-color: black"
        pane.setOnMousePressed { event -> placeButton(SavedButton(ActionType.PRINTLN, event.x, event.y)) }
    }
    fun placeButton(node: SavedButton) {
        println("creating button at (${node.x}, ${node.y})")
        val button = ActionButton(node.text, node.actionType)
        button.font = Font(node.fontSize)

        button.setOnMouseClicked {event ->
            if (event.button == MouseButton.PRIMARY) {
                println(button.text)
            }
        }

        setButtonRelocation(button)
        OptionsDialogue(button)
        println(button.actionType)

        pane.children.add(button)
        buttonList?.add(button)
        button.setPrefSize(node.width, node.height)
        button.translateX = snapToGrid(gridSize, node.x - button.width)
        button.translateY = snapToGrid(gridSize, node.y - button.height)
    }

    private fun buttonResize(button: Button,  event: MouseEvent) {
        button.setPrefSize(snapToGrid(gridSize, button.prefWidth + (event.x - button.prefWidth)), snapToGrid(gridSize, button.prefHeight +(event.y - button.prefHeight)))
    }
    private fun buttonRelocate(button: Button,  event: MouseEvent) {
//        println("${event.x}, ${event.y}....${translate.x}, ${translate.y}")
        button.translateX = snapToGrid(gridSize, button.translateX + (event.x - button.width/2))
        button.translateY = snapToGrid(gridSize, button.translateY + (event.y - button.height/2))
    }
    private fun setButtonRelocation(button: Button) {
        button.setOnMouseDragged {event ->
            if (event.button == MouseButton.PRIMARY) {
                if (event.x + button.width/6 > button.width || event.y + button.height/6 > button.height) {
                    buttonResize(button, event)
                } else {
                    buttonRelocate(button, event)
                }
            }
        }
        button.setOnMouseMoved {event ->
            if (event.x + button.width/6 > button.width || event.y + button.height/6 > button.height) {
                scene.cursor = Cursor.SE_RESIZE
            } else {
                scene.cursor = Cursor.CROSSHAIR
            }
        }
        button.setOnMouseExited {
            scene.cursor = Cursor.DEFAULT
        }
    }
    private fun snapToGrid(gridSize: Int, value: Double) : Double { //Rounds past 1 ex: Input: (100, 666.0) Output: 700.0
        return (round(value / gridSize) * gridSize)
    }
}