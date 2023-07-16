package com.kilk.nodes

import com.kilk.TabDeck.editMode
import javafx.scene.control.Button

class ActionButton(x: Double, y: Double, width: Double, height: Double, text: String, var buttonType: ButtonType, var nodeAction: NodeAction, val defaultValue: Any, var actionValue: Any, style: String = "", var showValueAsText: Boolean = false): Button(text), Savable {
    var value: Any = defaultValue
        set(value) {
            when (nodeAction) {
                NodeAction.PRINTLN -> {println(value); field = value}
                else -> println("Action $nodeAction not implemented yet")
            }
            if (this.showValueAsText) text = try {value.toString()} catch(e: java.lang.Exception) {text}
        }

    init {
        this.translateX = x
        this.translateY = y
        this.width = width
        this.height = height
        this.style = style
        this.text = value.toString()


        setButtonAction()
    }

    fun setButtonAction(buttonType: ButtonType = this.buttonType) {
        when (buttonType) {

            ButtonType.TOGGLE -> {
                if (value is Boolean) {
                    this.setOnAction {
                        if (!editMode) {
                            value = !(value as Boolean)
                        }
                    }
                } else {
                    println("could not set action of $this with text: $text. defaultValue must be a Boolean for buttons with a TOGGLE property")
                }
            }

            ButtonType.SET -> {
                this.setOnAction {
                    if (!editMode) {
                        value = actionValue
                    }
                }
            }
            else -> println("$buttonType not implemented yet")
        }
    }

    override fun getJson(): String {
        println("in $this getJson() function")
        return jsonMapper.writeValueAsString(SavedActionButton(translateX, translateY, width, height, text, buttonType, nodeAction, defaultValue, actionValue, style, showValueAsText))
    }
}