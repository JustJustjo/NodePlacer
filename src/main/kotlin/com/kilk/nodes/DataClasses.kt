package com.kilk.nodes

data class SavedTabDeck (
    val tabs: ArrayList<String>?,
    val style: String
)
data class SavedTab (
    val text: String,
    val style: String,
    val children: ArrayList<String>
)
data class SavedNode (
    val nodeName: String,
    val data: String
)
enum class ButtonType{
    SET,
    ADD,
    SUBTRACT
}
enum class TextBoxType {
    WRITE,
    READ
}
enum class PublishAction {
    PRINT,
    NETWORKTABLES,
    NONE
}
