package com.kilk.nodes

import com.kilk.NTClient
import com.kilk.TabDeck
import com.kilk.panels.ActionToggleButtonSettings
import com.kilk.panels.RightClickMenu
import javafx.scene.Cursor
import javafx.scene.control.Button
import javafx.scene.input.MouseButton

class ActionToggleButton(x: Double?, y: Double?, width: Double, height: Double, text: String, var publishAction: PublishAction = PublishAction.PRINT, defaultValue: Boolean = false, style: String = "", var showValueAsText: Boolean = false, var entryKey: String? = null, val isDisplay: Boolean = false): Button(text), Savable {
    var defaultValue: Boolean = defaultValue
        set(value) {
            this.value = value
            field = value
        }
    var value: Boolean = defaultValue
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

        this.setOnAction {
            if (!TabDeck.editMode) {
                value = !(value)
            }
        }
        if (!isDisplay) {
            setEditActions()
        }
        //sets the context menu to rightClickMenu (whenever this button gets right-clicked it will show up)
        this.contextMenu = RightClickMenu(this, ActionToggleButtonSettings(this))
    }

    //adds the relocating and resizing to the button
    private fun setEditActions() {
        this.setOnMouseDragged { event ->
            if (event.button == MouseButton.PRIMARY && TabDeck.editMode) {
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
            if (TabDeck.editMode) {
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
        val data = jsonMapper.writeValueAsString(SavedActionToggleButton(translateX, translateY, width, height, text, publishAction, defaultValue, style, showValueAsText, entryKey))
        return jsonMapper.writeValueAsString(SavedNode("ActionToggleButton", data))
    }
    override fun copySelf() = ActionToggleButton(translateX, translateY, width, height, text, publishAction, defaultValue, style, showValueAsText, entryKey)
    data class SavedActionToggleButton (
        val x: Double,
        val y: Double,
        val width: Double,
        val height: Double,
        val text: String,
        val publishAction: PublishAction,
        val defaultValue: Boolean,
        val style: String,
        val showValueAsText: Boolean,
        val entryKey: String?
    )
}