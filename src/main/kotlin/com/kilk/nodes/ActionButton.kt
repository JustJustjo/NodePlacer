package com.kilk.nodes

import com.kilk.TabDeck.editMode
import javafx.scene.control.Button

class ActionButton(x: Double, y: Double, width: Double, height: Double, text: String, var buttonType: ButtonType, var publishAction: PublishAction, val defaultValue: Any, var actionValue: Any, style: String = "", var showValueAsText: Boolean = false): Button(text), Savable {
    var value: Any = defaultValue
        set(value) {
            when (publishAction) {
                PublishAction.PRINTLN -> { println(value); field = value }
                else -> println("Action $publishAction not implemented yet")
            }
            if (this.showValueAsText) { text = value.toString() }
        }

    init {
        this.translateX = x
        this.translateY = y
        this.width = width
        this.height = height
        this.style = style
        this.text = value.toString()


        setButtonAction(this.buttonType)
    }

    fun setButtonAction(buttonType: ButtonType) {
        when (buttonType) {
            ButtonType.TOGGLE -> setToggleAction()
            ButtonType.SET -> setSetAction()
            else -> println("$buttonType not implemented yet")
        }
    }

    private fun setToggleAction() {
        this.setOnAction {
            if (!editMode) {
                value = !(value as Boolean)
            }
        }
    }
    private fun setSetAction() {
        this.setOnAction {
            if (!editMode) {
                value = actionValue
            }
        }
    }

    override fun getJson(): String {
        println("in $this getJson() function")
        return jsonMapper.writeValueAsString(SavedActionButton(translateX, translateY, width, height, text, buttonType, publishAction, defaultValue, actionValue, style, showValueAsText))
    }
}