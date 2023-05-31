package com.kilk

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.control.Button
import javafx.scene.control.TextField
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
        val savedNodeLists: List<SavedNode> = Gson().fromJson(file.readText(), collectionType) as List<SavedNode>
        println(savedNodeLists)
        savedNodeLists.forEach {
            when (it.nodeType) {
                NodeType.BUTTON -> Dashboard.placeNode(it)
                else -> println("what???")
            }
        }
    }
    fun saveButtons(list: ObservableList<ActionButton>?) {
        println("Inside saveButtons. $list")
        val file = fileChooser.showSaveDialog(NodePlacer.stage)
        val gson = Gson()
        val savedNodeList: ObservableList<SavedNode> = FXCollections.observableArrayList()
        list?.forEach {
            savedNodeList.add(SavedNode(it.nodeType, it.actionType, it.actionValue, it.translateX, it.translateY, it.width, it.height, it.text, it.font.size))
        }
        println(savedNodeList)
        val jsonString = gson.toJson(savedNodeList)
        file.writeText(jsonString)
    }
}
data class SavedNode(
    val nodeType: NodeType,
    val actionType: ActionType,
    val actionValue: Any = 1,
    val x: Double,
    val y: Double,
    val width: Double = 40.0,
    val height: Double = 40.0,
    val text: String = "Hi",
    var fontSize: Double = 15.0
)
enum class ActionType{
    PRINTLN,
    TOGGLE,
    SET,
    ADD,
    SUBTRACT,
    DIVIDE,
    MULTIPLY
}
enum class NodeType{
    BUTTON,
//    TEXTBOX,
    TEXTFIELD
}
class ActionButton(text: String, type: ActionType = ActionType.PRINTLN, actionValue: Any): Button(text){
    val nodeType: NodeType = NodeType.BUTTON
    val actionValue = actionValue
    var x = 0.0
    var y = 0.0
    var actionType: ActionType = type
        set(value) {
            println(value)
            field = value
        }
}
class ActionTextField(text: String, type: ActionType = ActionType.PRINTLN, actionValue: Any): TextField(text) {
    val nodeType: NodeType = NodeType.TEXTFIELD
    val actionValue = actionValue
    var actionType: ActionType = type
        set(value) {
            println(value)
            field = value
        }
}