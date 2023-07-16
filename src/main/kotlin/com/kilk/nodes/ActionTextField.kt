package com.kilk.nodes

import javafx.scene.control.TextField

class ActionTextField(x: Double, y: Double, width: Double, height: Double, text: String, var textBoxType: TextBoxType, var nodeAction: NodeAction, val defaultValue: Any, style: String = ""): TextField(text), Savable {
    var value: Any = defaultValue
        set(value) {
            when (nodeAction) {
                NodeAction.PRINTLN -> {println(value); field = value}
                else -> println("Action $nodeAction not implemented yet")
            }
        }

    init {
        this.translateX = x
        this.translateY = y
        this.width = width
        this.height = height
        this.style = style


        changeTextBoxType()
    }
    fun changeTextBoxType(textBoxType: TextBoxType = this.textBoxType) {
        when (textBoxType) {
            TextBoxType.WRITE -> {
                this.setOnAction {
                    value = this.text
                }
            }
            TextBoxType.READ -> {
                this.setOnAction {}
                this.isEditable = false
            }
        }
    }
    override fun getJson(): String {
        println("in $this getJson() function")
        return jsonMapper.writeValueAsString(SavedTextField(translateX, translateY, width, height, textBoxType, nodeAction, defaultValue, style))
    }
}