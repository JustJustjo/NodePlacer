package com.kilk.panels

import com.kilk.nodes.ActionTextBox

class ActionTextBoxSettingsPanel(textBox: ActionTextBox): NodeSettingsPanel(textBox) {

    init {
        publishActionDropdown.value = textBox.publishAction
        textInput.setOnKeyTyped {
            textBox.text = textInput.text
        }
        publishActionDropdown.setOnAction {
            textBox.publishAction = publishActionDropdown.value
        }
    }

}