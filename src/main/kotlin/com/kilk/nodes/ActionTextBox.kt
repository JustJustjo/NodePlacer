package com.kilk.nodes

import com.kilk.TabDeck.editMode
import javafx.scene.control.TextField

class ActionTextBox(x: Double, y: Double, width: Double, height: Double, text: String, var textBoxType: TextBoxType, var publishAction: PublishAction, style: String = ""): TextField(text), Savable {
    var value: String = text
        set(value) {
            when (publishAction) {
                PublishAction.PRINTLN -> {println(value); field = value}
                PublishAction.NONE -> field = value
                else -> println("Action $publishAction not implemented yet")
            }
        }

    init {
        this.translateX = x
        this.translateY = y
        this.width = width
        this.height = height
        this.style = style


        changeTextBoxType(this.textBoxType)
    }
    fun changeTextBoxType(textBoxType: TextBoxType) {
        when (textBoxType) {
            TextBoxType.WRITE -> setWriteType()
            TextBoxType.READ -> setReadType()
        }
    }

    private fun setWriteType() {
        this.isEditable = true
        this.setOnAction {
            if (!editMode) {
                value = this.text
            }
        }
    }
    private fun setReadType() {
        this.isEditable = false
        this.setOnAction {}
    }
    override fun getJson(): String {
        println("in $this getJson() function")
        return jsonMapper.writeValueAsString(SavedTextBox(translateX, translateY, width, height, text, textBoxType, publishAction, style))
    }
}