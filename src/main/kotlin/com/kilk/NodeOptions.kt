package com.kilk

import javafx.scene.control.Button
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.scene.text.Font

class NodeOptions(button: Button): VBox() {
    val nameInput = TextField(button.text)
    val textSlider = Slider(15.0, 400.0, 15.0)
    init {
        nameInput.setOnKeyTyped {
            button.text = nameInput.text
        }
        textSlider.setOnMouseDragged {
            button.font = Font(textSlider.value)
        }
        println(button.font)
        children.addAll(nameInput, textSlider)
    }
}