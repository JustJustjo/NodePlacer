package com.kilk

import javafx.application.Application
import javafx.geometry.Rectangle2D
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Screen
import javafx.stage.Stage

class NodePlacer: Application() {
    companion object {
        lateinit var stage: Stage

        @JvmStatic
        fun main(args: Array<String>) {
            println("Launching NodePlacer...")
            launch(NodePlacer::class.java, *args)
        }
    }
    override fun start(stage: Stage) {
        val screen = Screen.getPrimary()

        stage.title = "NodePlacer"
        stage.icons.add(Image("node-icon.png"))
        Companion.stage = stage

        val bounds = Rectangle2D(screen.visualBounds.minX, screen.visualBounds.minY, screen.visualBounds.width, screen.visualBounds.height)
        stage.scene = Scene(Dashboard, bounds.width, bounds.height)
        stage.sizeToScene()
        stage.show()
    }
}