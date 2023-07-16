package com.kilk.nodes

import com.kilk.TabDeck.editMode
import com.kilk.panels.ActionButtonSettingsPanel
import com.kilk.panels.RightClickPanel
import javafx.scene.Cursor
import javafx.scene.control.*
import javafx.scene.input.MouseButton

class ActionButton(x: Double, y: Double, width: Double, height: Double, text: String, var buttonType: ButtonType, var publishAction: PublishAction, val defaultValue: Any, var actionValue: Any, style: String = "", var showValueAsText: Boolean = false): Button(text), Savable {
    var value: Any = defaultValue
        set(value) {
            when (publishAction) {
                PublishAction.PRINTLN -> { println(value); field = value }
                else -> println("Action $publishAction not implemented yet")
            }
            if (this.showValueAsText) { text = value.toString() }
        }
    val resizeThreshold: Double = 0.15 //% of the width and height of button for the cursor to be in order to resize
    private var pressedLocation = Pair(0.0, 0.0)

    init {
        this.translateX = x
        this.translateY = y
        this.width = width
        this.height = height
        this.style = style
        this.text = value.toString()

        setButtonAction(this.buttonType)
        //sets the context menu to rightClickMenu (whenever this button gets right-clicked it will show up)
        this.contextMenu = RightClickPanel(this, ActionButtonSettingsPanel(this))
    }

    fun setButtonAction(buttonType: ButtonType) {
        when (buttonType) {
            ButtonType.TOGGLE -> setToggleAction()
            ButtonType.SET -> setSetAction()
            else -> println("$buttonType not implemented yet")
        }
        setEditActions()
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
    //adds the relocating and resizing to the button
    private fun setEditActions() {
        this.setOnMouseDragged { event ->
            if (event.button == MouseButton.PRIMARY && editMode) {
                //checks if mouse is on the edge of button. if true: resize. if false: relocate.
                if (event.x + this.width * resizeThreshold  > this.width || event.y + this.height * resizeThreshold > this.height) {
                    //resize width and height
                    this.setMinSize(
                        snapToGrid(event.x),
                        snapToGrid(event.y)
                    )
                } else {
                    //relocate the x and y cords of the button
                    this.translateX = snapToGrid(this.translateX + (event.x - pressedLocation.first))
                    this.translateY = snapToGrid(this.translateY + (event.y - pressedLocation.second))
                }
            }
        }
        this.setOnMouseMoved { event ->
            if (editMode) {
                //changes the cursor to let the user know if they're resizing or relocating
                if (event.x + this.width * resizeThreshold > this.width || event.y + this.height * resizeThreshold > this.height) {
                    scene.cursor = Cursor.SE_RESIZE
                } else {
                    scene.cursor = Cursor.CROSSHAIR
                }
            }
        }
        this.setOnMouseExited {
            scene.cursor = Cursor.DEFAULT
        }
        this.setOnMousePressed {
            pressedLocation = Pair(it.x, it.y)
        }
    }

    override fun getJson(): String {
        println("in $this getJson() function")
        return jsonMapper.writeValueAsString(SavedActionButton(translateX, translateY, width, height, text, buttonType, publishAction, defaultValue, actionValue, style, showValueAsText))
    }
}