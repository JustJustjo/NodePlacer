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
    fun loadButtons() {
        val file = fileChooser.showOpenDialog(NodePlacer.stage)
        val collectionType: Type = object : TypeToken<List<SavedNode?>?>() {}.type
        val savedNodeList: List<SavedNode> = Gson().fromJson(file.readText(), collectionType) as List<SavedNode>
        println(savedNodeList)
        savedNodeList.forEach {
            Dashboard.placeButton(it)
        }
    }
    fun saveButtons(list: ObservableList<Button>?) {
        println("Inside saveButtons. $list")
        val file = fileChooser.showSaveDialog(NodePlacer.stage)
        val gson = Gson()
        val savedNodeList: ObservableList<SavedNode> = FXCollections.observableArrayList()
        list?.forEach {
            savedNodeList.add(SavedNode(it.translateX, it.translateY, it.width, it.height, it.text, it.font.size))
        }
        println(savedNodeList)
        val jsonString = gson.toJson(savedNodeList)
        file.writeText(jsonString)
    }
}
data class SavedNode(
//    val type: NodeType,
    val x: Double,
    val y: Double,
    val width: Double = 50.0,
    val height: Double = 50.0,
    val text: String = "Hi",
    var fontSize: Double = 15.0
)
enum class NodeType{
    BUTTON
}