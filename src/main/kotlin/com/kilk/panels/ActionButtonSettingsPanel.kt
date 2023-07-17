package com.kilk.panels

import com.kilk.nodes.ActionButton
import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.text.Font

class ActionButtonSettingsPanel(button: ActionButton): VBox() {
    //textbox to change the text of button
    val textInput = TextField(button.text)
    //text size slider
    val textSizeSlider = Slider(15.0, 400.0, 15.0)
    init {
        textInput.setOnKeyTyped {
            button.text = textInput.text
        }
        textSizeSlider.setOnMouseDragged {
            button.font = Font(textSizeSlider.value)
        }
        children.addAll(textInput, textSizeSlider)
    }
}