package com.kilk

import com.kilk.TabDeck.editMode
import javafx.scene.control.Button
import javafx.scene.control.ScrollPane
import javafx.scene.control.SplitPane

object NodeSplitPane: SplitPane() {
    init {
        this.setDividerPositions(0.75)
        updateSplitPane()
    }
    fun updateSplitPane() {
        this.items.clear()
        if (editMode) {
            this.items.addAll(TabDeck, ScrollPane(Button("hello")))
        } else {
            this.items.addAll(TabDeck)
        }
        this.setDividerPositions(0.75)
    }
}