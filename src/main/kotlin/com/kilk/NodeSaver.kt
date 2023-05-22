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
        val collectionType: Type = object : TypeToken<List<SavedButton?>?>() {}.type
        val savedButtonList: List<SavedButton> = Gson().fromJson(file.readText(), collectionType) as List<SavedButton>
        println(savedButtonList)
        savedButtonList.forEach {
            Dashboard.placeButton(it)
        }
    }
    fun saveButtons(list: ObservableList<ActionButton>?) {
        println("Inside saveButtons. $list")
        val file = fileChooser.showSaveDialog(NodePlacer.stage)
        val gson = Gson()
        val savedButtonList: ObservableList<SavedButton> = FXCollections.observableArrayList()
        list?.forEach {
            savedButtonList.add(SavedButton(it.actionType, it.translateX, it.translateY, it.width, it.height, it.text, it.font.size))
        }
        println(savedButtonList)
        val jsonString = gson.toJson(savedButtonList)
        file.writeText(jsonString)
    }
}
data class SavedButton(
    val actionType: ActionType,
    val x: Double,
    val y: Double,
    val width: Double = 50.0,
    val height: Double = 50.0,
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
class ActionButton(text: String, type: ActionType = ActionType.PRINTLN): Button(text){
    var actionType: ActionType = type
        set(value) {
            println(value)
            field = value
        }
}