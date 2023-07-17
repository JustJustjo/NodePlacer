package com.kilk.panels

import javafx.scene.control.*
import javafx.scene.layout.VBox
import kotlin.math.roundToInt

open class NodeSettingsPanel(val node: Control): VBox() {
    //textbox to change the text of button
    val textInput = TextField()
    val styleInput = TextField(node.style)
    //text size slider
    val fontSizeSlider = Slider(15.0, 400.0, 15.0)



    var textSize: Int = fontSizeSlider.value.roundToInt()
        set(value) {
            node.style = node.style.replace("-fx-font-size: ${field}px; ", "-fx-font-size: ${value}px; ")
            field = value
        }

    init {
        if (!node.style.contains("-fx-font-size:")) {
            node.style += "-fx-font-size: ${textSize}px; "
        }
        textInput.setOnKeyTyped {
            when (node) {
                is Button -> node.text = textInput.text
                is TextField -> node.text = textInput.text
            }
        }
        styleInput.setOnKeyTyped {
            node.style = styleInput.text
        }
        fontSizeSlider.setOnMouseDragged {
            textSize = fontSizeSlider.value.roundToInt()
        }
        children.addAll(textInput, fontSizeSlider, styleInput)
    }
}