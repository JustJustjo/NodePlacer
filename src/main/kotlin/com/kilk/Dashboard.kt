package com.kilk

import javafx.geometry.Pos
import javafx.scene.control.Button
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox

object Dashboard: VBox() {
    val pane = AnchorPane()
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
        button.setOnMouseClicked {
            println("Hello")
        }
        button.setOnMouseDragged {event ->
            if (event.x + button.width/10 > button.width || event.y + button.height/10 > button.height) {
                buttonResize(button, event)
            } else {
                buttonRelocate(button, event)
            }
        }
        AnchorPane.setLeftAnchor(button, clickEvent.x - button.width)
        AnchorPane.setTopAnchor(button, clickEvent.y - button.height)
        pane.children.add(button)
    }
    fun buttonResize(button: Button,  event: MouseEvent) {
        button.setPrefSize(button.prefWidth + (event.x - button.prefWidth), button.prefHeight +(event.y - button.prefHeight))
    }
    fun buttonRelocate(button: Button,  event: MouseEvent) {
        println("${event.x}, ${event.y}....${button.layoutX}")
        AnchorPane.setLeftAnchor(button, button.layoutX + (event.x - button.width/2))
        AnchorPane.setTopAnchor(button, button.layoutY + (event.y - button.height/2))
    }
}