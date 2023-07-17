package com.kilk

import com.fasterxml.jackson.module.kotlin.readValue
import com.kilk.nodes.SavedTabDeck
import javafx.stage.FileChooser

object NodeSaver {
    val fileChooser = FileChooser()
    init {
        fileChooser.extensionFilters.add(FileChooser.ExtensionFilter("NodePlacer files", "*.json"))
    }
    fun saveNodes() {
        println("Attempting to save nodes")
        val exportFile = fileChooser.showSaveDialog(NodePlacer.stage)
        exportFile.writeText(TabDeck.getJson())
        println(exportFile.readText())
        println("Saved json to: ${exportFile.absolutePath}")
    }

    fun loadNodes() {
        val importFile = fileChooser.showOpenDialog(NodePlacer.stage)
        println(importFile.readText())
        val savedTabDeck: SavedTabDeck = TabDeck.jsonMapper.readValue(importFile.readText())
        TabDeck.loadChildren(savedTabDeck.tabs)

    }





//    fun loadButtons() {
//        val collectionType: Type = object : TypeToken<List<SavedNode?>?>() {}.type
//        val savedNodeLists: List<SavedNode> = Gson().fromJson(file.readText(), collectionType) as List<SavedNode>
//        println(savedNodeLists)
//        savedNodeLists.forEach {
//            when (it.nodeType) {
//                NodeType.BUTTON -> TabDeck.placeNode(it)
//                else -> println("what???")
//            }
//        }
//    }
//    fun saveButtons(list: ObservableList<ActionButton>?) {
//        println("Inside saveButtons. $list")
//        val gson = Gson()
//        val savedNodeList: ObservableList<SavedNode> = FXCollections.observableArrayList()
//        list?.forEach {
//            savedNodeList.add(SavedNode(it.nodeType, it.actionType, it.actionValue, it.translateX, it.translateY, it.width, it.height, it.text, it.font.size))
//        }
//        println(savedNodeList)
//        val jsonString = gson.toJson(savedNodeList)
//        file.writeText(jsonString)
//    }
}
//data class SavedNode(
//    val nodeType: NodeType,
//    val actionType: ActionType,
//    val actionValue: Any = 1,
//    val x: Double,
//    val y: Double,
//    val width: Double = 40.0,
//    val height: Double = 40.0,
//    val text: String = "Hi",
//    var fontSize: Double = 15.0
//)



