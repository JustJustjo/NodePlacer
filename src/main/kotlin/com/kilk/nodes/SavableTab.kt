package com.kilk.nodes

import com.fasterxml.jackson.module.kotlin.readValue
import com.kilk.TabDeck.editMode
import javafx.scene.control.Tab
import javafx.scene.input.MouseButton
import javafx.scene.layout.Pane
import javafx.stage.Screen

class SavableTab(text: String = ":)", style: String = "", childrenArray: ArrayList<String>? = null): Tab(text), Savable {
    val pane = Pane() //creates the pane, this will be the class with all the nodes on it
    val screenBounds = Screen.getPrimary().visualBounds
    init {
        //set the tab content to the pane
        content = pane
        isClosable = editMode
        //set the pane size to screen size
        pane.setPrefSize(screenBounds.width, screenBounds.height)
        //setting the style of pane
        pane.style = style

        pane.setOnMousePressed { event ->
            if (editMode) {
                //later this wil give tab options on right click, but right now it creates a button or textbox
                if (event.button == MouseButton.SECONDARY) {
                    println("Secondary")
                    pane.children.add(ActionTextBox(event.x, event.y, 500.0, 200.0, pane.children.count().toString(), TextBoxType.WRITE, PublishAction.PRINTLN))
                } else {
                    pane.children.add(ActionButton(event.x, event.y, 100.0, 100.0, pane.children.count().toString(), ButtonType.TOGGLE, PublishAction.PRINTLN, true, true, showValueAsText = true))
                }
            }
        }
        if (childrenArray != null) {
            //creates and adds children nodes if constructed with them
            loadChildren(childrenArray)
        }
    }
    override fun getJson(): String {
        println("in $this getJson() function")

        //creates a list of all the children's json data (if they inherit form Savable interface)
        val childrenArray: ArrayList<String> = ArrayList()
        pane.children.forEach { if (it is Savable) { childrenArray.add(it.getJson()) } else { println("$it does not inherit from Savable") } }

        return jsonMapper.writeValueAsString(SavedTab(text, pane.style, childrenArray))
    }
    //puts nodes into the pane if given a json string
    override fun loadChildren(childrenArray: ArrayList<String>?) {
        childrenArray?.forEach {
            val node: SavedNode = jsonMapper.readValue(it)
            val data = node.data

            when (node.nodeType) {
                NodeType.BUTTON -> {
                    val d: SavedActionButton = jsonMapper.readValue(data)
                    pane.children.add(ActionButton(d.x, d.y, d.width, d.height, d.text, d.buttonType, d.publishAction, d.defaultValue, d.actionValue, d.style, d.showValueAsText))
                }
                NodeType.TEXTBOX -> {
                    val d: SavedTextBox = jsonMapper.readValue(data)
                    pane.children.add(ActionTextBox(d.x, d.y, d.width, d.height, d.text, d.textBoxType, d.publishAction, d.style))
                }
            }
        }
    }
}