package com.kilk.nodes

import javafx.scene.control.Button

class BetterToggleButton(text: String, defautValue: Boolean = false, borderWeight: Int = 5, val borderColor: String = "red"): Button(text) {
    var value: Boolean = defautValue
    val borderString = "$borderWeight $borderWeight $borderWeight $borderWeight"
    init {
        if (!this.style.contains("-fx-border-width:")) {
            this.style += "-fx-border-color: $borderColor; -fx-border-width: 0 0 0 0"
        }
        value = !value
        toggle()
        println(this.style)
    }
    fun toggle() {
        value = !value
        if (value) {
            this.style = this.style.replace("-fx-border-color: $borderColor; -fx-border-width: 0 0 0 0", "-fx-border-color: $borderColor; -fx-border-width: $borderString")
        } else {
            this.style = this.style.replace("-fx-border-color: $borderColor; -fx-border-width: $borderString", "-fx-border-color: $borderColor; -fx-border-width: 0 0 0 0")
        }
        println(this.style)
    }
}