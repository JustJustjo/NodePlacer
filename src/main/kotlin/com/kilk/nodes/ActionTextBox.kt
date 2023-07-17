package com.kilk.nodes

import com.kilk.TabDeck.editMode
import com.kilk.panels.ActionTextBoxSettingsPanel
import com.kilk.panels.RightClickPanel
import javafx.scene.Cursor
import javafx.scene.control.TextField
import javafx.scene.input.MouseButton

class ActionTextBox(x: Double, y: Double, width: Double, height: Double, text: String, var textBoxType: TextBoxType, var publishAction: PublishAction, style: String = ""): TextField(text), Savable {
    var value: String = text
        set(value) {
            when (publishAction) {
                PublishAction.PRINTLN -> { println(value) }
                PublishAction.NONE -> {}
                else -> println("Action $publishAction not implemented yet")
            }
            this.text = value
            field = value
        }

    val resizeThreshold: Double = 0.15 //% of the width and height of button for the cursor to be in order to resize
    private var pressedLocation = Pair(0.0, 0.0)

    init {
        this.translateX = x
        this.translateY = y
        this.minWidth = width
        this.maxWidth = width
        this.minHeight = height
        this.maxHeight = height
        this.style = style

        changeTextBoxAction(this.textBoxType)
        //sets the context menu to rightClickMenu (whenever this button gets right-clicked it will show up)
        this.contextMenu = RightClickPanel(this, ActionTextBoxSettingsPanel(this))
    }



    private fun setEditActions() {
        this.setOnMouseDragged { event ->
            if (event.button == MouseButton.PRIMARY && editMode) {
                //checks if mouse is on the edge of textbox. if true: resize. if false: relocate.
                if (event.x + this.width * resizeThreshold  > this.width || event.y + this.height * resizeThreshold > this.height) {
                    //resize width and height
                    this.setMinSize(
                        snapToGrid(event.x),
                        snapToGrid(event.y)
                    )
                } else {
                    //relocate the x and y cords of the textbox
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
            this.isEditable = !editMode
        }
        this.setOnMouseExited {
            scene.cursor = Cursor.DEFAULT
        }
        this.setOnMousePressed {
            pressedLocation = Pair(it.x, it.y)
        }

    }

    fun changeTextBoxAction(textBoxType: TextBoxType) {
        when (textBoxType) {
            TextBoxType.WRITE -> setWriteAction()
            TextBoxType.READ -> setReadAction()
        }
        setEditActions()
    }
    //what it will do to the value when reading
    private fun setWriteAction() {
        this.isEditable = true
        this.setOnAction {
            if (!editMode) {
                value = this.text
            }
        }
    }
    //set to do nothing when clicked
    private fun setReadAction() {
        this.isEditable = false
        this.setOnAction {}
        this.setOnMouseMoved {}
    }
    override fun getJson(): String {
        println("in $this getJson() function")
        val data = jsonMapper.writeValueAsString(SavedTextBox(translateX, translateY, width, height, text, textBoxType, publishAction, style))
        return jsonMapper.writeValueAsString(SavedNode(NodeType.TEXTBOX, data))
    }
}