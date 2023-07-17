package com.kilk.panels

import com.kilk.nodes.ActionTextBox
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.scene.text.Font

class ActionTextBoxSettingsPanel(textBox: ActionTextBox): VBox() {
    //textbox to change the text of parent textbox
    val textInput = TextField(textBox.text)
    //slider to change font size
    val textSizeSlider = Slider(15.0, 400.0, 15.0)
    init {
        textInput.setOnKeyTyped {
            textBox.text = textInput.text
        }
        textSizeSlider.setOnMouseDragged {
            textBox.font = Font(textSizeSlider.value)
        }
        children.addAll(textInput, textSizeSlider)
    }
}