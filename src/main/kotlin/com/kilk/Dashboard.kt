package com.kilk

import javafx.scene.Cursor
import javafx.scene.control.*
import javafx.scene.control.ButtonBar.ButtonData
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import javafx.scene.transform.Translate
import javafx.stage.FileChooser
import kotlin.math.round

object Dashboard: VBox() {
    val pane = Pane()
    const val gridSize = 20
    val fileChooser = FileChooser()
    init {
        println("Dashboard says hi")
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("NodePlacer files", "*.nodes"))
        Dashboard.children.addAll(pane)
        pane.isVisible = true
        pane.setPrefSize(1.0, 1000.0)
        pane.style = "-fx-background-color: gray"
        pane.setOnMousePressed { event -> clicked(event) }
    }
    fun clicked(clickEvent: MouseEvent) {
        println("${clickEvent.x}, ${clickEvent.y}")
        val button = Button("HI")
        val translate = Translate()
        val buttonContextMenu = ContextMenu()
        val optionsItem = MenuItem("Options")
        val deleteItem = MenuItem("Remove")
        val settings = Dialog<String>()
        val buttonType = ButtonType("Ok", ButtonData.OK_DONE)

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
                    buttonRelocate(button, translate, event)
                }
            }
        }

        buttonContextMenu.items.addAll(optionsItem, deleteItem)
        button.contextMenu = buttonContextMenu
        button.transforms.add(translate)

        println(clickEvent.x - button.width)
        println(roundToLarge(gridSize, clickEvent.x - button.width))

        pane.children.add(button)
        translate.x = roundToLarge(gridSize, clickEvent.x - button.width)
        translate.y = roundToLarge(gridSize, clickEvent.y - button.height)
    }
    fun buttonResize(button: Button,  event: MouseEvent) {
        button.setPrefSize(roundToLarge(gridSize, button.prefWidth + (event.x - button.prefWidth)), roundToLarge(gridSize, button.prefHeight +(event.y - button.prefHeight)))
    }
    fun buttonRelocate(button: Button, translate: Translate,  event: MouseEvent) {
        println("${event.x}, ${event.y}....${translate.x}, ${translate.y}")
        translate.x = roundToLarge(gridSize, translate.x + (event.x - button.width/2))
        translate.y = roundToLarge(gridSize, translate.y + (event.y - button.height/2))
    }
    fun roundToLarge(int: Int, value: Double) : Double { //Rounds past 1 ex: Input: (100, 666.0) Output: 700.0
        return (round(value / int) * int)
    }
}