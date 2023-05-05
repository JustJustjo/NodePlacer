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
import kotlin.math.round

object Dashboard: VBox() {
    val pane = Pane()
    const val gridSize = 20
    val buttonList: ObservableList<Button>? = FXCollections.observableArrayList()

    init {
        println("Dashboard says hi")
        Dashboard.children.addAll(MenuDeck, pane)
        pane.setPrefSize(1.0, 1000.0)
        pane.style = "-fx-background-color: gray"
        pane.setOnMousePressed { event -> placeButton(SavedNode(event.x, event.y)) }
    }
    fun placeButton(node: SavedNode) {
        println("creating button at (${node.x}, ${node.y})")
        val button = Button(node.text)
        button.font = Font(node.fontSize)

        button.setOnMouseClicked {event ->
            if (event.button == MouseButton.PRIMARY) {
                println(button.text)
            }
        }

        setButtonRelocation(button)
        OptionsDialogue(button)

        pane.children.add(button)
        buttonList?.add(button)
        button.setPrefSize(node.width, node.height)
        button.translateX = roundToLarge(gridSize, node.x - button.width)
        button.translateY = roundToLarge(gridSize, node.y - button.height)
    }

    private fun buttonResize(button: Button,  event: MouseEvent) {
        button.setPrefSize(roundToLarge(gridSize, button.prefWidth + (event.x - button.prefWidth)), roundToLarge(gridSize, button.prefHeight +(event.y - button.prefHeight)))
    }
    private fun buttonRelocate(button: Button,  event: MouseEvent) {
//        println("${event.x}, ${event.y}....${translate.x}, ${translate.y}")
        button.translateX = roundToLarge(gridSize, button.translateX + (event.x - button.width/2))
        button.translateY = roundToLarge(gridSize, button.translateY + (event.y - button.height/2))
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
    private fun roundToLarge(int: Int, value: Double) : Double { //Rounds past 1 ex: Input: (100, 666.0) Output: 700.0
        return (round(value / int) * int)
    }
}