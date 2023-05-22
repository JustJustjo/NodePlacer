package com.kilk

class Main {
    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            println("In main main")
            NodePlacer.main(args)
        }
    }
}