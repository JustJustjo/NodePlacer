package com.kilk

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Button
import javafx.stage.FileChooser
import java.lang.reflect.Type

object NodeSaver {
    val fileChooser = FileChooser()
    init {
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("NodePlacer files", "*.json"))
    }
    fun saveButtons(list: ObservableList<Button>?) {
        println("Inside saveButtons. $list")
        val file = fileChooser.showSaveDialog(NodePlacer.stage)
        val gson = Gson()
        val savedNodeList: ObservableList<SavedNode> = FXCollections.observableArrayList()
        list?.forEach {
            savedNodeList.add(SavedNode(it.text, it.width, it.height, it.translateX, it.translateY))
        }
        println(savedNodeList)
        val jsonString = gson.toJson(savedNodeList)
        file.writeText(jsonString)
    }
    fun loadButtons() {
        val file = fileChooser.showOpenDialog(NodePlacer.stage)
        val collectionType: Type = object : TypeToken<List<SavedNode?>?>() {}.type
        val savedNodeList: List<SavedNode> = Gson().fromJson(file.readText(), collectionType) as List<SavedNode>
        println(savedNodeList)
        savedNodeList.forEach {
            Dashboard.placeButton(it.text, it.width, it.height, it.x, it.y)
        }
    }
}
data class SavedNode(
//    val type: NodeType,
    val text: String,
    val width: Double,
    val height: Double,
    val x: Double,
    val y: Double
)
enum class NodeType{
    BUTTON
}