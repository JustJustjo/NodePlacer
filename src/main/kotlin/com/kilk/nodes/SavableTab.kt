package com.kilk.nodes

import com.kilk.TabDeck.editMode
import javafx.scene.control.Tab
import javafx.scene.input.MouseButton
import javafx.scene.layout.Pane
import javafx.stage.Screen

class SavableTab(text: String = ":)"): Tab(text), Savable {
    val pane = Pane()
    val screenBounds = Screen.getPrimary().visualBounds
    init {
        content = pane
        isClosable = editMode
        pane.setPrefSize(screenBounds.width, screenBounds.height)
        pane.style = "-fx-background-color: darkslategray"

        pane.setOnMouseClicked { event ->
            if (editMode) {
                if (event.button == MouseButton.SECONDARY) {
                    println("Secondary")
                } else {
                    pane.children.add(ActionButton(event.x, event.y, 100.0, 100.0, pane.children.count().toString(), ButtonType.TOGGLE, PublishAction.PRINTLN, true, true, showValueAsText = true))
                }
            }
        }
    }
    override fun getJson(): String {
        println("in $this getJson() function")

        val childrenArray: ArrayList<String> = ArrayList()
        pane.children.forEach { if (it is Savable) { childrenArray.add(it.getJson()) } else { println("$it does not inherit from Savable") } }

        return jsonMapper.writeValueAsString(SavedTab(text, pane.style, childrenArray))
    }
}