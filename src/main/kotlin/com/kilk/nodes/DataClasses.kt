package com.kilk.nodes

data class SavedTabDeck (
    val tabs: ArrayList<String>?
)
data class SavedTab (
    val tabType: TabType,
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
    val nodeAction: NodeAction,
    val defaultValue: Any,
    val actionValue: Any,
    val style: String,
    val showValueAsText: Boolean
)
data class SavedTextField (
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double,
    val textBoxType: TextBoxType,
    val nodeAction: NodeAction,
    val defaultValue: Any,
    val style: String
)

enum class NodeType{
    BUTTON,
    TEXTBOX
}
enum class ButtonType{
    TOGGLE,
    SET,
    ADD,
    SUBTRACT
}
enum class TextBoxType {
    WRITE,
    READ
}
enum class TabType {
    DEFAULT,
    CUSTOM
}

enum class NodeAction {
    PRINTLN,
    NT
}
