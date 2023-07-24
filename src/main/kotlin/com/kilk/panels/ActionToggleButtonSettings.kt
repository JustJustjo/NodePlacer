package com.kilk.panels

import com.kilk.nodes.ActionToggleButton
import com.kilk.nodes.BetterToggleButton
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Label
import javafx.scene.control.TextField

class ActionToggleButtonSettings(button: ActionToggleButton): NodeSettingsPanel(button) {
    val valueAsTextButton = BetterToggleButton("Value as Text", button.showValueAsText)
    val defaultValueInput = TextField(button.defaultValue.toString())
    val defaultValueLabel = Label("Default Value:", defaultValueInput)

    init {
        publishActionDropdown.value = button.publishAction
        textInputLabel.isDisable = button.showValueAsText

        textInput.setOnKeyTyped {
            button.text = textInput.text
        }
        publishActionDropdown.setOnAction {
            button.publishAction = publishActionDropdown.value
        }

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

        children.addSecond(defaultValueLabel, valueAsTextButton)
    }
}