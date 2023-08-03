package com.kilk.panels

import com.kilk.nodes.ActionToggleButton
import com.kilk.nodes.BetterToggleButton
import com.kilk.nodes.PublishAction
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Label
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import kotlin.math.roundToInt

class ActionToggleButtonSettings(button: ActionToggleButton): NodeSettingsPanel(button) {
    val valueAsTextButton = BetterToggleButton("Value as Text", button.showValueAsText)
    val defaultValueInput = TextField(button.defaultValue.toString())
    val defaultValueLabel = Label("Default Value:", defaultValueInput)
    val ntKeyInput = TextField(button.entryKey)
    val ntKeyLabel = Label("NetworkTable Key:", ntKeyInput)
    val tStyleInput = TextArea(button.tStyle)
    val fStyleInput = TextArea(button.fStyle)
    val tStyleLabel = Label("True Style", tStyleInput)
    val fStyleLabel = Label("False Style", fStyleInput)
    val styleHBox = HBox()

    var prevTextSize: Int = fontSizeSlider.value.roundToInt()

    init {
        this.children.removeAll(styleInput)
        publishActionDropdown.value = button.publishAction
        if (publishActionDropdown.value == PublishAction.NETWORKTABLES) {
            this.children.add(ntKeyLabel)
        }
        if (!button.tStyle.contains("-fx-font-size:")) {
            button.tStyle += ";-fx-font-size: ${prevTextSize}px;"
        }
        if (!button.fStyle.contains("-fx-font-size:")) {
            button.fStyle += ";-fx-font-size: ${prevTextSize}px;"
        }
        fontSizeSlider.setOnMouseDragged {
            val size = fontSizeSlider.value.roundToInt()
            button.tStyle = button.tStyle.replace("-fx-font-size: ${prevTextSize}px", "-fx-font-size: ${size}px")
            button.fStyle = button.fStyle.replace("-fx-font-size: ${prevTextSize}px", "-fx-font-size: ${size}px")
            tStyleInput.text = button.tStyle
            fStyleInput.text = button.fStyle
            button.updateStyle()
            prevTextSize = size
        }

        textInputLabel.isDisable = button.showValueAsText
        textInput.setOnKeyTyped {
            button.text = textInput.text
        }
        publishActionDropdown.setOnAction {
            button.publishAction = publishActionDropdown.value
            if (publishActionDropdown.value == PublishAction.NETWORKTABLES) {
                println(this.children.indexOf(publishActionDropdown))
                this.children.addAt(1, ntKeyLabel)
            } else {
                this.children.remove(ntKeyLabel)
            }
        }
        ntKeyLabel.contentDisplay = ContentDisplay.BOTTOM
        ntKeyInput.text = button.entryKey
        ntKeyInput.setOnKeyTyped {
            button.entryKey = ntKeyInput.text
        }
        ntKeyInput.promptText = "table/key"

        valueAsTextButton.setOnAction {
            valueAsTextButton.toggle()
            button.showValueAsText = valueAsTextButton.value
            textInputLabel.isDisable = button.showValueAsText
            textInput.text = button.text
        }
        defaultValueLabel.contentDisplay = ContentDisplay.BOTTOM
        defaultValueInput.setOnAction {
            button.defaultValue = defaultValueInput.text.toBoolean()
            println(button.defaultValue)
        }
        tStyleInput.isWrapText = true
        tStyleInput.setPrefSize(300.0, 120.0)
        tStyleLabel.contentDisplay = ContentDisplay.BOTTOM
        tStyleInput.setOnKeyTyped {
            button.tStyle = tStyleInput.text
            button.updateStyle()
        }
        fStyleInput.isWrapText = true
        fStyleInput.setPrefSize(300.0, 120.0)
        fStyleLabel.contentDisplay = ContentDisplay.BOTTOM
        fStyleInput.setOnKeyTyped {
            button.fStyle = fStyleInput.text
            button.updateStyle()
        }
        styleHBox.children.addAll(tStyleLabel, fStyleLabel)
        children.addSecond(defaultValueLabel, valueAsTextButton)
        children.add(styleHBox)
    }
}