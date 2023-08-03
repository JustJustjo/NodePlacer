package com.kilk.nodes

import com.kilk.NTClient
import com.kilk.TabDeck.editMode
import com.kilk.panels.ActionButtonSettingsPanel
import com.kilk.panels.RightClickMenu
import javafx.scene.Cursor
import javafx.scene.control.*
import javafx.scene.input.MouseButton

class ActionButton(x: Double?, y: Double?, width: Double, height: Double, text: String, var buttonType: ButtonType = ButtonType.ADD, var publishAction: PublishAction = PublishAction.PRINT, defaultValue: Any = 0, var actionValue: Any = 1, style: String = "", var showValueAsText: Boolean = false, var entryKey: String? = null, val isDisplay: Boolean = false): Button(text), Savable {
    var defaultValue: Any = defaultValue
        set(value) {
            this.value = value
            field = value
        }
    var value: Any = defaultValue
        set(value) {
            when (publishAction) {
                PublishAction.PRINT -> { println(value) }
                PublishAction.NETWORKTABLES -> {
                    NTClient.ntInstance.getEntry(entryKey).setValue(value)
                }
                PublishAction.NONE -> {}
            }
            if (showValueAsText) { text = value.toString() }
            field = value
        }

    val resizeThreshold: Double = 0.15 //% of the width and height for the cursor to be in order to resize
    private var pressedLocation = Pair(0.0, 0.0)

    init {
        if (x != null && y != null) {
            this.translateX = x
            this.translateY = y
        }
        this.maxWidth = width
        this.maxHeight = height
        if (this.isDisplay) {
            this.prefWidth = width
            this.prefHeight = height
        } else {
            this.minWidth = width
            this.minHeight = height
        }
        this.style = style
        if (showValueAsText) {
            this.text = value.toString()
        }

        setButtonAction(this.buttonType)
        //sets the context menu to rightClickMenu (whenever this button gets right-clicked it will show up)
        this.contextMenu = RightClickMenu(this, ActionButtonSettingsPanel(this))
    }

    fun setButtonAction(buttonType: ButtonType) {
        this.buttonType = buttonType
        when (buttonType) {
            ButtonType.SET -> setSetAction()
            ButtonType.ADD -> setAddAction()
            ButtonType.SUBTRACT -> setSubtractAction()
        }
        if (!isDisplay) {
            setEditActions()
        }
    }
    private fun setSetAction() {
        this.setOnAction {
            if (!editMode) {
                value = actionValue
            }
        }
    }
    private fun setAddAction() {
        this.setOnAction {event ->
            if (!editMode) {
                try {
                    value = value.toString().toInt().plus(actionValue.toString().toInt())
                } catch (e: Exception) {
                    try {
                        value = value.toString().toDouble().plus(actionValue.toString().toDouble())
                    } catch (_: Exception) { }
                }
            }
        }
    }
    private fun setSubtractAction() {
        this.setOnAction {event ->
            if (!editMode) {
                try {
                    value = value.toString().toInt().minus(actionValue.toString().toInt())
                } catch (e: Exception) {
                    try {
                        value = value.toString().toDouble().minus(actionValue.toString().toDouble())
                    } catch (_: Exception) { }
                }
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
            //resets the cursor to be back to default
            scene.cursor = Cursor.DEFAULT
        }
        this.setOnMousePressed {
            pressedLocation = Pair(it.x, it.y)
        }
    }

    override fun getJson(): String {
        println("in $this getJson() function")
        val data = jsonMapper.writeValueAsString(SavedActionButton(translateX, translateY, width, height, text, buttonType, publishAction, defaultValue, actionValue, style, showValueAsText, entryKey))
        return jsonMapper.writeValueAsString(SavedNode("ActionButton", data))
    }
    override fun copySelf() = ActionButton(translateX, translateY, width, height, text, buttonType, publishAction, defaultValue, actionValue, style, showValueAsText, entryKey)
    data class SavedActionButton (
        val x: Double,
        val y: Double,
        val width: Double,
        val height: Double,
        val text: String,
        val buttonType: ButtonType,
        val publishAction: PublishAction,
        val defaultValue: Any,
        val actionValue: Any,
        val style: String,
        val showValueAsText: Boolean,
        val entryKey: String?
    )
}