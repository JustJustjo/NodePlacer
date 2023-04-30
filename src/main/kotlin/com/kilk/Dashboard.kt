package com.kilk

import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Cursor
import javafx.scene.control.*
import javafx.scene.control.ButtonBar.ButtonData
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import kotlin.math.round

object Dashboard: VBox() {
    val pane = Pane()
    const val gridSize = 20
    val buttonList: ObservableList<Button>? = FXCollections.observableArrayList()

    init {
        println("Dashboard says hi")
        Dashboard.children.addAll(MenuDeck, pane)
        pane.isVisible = true
        pane.setPrefSize(1.0, 1000.0)
        pane.style = "-fx-background-color: gray"
        pane.setOnMousePressed { event -> placeButton("Hi", 100.0, 100.0, event.x, event.y) }
    }
    fun placeButton(text: String, width: Double, height: Double, x: Double, y: Double) {
        println("$x, $y")
        val button = Button(text)
        val buttonContextMenu = ContextMenu()
        val optionsItem = MenuItem("Options")
        val deleteItem = MenuItem("Remove")
        val settings = Dialog<String>()
        val buttonType = ButtonType("Ok", ButtonData.OK_DONE)

        button.setPrefSize(width, height)

        settings.dialogPane.buttonTypes.add(buttonType)
        settings.headerText = "Settings"
        settings.dialogPane.content = OptionsDialogue(button)
        optionsItem.setOnAction {
            settings.showAndWait()
        }
        deleteItem.setOnAction {
            pane.children.remove(button)
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
        button.setOnMouseClicked {event ->
            val pressedButton = event.button
            if (pressedButton == MouseButton.PRIMARY) {
                println(button.text)
            }
        }
        button.setOnMouseDragged {event ->
            val pressedButton = event.button
            if (pressedButton == MouseButton.PRIMARY) {
                if (event.x + button.width/6 > button.width || event.y + button.height/6 > button.height) {
                    buttonResize(button, event)
                } else {
                    buttonRelocate(button, event)
                }
            }
        }

        buttonContextMenu.items.addAll(optionsItem, deleteItem)
        button.contextMenu = buttonContextMenu

        println(roundToLarge(gridSize, x - button.width))

        pane.children.add(button)
        buttonList?.add(button)
        button.translateX = roundToLarge(gridSize, x - button.width)
        button.translateY = roundToLarge(gridSize, y - button.height)
    }
    fun buttonResize(button: Button,  event: MouseEvent) {
        button.setPrefSize(roundToLarge(gridSize, button.prefWidth + (event.x - button.prefWidth)), roundToLarge(gridSize, button.prefHeight +(event.y - button.prefHeight)))
    }
    fun buttonRelocate(button: Button,  event: MouseEvent) {
//        println("${event.x}, ${event.y}....${translate.x}, ${translate.y}")
        button.translateX = roundToLarge(gridSize, button.translateX + (event.x - button.width/2))
        button.translateY = roundToLarge(gridSize, button.translateY + (event.y - button.height/2))
    }
    fun roundToLarge(int: Int, value: Double) : Double { //Rounds past 1 ex: Input: (100, 666.0) Output: 700.0
        return (round(value / int) * int)
    }
}