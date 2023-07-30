package com.kilk

import com.kilk.TabDeck.editMode
import javafx.scene.control.SplitPane

object NodeSplitPane: SplitPane() {
    init {
        updateSplitPane()
    }
    fun updateSplitPane() {
        this.items.clear()
        if (editMode) {
            this.items.addAll(TabDeck, NodeLibrary)
        } else {
            this.items.addAll(TabDeck)
        }
        this.setDividerPositions(0.85)
    }
}