package com.kilk.nodes

import com.fasterxml.jackson.module.kotlin.readValue
import com.kilk.TabDeck.editMode
import com.kilk.panels.RightClickMenu
import com.kilk.panels.TabSettingsPanel
import javafx.scene.control.Tab
import javafx.scene.input.MouseButton
import javafx.scene.layout.Pane
import com.kilk.NodeSaver.returnNode

class SavableTab(text: String = ":)", style: String = "", childrenArray: ArrayList<String>? = null): Tab(text), Savable {
    val pane = Pane() //creates the pane, this will be the class with all the nodes on it
    init {
        if (this.style == null) {
            this.style = ""
        }
        //set the tab content to the pane
        content = pane
        isClosable = editMode
        //set the pane size to screen size
        pane.setPrefSize(10000.0, 10000.0)
        //setting the style of pane
        pane.style = style

        pane.setOnMousePressed { event ->
            if (editMode) {
                //later this wil give tab options on right click, but right now it creates a button or textbox
                if (event.button == MouseButton.SECONDARY) {
                    println("Secondary")
//                    pane.children.add(ActionTextBox(event.x, event.y, 100.0, 100.0, pane.children.count().toString()))
//                    pane.children.add(ActionToggleButton(event.x, event.y, 100.0, 100.0, pane.children.count().toString(), PublishAction.PRINT, showValueAsText = true))
                } else {
//                    pane.children.add(ActionButton(event.x, event.y, 100.0, 100.0, pane.children.count().toString(), ButtonType.SET, PublishAction.PRINT, 0, 1, showValueAsText = true))
                }
            }
        }
        if (childrenArray != null) {
            //creates and adds children nodes if constructed with them
            loadChildren(childrenArray)
        }
        this.contextMenu = RightClickMenu(this, TabSettingsPanel(this))
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
            val nodeName = node.nodeName
            val data = node.data
            pane.children.add(returnNode(nodeName, data))
        }
    }
}