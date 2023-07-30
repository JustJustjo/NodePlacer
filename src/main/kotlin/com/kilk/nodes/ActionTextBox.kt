package com.kilk.nodes

import com.kilk.NTClient
import com.kilk.TabDeck.editMode
import com.kilk.panels.ActionTextBoxSettingsPanel
import com.kilk.panels.RightClickMenu
import edu.wpi.first.networktables.NetworkTableEvent
import javafx.application.Platform
import javafx.scene.Cursor
import javafx.scene.control.TextField
import javafx.scene.input.MouseButton
import java.util.*

class ActionTextBox(x: Double?, y: Double?, width: Double, height: Double, text: String, var textBoxType: TextBoxType = TextBoxType.READ, var publishAction: PublishAction = PublishAction.PRINT, style: String = "", var entryKey: String? = null, val isDisplay: Boolean = false): TextField(text), Savable {
    var value: String = text
        get() {
            if (publishAction == PublishAction.NETWORKTABLES) {
                return NTClient.ntInstance.getEntry(entryKey).value.value.toString()
            } else {
                return field
            }
        }
        set(value) {
            when (publishAction) {
                PublishAction.PRINT -> { println(value) }
                PublishAction.NONE -> {}
                PublishAction.NETWORKTABLES -> {
                    NTClient.ntInstance.getEntry(entryKey).setValue(value)
                }
            }
            field = value
        }
    var listenerID: Int? = null

    val resizeThreshold: Double = 0.15 //% of the width and height of button for the cursor to be in order to resize
    private var pressedLocation = Pair(0.0, 0.0)

    init {
        if (x != null && y != null) {
            this.translateX = x
            this.translateY = y
        }
        this.minWidth = width
        this.maxWidth = width
        this.minHeight = height
        this.maxHeight = height
        this.style = style

        changeTextBoxAction(this.textBoxType)
        //sets the context menu (whenever this button gets right-clicked it will show up)
        this.contextMenu = RightClickMenu(this, ActionTextBoxSettingsPanel(this))
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
                    this.setMaxSize(
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

    fun changeTextBoxAction(textBoxType: TextBoxType = this.textBoxType) {
        when (textBoxType) {
            TextBoxType.WRITE -> setWriteAction()
            TextBoxType.READ -> setReadAction()
        }
        if (!isDisplay) {
            setEditActions()
        }
    }
    //what it will do to the value when reading
    private fun setWriteAction() {
        this.isEditable = true
        this.setOnKeyTyped {
            if (!editMode) {
                Platform.runLater {
                    value = this.text
                }
            }
        }
        updateTextBoxNTListener()
    }
    //set to do nothing when clicked
    private fun setReadAction() {
        this.isEditable = false
        this.setOnKeyTyped {}
        this.setOnMouseMoved {}
        updateTextBoxNTListener()
    }
    fun updateTextBoxNTListener() {
        if (this.listenerID != null) {
            println("removing listener with ID: ${this.listenerID}")
            NTClient.ntInstance.removeListener(listenerID!!)
        }
        if (publishAction == PublishAction.NETWORKTABLES) {
            if (entryKey != null) {
                val listener = NTClient.ntInstance.addListener( //add listener that checks if the value get updated (uses 100% of a single cpu core so kinda inefficient)
                    NTClient.ntInstance.getEntry(entryKey),
                    EnumSet.of(if (this.textBoxType == TextBoxType.WRITE) NetworkTableEvent.Kind.kValueRemote else NetworkTableEvent.Kind.kValueAll, NetworkTableEvent.Kind.kConnected)
                ) {
                    this.listenerID = it.listener
                    Platform.runLater { //this Platform.runLater fixes multiple weird java thread errors
                        this.text = it.valueData.value.value.toString()
                    }
                }
                this.listenerID = listener
                println("added listener that listens on key: $entryKey   listenerID: $listener")
            } else {
                println("TextBox network table entry key is null")
            }
        }
    }
    override fun getJson(): String {
        println("in $this getJson() function")
        val data = jsonMapper.writeValueAsString(SavedTextBox(translateX, translateY, width, height, text, textBoxType, publishAction, style))
        return jsonMapper.writeValueAsString(SavedNode(NodeType.TEXTBOX, data))
    }
}