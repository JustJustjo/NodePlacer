package com.kilk.panels

import com.kilk.nodes.ActionButton
import com.kilk.nodes.BetterToggleButton

class ActionButtonSettingsPanel(button: ActionButton): NodeSettingsPanel(button) {
    val valueAsTextButton = BetterToggleButton("Value as Text", button.showValueAsText)

    init {
        textInput.text = button.text

        valueAsTextButton.setOnAction {
            valueAsTextButton.toggle()
            button.showValueAsText = valueAsTextButton.value
            textInputLabel.isDisable = button.showValueAsText
            textInput.text = button.text
        }
        textInputLabel.isDisable = button.showValueAsText

        children.addAll(valueAsTextButton)
    }

}