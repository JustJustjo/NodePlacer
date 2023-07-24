package com.kilk.panels

import com.kilk.nodes.ActionToggleButton
import com.kilk.nodes.BetterToggleButton
import com.kilk.nodes.PublishAction
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Label
import javafx.scene.control.TextField

class ActionToggleButtonSettings(button: ActionToggleButton): NodeSettingsPanel(button) {
    val valueAsTextButton = BetterToggleButton("Value as Text", button.showValueAsText)
    val defaultValueInput = TextField(button.defaultValue.toString())
    val defaultValueLabel = Label("Default Value:", defaultValueInput)
    val ntKeyInput = TextField(button.entryKey)
    val ntKeyLabel = Label("NetworkTable Key:", ntKeyInput)

    init {
        publishActionDropdown.value = button.publishAction
        if (publishActionDropdown.value == PublishAction.NETWORKTABLES) {
            this.children.add(ntKeyLabel)
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

        children.addSecond(defaultValueLabel, valueAsTextButton)
    }
}