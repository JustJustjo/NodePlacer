package com.kilk.panels

import com.kilk.nodes.ActionButton
import com.kilk.nodes.BetterToggleButton
import com.kilk.nodes.ButtonType
import com.kilk.nodes.PublishAction
import javafx.scene.control.*

class ActionButtonSettingsPanel(button: ActionButton): NodeSettingsPanel(button) {
    val valueAsTextButton = BetterToggleButton("Value as Text", button.showValueAsText)
    val buttonTypeDropdown = ComboBox<ButtonType>()
    val buttonTypeLabel = Label("Button Type:", buttonTypeDropdown)
    val defaultValueInput = TextField(button.defaultValue.toString())
    val defaultValueLabel = Label("Default Value:", defaultValueInput)
    val actionValueInput = TextField(button.actionValue.toString())
    val actionValueLabel = Label("Action Value:", actionValueInput)
    val ntKeyInput = TextField(button.entryKey)
    val ntKeyLabel = Label("NetworkTable Entry:", ntKeyInput)

    init {

        if (publishActionDropdown.value == PublishAction.NETWORKTABLES) {
            this.children.add(ntKeyLabel)
        }
        textInputLabel.isDisable = button.showValueAsText
        textInput.text = button.text
        textInput.setOnKeyTyped {
            button.text = textInput.text
        }
        publishActionDropdown.value = button.publishAction
        publishActionDropdown.setOnAction {
            button.publishAction = publishActionDropdown.value
            if (publishActionDropdown.value == PublishAction.NETWORKTABLES) {
                println(this.children.indexOf(publishActionDropdown))
                this.children.addAt(2, ntKeyLabel)
            } else {
                this.children.remove(ntKeyLabel)
            }
        }
        ntKeyLabel.contentDisplay = ContentDisplay.BOTTOM
        ntKeyInput.promptText = "table/entry"
        ntKeyInput.setOnKeyTyped {
            button.entryKey = ntKeyInput.text
        }

        defaultValueLabel.contentDisplay = ContentDisplay.BOTTOM
        defaultValueInput.setOnKeyTyped {
            button.defaultValue =
                try {
                    defaultValueInput.text.toInt()
                } catch (_: Exception) {
                    try {
                        defaultValueInput.text.toDouble()
                    } catch (_: Exception) {
                        defaultValueInput.text
                    }
                }
            println(button.defaultValue)
        }
        actionValueLabel.contentDisplay = ContentDisplay.BOTTOM
        actionValueInput.setOnKeyTyped {
            button.actionValue =
                try {
                    actionValueInput.text.toInt()
                } catch (_: Exception) {
                    try {
                        actionValueInput.text.toDouble()
                    } catch (_: Exception) {
                        try {
                            actionValueInput.text.toBooleanStrict()
                        } catch (_: Exception) {
                            actionValueInput.text
                        }
                    }
                }
            println(Pair(button.actionValue, button.actionValue.javaClass))
        }

        buttonTypeLabel.contentDisplay = ContentDisplay.BOTTOM
        buttonTypeDropdown.items.addAll(ButtonType.SET, ButtonType.ADD, ButtonType.SUBTRACT)
        buttonTypeDropdown.value = button.buttonType
        buttonTypeDropdown.setOnAction {
            button.setButtonAction(buttonTypeDropdown.value)
            println(button.buttonType)
        }
        valueAsTextButton.setOnAction {
            valueAsTextButton.toggle()
            button.showValueAsText = valueAsTextButton.value
            textInputLabel.isDisable = button.showValueAsText
            textInput.text = button.text
        }

        children.addSecond(actionValueLabel, defaultValueLabel, valueAsTextButton)
        children.addFirst(buttonTypeLabel)
    }

}