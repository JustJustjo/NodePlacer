package com.kilk

import javafx.geometry.Pos
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
        pane.setOnMouseClicked { event -> clicked(event) }
    }
    fun clicked(event: MouseEvent) {
        println("${event.x}, ${event.y}")
//        val button = Button("HI")
//        pane.snapPositionX(event.x)
//        pane.snapPositionY(event.y)
    }
}