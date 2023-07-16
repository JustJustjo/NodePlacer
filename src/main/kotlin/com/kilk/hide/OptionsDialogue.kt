package com.kilk.hide

import javafx.scene.control.*
import javafx.scene.layout.VBox
import javafx.scene.text.Font

//class OptionsDialogue(button: ActionButton): VBox() {
//    val nameInput = TextField(button.text)
//    val textSlider = Slider(15.0, 400.0, 15.0)
//    val actionButton = Button("SET")
//    init {
//        setOptionsMenu(button)
//        nameInput.setOnKeyTyped {
//            button.text = nameInput.text
//        }
//        textSlider.setOnMouseDragged {
//            button.font = Font(textSlider.value)
//        }
//        actionButton.setOnAction {
//            button.actionType = ActionType.SET
//        }
//        println(button.font)
//        children.addAll(nameInput, textSlider, actionButton)
//    }
//    fun setOptionsMenu(button: Button) {
//        val buttonContextMenu = ContextMenu()
//        val buttonType = ButtonType("Ok", ButtonBar.ButtonData.OK_DONE)
//        val settings = Dialog<String>()
//
//        settings.dialogPane.buttonTypes.add(buttonType)
//        settings.headerText = "Settings"
//        settings.dialogPane.content = this
//
//        val optionsItem = MenuItem("Options")
//        optionsItem.setOnAction {
//            settings.showAndWait()
//        }
//        val deleteItem = MenuItem("Remove")
//        deleteItem.setOnAction {
//            TabDeck.pane.children.remove(button)
//        }
//
//        buttonContextMenu.items.addAll(optionsItem, deleteItem)
//        button.contextMenu = buttonContextMenu
//    }
//}