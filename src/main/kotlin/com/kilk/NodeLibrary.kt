package com.kilk

import com.kilk.nodes.ActionButton
import com.kilk.nodes.ActionTextBox
import com.kilk.nodes.ActionToggleButton
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.text.Font

object NodeLibrary: VBox(5.0) {
    val libraryLabel = Label("Node Library")
    val actionButton = ActionButton(null, null, 150.0, 150.0, "Action Button", isDisplay = true)
    val toggleButton = ActionToggleButton(null, null, 150.0, 150.0, "Toggle Button", isDisplay = true)
    val textBox = ActionTextBox(null, null, 150.0, 150.0, "Text Box", isDisplay = true)

    init {
        actionButton.setOnMouseDragged {
            println(listOf(this.translateX, this.translateY))
        }
        libraryLabel.font = Font(25.0)
        this.style = "-fx-background-color: #4287f5"
        this.alignment = Pos.CENTER
        this.children.addAll(libraryLabel, actionButton, toggleButton, textBox)
    }
}