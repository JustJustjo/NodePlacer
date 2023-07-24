package com.kilk.nodes

data class SavedTabDeck (
    val tabs: ArrayList<String>?
)
data class SavedTab (
    val text: String,
    val style: String,
    val children: ArrayList<String>
)
data class SavedActionButton (
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double,
    val text: String,
    val buttonType: ButtonType,
    val publishAction: PublishAction,
    val defaultValue: Any,
    val actionValue: Any,
    val style: String,
    val showValueAsText: Boolean,
    val entryKey: String?
)
data class SavedActionToggleButton (
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double,
    val text: String,
    val publishAction: PublishAction,
    val defaultValue: Boolean,
    val style: String,
    val showValueAsText: Boolean,
    val entryKey: String?
)
data class SavedTextBox (
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double,
    val text: String,
    val textBoxType: TextBoxType,
    val publishAction: PublishAction,
    val style: String
)
data class SavedNode (
    val nodeType: NodeType,
    val data: String
)

enum class NodeType{
    TOGGLEBUTTON,
    BUTTON,
    TEXTBOX
}
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
