package com.kilk.panels

import com.kilk.TabDeck
import javafx.geometry.Pos
import javafx.scene.control.*
import javafx.scene.layout.VBox
import java.awt.Desktop
import java.net.URI
import kotlin.math.roundToInt

class TabSettingsPanel(val tab: Tab): VBox(10.0) {
    val textInput = TextField(tab.text)
    val textLabel = Label("Text:", textInput)
    val styleInput = TextArea(tab.style)
    val styleLabel = Hyperlink("Tab CSS Style:")
    val tabDeckStyleInput = TextArea(TabDeck.tabPane.style)
    val tabDeckStyleLabel = Label("TabPane Style:", tabDeckStyleInput)
    val fontSizeSlider = Slider(15.0, 400.0, 15.0)
    val fontSizeSliderLabel = Label("Text Size:", fontSizeSlider)
    val tabHeightSlider = Slider(30.0, 400.0, TabDeck.tabHeight)
    val tabWidthSlider = Slider(30.0, 400.0, TabDeck.tabWidth)
    val tabWidthLabel = Label("Tabs Width:", tabWidthSlider)
    val tabHeightLabel = Label("Tabs Height:", tabHeightSlider)

    var textSize: Int = fontSizeSlider.value.roundToInt()
        set(value) {
            tab.style = tab.style.replace("-fx-font-size: ${field}px", "-fx-font-size: ${value}px")
            styleInput.text = tab.style
            println(tab.style)
            field = value
        }

    init {
        this.setMinSize(1.0, 500.0)
        if (!tab.style.contains("-fx-font-size:")) {
            tab.style += "-fx-font-size: ${textSize}px;"
        }
        styleLabel.contentDisplay = ContentDisplay.BOTTOM
        styleLabel.setOnAction {
            Desktop.getDesktop().browse(URI("https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html"))
        }
        styleInput.isWrapText = true
        styleInput.setPrefSize(500.0, 60.0)
        styleInput.setOnKeyTyped {
            tab.style = styleInput.text
        }
        tabDeckStyleLabel.contentDisplay = ContentDisplay.BOTTOM
        tabDeckStyleInput.isWrapText = true
        tabDeckStyleInput.setPrefSize(500.0, 60.0)
        tabDeckStyleInput.setOnKeyTyped {
            TabDeck.tabPane.style = tabDeckStyleInput.text
        }
        fontSizeSliderLabel.contentDisplay = ContentDisplay.BOTTOM
        fontSizeSlider.setOnMouseDragged {
            textSize = fontSizeSlider.value.roundToInt()
        }
        textLabel.contentDisplay = ContentDisplay.BOTTOM
        textInput.setOnKeyTyped {
            tab.text = textInput.text
        }
        tabHeightLabel.contentDisplay = ContentDisplay.BOTTOM
        tabHeightSlider.setOnMouseDragged {
            TabDeck.tabHeight = tabHeightSlider.value
            tabDeckStyleInput.text = TabDeck.tabPane.style
        }
        tabWidthLabel.contentDisplay = ContentDisplay.BOTTOM
        tabWidthSlider.setOnMouseDragged {
            TabDeck.tabWidth = tabWidthSlider.value
            tabDeckStyleInput.text = TabDeck.tabPane.style
        }

        this.alignment = Pos.CENTER
        this.children.addAll(textLabel, fontSizeSliderLabel, tabHeightLabel, tabWidthLabel, styleLabel, styleInput, tabDeckStyleLabel)
    }
}