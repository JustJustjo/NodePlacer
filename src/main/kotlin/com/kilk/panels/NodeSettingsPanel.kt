package com.kilk.panels

import com.kilk.nodes.PublishAction
import javafx.collections.ObservableList
import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.layout.VBox
import java.awt.Desktop
import java.net.URI
import kotlin.math.roundToInt

open class NodeSettingsPanel(val node: Control): VBox(10.0) {
    //textbox to change the text of button
    val textInput = TextField()
    val textInputLabel = Label("Text:", textInput)
    //style textbox
    val styleInput = TextArea(node.style)
    val styleInputHyperlink = Hyperlink("CSS Style:")
//    var prevStyleInput: String = node.style
    //text size slider
    val fontSizeSlider = Slider(15.0, 400.0, 15.0)
    val fontSizeSliderLabel = Label("Text Size:", fontSizeSlider)
    //publish dropdown
    val publishActionDropdown = ComboBox<PublishAction>()
    val publishActionLabel = Label("Publish Type:", publishActionDropdown)



    var textSize: Int = fontSizeSlider.value.roundToInt()
        set(value) {
            node.style = node.style.replace("-fx-font-size: ${field}px", "-fx-font-size: ${value}px")
            styleInput.text = node.style
            println(node.style)
            field = value
        }

    val labelList = listOf(fontSizeSliderLabel, textInputLabel, styleInputHyperlink)

    init {
        for (label in labelList) {
            label.contentDisplay = ContentDisplay.BOTTOM
        }
        if (!node.style.contains("-fx-font-size:")) {
            node.style += "-fx-font-size: ${textSize}px;"
        }
//        if (!node.style.contains(styleInput.text)) {
//            node.style += "; "
//            prevStyleInput = "#"
//        }
        styleInputHyperlink.contentDisplay = ContentDisplay.BOTTOM
        styleInputHyperlink.setOnAction {
            Desktop.getDesktop().browse(URI("https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html"))
        }
        styleInput.isWrapText = true
        styleInput.setPrefSize(500.0, 60.0)
        styleInput.setOnKeyTyped {
//            node.style = node.style.removeSuffix(prevStyleInput)
            node.style = styleInput.text
//            prevStyleInput = styleInput.text
        }
        fontSizeSlider.setOnMouseDragged {
            textSize = fontSizeSlider.value.roundToInt()
        }
        publishActionLabel.contentDisplay = ContentDisplay.BOTTOM
        publishActionDropdown.items.addAll(PublishAction.PRINT, PublishAction.NETWORKTABLES, PublishAction.NONE)

        this.alignment = Pos.CENTER
        this.children.addAll(publishActionLabel, textInputLabel, fontSizeSliderLabel, styleInputHyperlink, styleInput)
    }
    fun ObservableList<Node>.addFirst(vararg elements: Node) {
        val newList: ArrayList<Node> = ArrayList()

        newList.addAll(elements)
        this.forEach {
            newList.add(it)
        }

        this.clear()
        this.addAll(newList)
    }
    fun ObservableList<Node>.addSecond(vararg elements: Node) {
        val newList: ArrayList<Node> = ArrayList()

        newList.add(this.first())
        this.remove(this.first())

        newList.addAll(elements)
        this.forEach {
            newList.add(it)
        }

        this.clear()
        this.addAll(newList)
    }
}