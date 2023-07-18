package com.kilk.panels

import javafx.scene.control.*
import javafx.scene.layout.VBox
import kotlin.math.roundToInt

open class NodeSettingsPanel(val node: Control): VBox() {
    //textbox to change the text of button
    val textInput = TextField()
    val styleInput = TextArea(node.style)
    var prevStyleInput: String = styleInput.text
    //text size slider
    val fontSizeSlider = Slider(15.0, 400.0, 15.0)



    var textSize: Int = fontSizeSlider.value.roundToInt()
        set(value) {
            node.style = node.style.replace("-fx-font-size: ${field}px;", "-fx-font-size: ${value}px;")
            field = value
        }

    init {
        println(node.style)
        if (!node.style.contains("-fx-font-size:")) {
            node.style += "-fx-font-size: ${textSize}px;"
        }
        if (!node.style.contains(styleInput.text)) {
            node.style += "; "
            prevStyleInput = " "
        }
        textInput.setOnKeyTyped {
            when (node) {
                is Button -> node.text = textInput.text
                is TextField -> node.text = textInput.text
            }
        }
        styleInput.setOnKeyTyped {
            node.style = node.style.replace(prevStyleInput, "")
            node.style += styleInput.text
            prevStyleInput = styleInput.text
            println(Pair(node.style, prevStyleInput))
        }
        fontSizeSlider.setOnMouseDragged {
            textSize = fontSizeSlider.value.roundToInt()
        }
        children.addAll(textInput, fontSizeSlider, styleInput)
    }
}