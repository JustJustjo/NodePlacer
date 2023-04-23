package com.kilk

import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.control.ButtonBar.ButtonData
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import kotlin.math.round

object Dashboard: VBox() {
    val pane = AnchorPane()
    const val gridSize = 15
    init {
        println("Dashboard says hi")
        Dashboard.children.add(pane)
        Dashboard.alignment = Pos.TOP_CENTER
        pane.isVisible = true
        pane.setPrefSize(1.0, 1000.0)
        pane.style = "-fx-background-color: gray"
        pane.setOnMousePressed { event -> clicked(event) }
    }
    fun clicked(clickEvent: MouseEvent) {
        println("${clickEvent.x}, ${clickEvent.y}")
        val button = Button("HI")
        val buttonContextMenu = ContextMenu()
        val optionsItem = MenuItem("Options")
        val deleteItem = MenuItem("Remove")
        val settings = Dialog<String>()
        val buttonType = ButtonType("Ok", ButtonData.OK_DONE)
        settings.dialogPane.buttonTypes.add(buttonType)
        settings.headerText = "Settings"
        settings.dialogPane.content = NodeOptions(button)


        optionsItem.setOnAction {
            settings.showAndWait()
        }
        deleteItem.setOnAction {
            pane.children.remove(button)
        }

        button.setOnMouseClicked {event ->
            val pressedButton = event.button
            if (pressedButton == MouseButton.PRIMARY) {
                println(button.text)
            }
        }
        button.setOnMouseDragged {event ->
            if (event.x + button.width/6 > button.width || event.y + button.height/6 > button.height) {
                buttonResize(button, event)
            } else {
                buttonRelocate(button, event)
            }
        }
        buttonContextMenu.items.addAll(optionsItem, deleteItem)
        button.contextMenu = buttonContextMenu
        println(clickEvent.x - button.width)
        println(roundToLarge(gridSize, clickEvent.x - button.width))
        AnchorPane.setLeftAnchor(button, roundToLarge(gridSize, clickEvent.x - button.width))
        AnchorPane.setTopAnchor(button, roundToLarge(gridSize, clickEvent.y - button.height))
        pane.children.add(button)
    }
    fun buttonResize(button: Button,  event: MouseEvent) {
        button.setPrefSize(roundToLarge(gridSize, button.prefWidth + (event.x - button.prefWidth)), roundToLarge(gridSize, button.prefHeight +(event.y - button.prefHeight)))
    }
    fun buttonRelocate(button: Button,  event: MouseEvent) {
        println("${event.x}, ${event.y}....${button.layoutX}")
        AnchorPane.setLeftAnchor(button, roundToLarge(gridSize, button.layoutX + (event.x - button.width/2)))
        AnchorPane.setTopAnchor(button, roundToLarge(gridSize, button.layoutY + (event.y - button.height/2)))
    }
    fun roundToLarge(int: Int, value: Double) : Double { //Rounds past 1 ex: Input: (100, 666.0) Output: 700.0
        return (round(value / int) * int)
    }
}