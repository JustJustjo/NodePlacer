package com.kilk

import com.kilk.nodes.ActionButton
import com.kilk.nodes.ActionToggleButton
import javafx.scene.layout.GridPane

object NodeLibrary: GridPane() {
    val actionButton = ActionButton(null, null, 100.0, 100.0, "Action Button")
    val toggleButton = ActionToggleButton(null, null, 100.0, 100.0, "Toggle Button")
//    val textBox = ActionTextBox
}