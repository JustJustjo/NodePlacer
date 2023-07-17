package com.kilk.panels

import com.kilk.nodes.ActionButton

class ActionButtonSettingsPanel(button: ActionButton): NodeSettingsPanel(button) {

    init {
        if (button.showValueAsText) {
            children.remove(textInput)
        }
    }

}