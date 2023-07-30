package com.kilk.panels

import com.kilk.nodes.ActionTextBox
import com.kilk.nodes.PublishAction
import com.kilk.nodes.TextBoxType
import javafx.scene.control.ComboBox
import javafx.scene.control.ContentDisplay
import javafx.scene.control.Label
import javafx.scene.control.TextField

class ActionTextBoxSettingsPanel(textBox: ActionTextBox): NodeSettingsPanel(textBox) {
    val ntKeyInput = TextField(textBox.entryKey)
    val ntKeyLabel = Label("NetworkTable Entry:", ntKeyInput)
    val textBoxTypeDropdown = ComboBox<TextBoxType>()
    val textBoxTypeLabel = Label("TextBox Type:", textBoxTypeDropdown)

    init {
        this.children.remove(textInputLabel)
        if (textBox.textBoxType == TextBoxType.READ) {
            this.publishActionLabel.text = "Read From:"
        } else {
            this.publishActionLabel.text = "Publish To / Read From:"
        }
        publishActionDropdown.value = textBox.publishAction
        if (publishActionDropdown.value == PublishAction.NETWORKTABLES) {
            this.children.add(ntKeyLabel)
        }
        textInput.setOnKeyTyped {
            textBox.text = textInput.text
        }
        publishActionDropdown.setOnAction {
            textBox.publishAction = publishActionDropdown.value
            if (publishActionDropdown.value == PublishAction.NETWORKTABLES) {
                println(this.children.indexOf(publishActionDropdown))
                this.children.addAt(2, ntKeyLabel)
            } else {
                this.children.remove(ntKeyLabel)
            }
            textBox.updateTextBoxNTListener()
        }
        ntKeyLabel.contentDisplay = ContentDisplay.BOTTOM
        ntKeyInput.promptText = "table/entry"
        ntKeyInput.setOnKeyTyped {
            textBox.entryKey = ntKeyInput.text
            textBox.updateTextBoxNTListener()
        }
        textBoxTypeLabel.contentDisplay = ContentDisplay.BOTTOM
        textBoxTypeDropdown.items.addAll(TextBoxType.READ, TextBoxType.WRITE)
        textBoxTypeDropdown.value = textBox.textBoxType
        textBoxTypeDropdown.setOnAction {
            textBox.textBoxType = textBoxTypeDropdown.value
            textBox.changeTextBoxAction()
            textBox.updateTextBoxNTListener()
            if (textBox.textBoxType == TextBoxType.READ) {
                this.publishActionLabel.text = "Read From:"
            } else {
                this.publishActionLabel.text = "Publish To / Read From:"
            }
            println(textBox.textBoxType)
        }
        children.addFirst(textBoxTypeLabel)
    }

}