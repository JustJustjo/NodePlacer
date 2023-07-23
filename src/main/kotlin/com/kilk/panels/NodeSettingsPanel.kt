package com.kilk.panels

import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.VBox
import kotlin.math.roundToInt

open class NodeSettingsPanel(val node: Control): VBox(10.0) {
    //textbox to change the text of button
    val textInput = TextField()
    val textInputLabel = Label("Text:", textInput)
    val styleInput = TextArea(node.style)
    val styleInputLabel = Label("CSS Style:", styleInput)
    var prevStyleInput: String = styleInput.text
    //text size slider
    val fontSizeSlider = Slider(15.0, 400.0, 15.0)
    val fontSizeSliderLabel = Label("Text Size:", fontSizeSlider)



    var textSize: Int = fontSizeSlider.value.roundToInt()
        set(value) {
            node.style = node.style.replace("-fx-font-size: ${field}px", "-fx-font-size: ${value}px")
            println(node.style)
            field = value
        }

    val labelList = listOf(fontSizeSliderLabel, textInputLabel, styleInputLabel)

    init {
        for (label in labelList) {
            label.contentDisplay = ContentDisplay.BOTTOM
        }
        if (!node.style.contains("-fx-font-size:")) {
            node.style += "-fx-font-size: ${textSize}px;"
        }
        if (!node.style.contains(styleInput.text)) {
            node.style += "; "
            prevStyleInput = "#"
        }

        textInput.setOnKeyTyped {
            when (node) {
                is Button -> node.text = textInput.text
                is TextField -> node.text = textInput.text
            }
        }
        styleInput.setOnKeyTyped {
            node.style = node.style.removeSuffix(prevStyleInput)
            node.style += styleInput.text
            prevStyleInput = styleInput.text
        }
        fontSizeSlider.setOnMouseDragged {
            textSize = fontSizeSlider.value.roundToInt()
        }
        this.alignment = Pos.CENTER
        this.children.addAll(textInputLabel, fontSizeSliderLabel, styleInputLabel)
    }
}