package com.kilk

import com.kilk.nodes.*
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.VBox
import javafx.scene.text.Font

object NodeLibrary: VBox(5.0) {
    val libraryTitle = Label("Node Library")
    val actionButton = ActionButton(null, null, 200.0, 150.0, "Action Button", isDisplay = true)
    val toggleButton = ActionToggleButton(null, null, 150.0, 150.0, "Toggle Button", isDisplay = true)
    val textBox = ActionTextBox(null, null, 150.0, 150.0, "Text Box", isDisplay = true)

    val nodeList = listOf<Savable>(actionButton, toggleButton, textBox)

    var dragging = false
    lateinit var draggingNode: Savable
    lateinit var pressedLocation: Pair<Double, Double>

    init {
        nodeList.forEach {
            if (it is Node) {
                it.setOnMouseDragged {event ->
                    val tabContent = TabDeck.tabPane.selectionModel.selectedItem
                    val clickedNode = event.source
                    if (tabContent is SavableTab && clickedNode is Savable) {
                        if (!dragging) {
                            println("creating node on beginning of drag")
                            draggingNode = clickedNode.copySelf()!!
                            tabContent.pane.children.add(draggingNode as Node)
                            dragging = true
                        }
                        //translating node to mouse location on the pane
                        (draggingNode as Node).translateX = TabDeck.snapToGrid(event.sceneX - pressedLocation.first)
                        (draggingNode as Node).translateY = TabDeck.snapToGrid(event.sceneY - TabDeck.tabHeight - MenuDeck.height - pressedLocation.second)
                    } else {
                        println("you are not on a Savable tab")
                    }
                }
                it.setOnMouseReleased {
                    dragging = false
                }
                it.setOnMousePressed { event ->
                    pressedLocation = Pair(event.x, event.y)
                }
            }
        }


        libraryTitle.font = Font(25.0)
        this.style = "-fx-background-color: #4287f5"
        this.alignment = Pos.CENTER
        this.children.addAll(libraryTitle, actionButton, toggleButton, textBox)
    }
}