package com.kilk

import edu.wpi.first.networktables.NetworkTableInstance
import javafx.application.Platform
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

object NTClient {
    val ntInstance = NetworkTableInstance.getDefault()
    private val timer = Timer()
    private var connectionJob: Job? = null
    private var reconnected: Boolean = true

    val fmsTable = ntInstance.getTable("FMSInfo")

    val isRedEntry = fmsTable.getBooleanTopic("IsRedAlliance").subscribe(true)

    var ipAddress = "localhost"
    var quarterCount = 0


    val isRed: Boolean
        get() = isRedEntry.get()


    init {
        println("NTClient says hi!!")
        initConnectionStatusCheck()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun connect() {
        val address = ipAddress
        println("Connecting to address: $address")

        connectionJob?.cancel()

        connectionJob = GlobalScope.launch {
            // shut down previous server, if connected
            if (ntInstance.isConnected) {
                ntInstance.stopClient()
            }

            // reconnect with new address
            ntInstance.startClient4("NodePlacer")
            ntInstance.setServer(address)
        }
    }

    private fun initConnectionStatusCheck() { //loop that checks for connection
        println("inside initConnectionStatusCheck")
        val updateFrequencyInSeconds = 1
        timer.schedule(object : TimerTask() {
            override fun run() {
                // check network table connection
                if (!ntInstance.isConnected) {
                    // attempt to connect
                    println("\u001b[33m" + "Not Connected!!!! Connecting to network table..." + "\u001b[0m")
                    connect()
                }

                Platform.runLater {
                    if (!ntInstance.isConnected) { //QUARTER!!!!!
                        reconnected = false
                        quarterCount += 1
//                        println("Quarter count: $quarterCount")
                    } else {
                        secondConnect()
                    }
                    TabDeck.updateBorder()
                }
            }
        }, 10, 1000L * updateFrequencyInSeconds)
    }
    fun secondConnect() { // fix for a strange NetworkTable update bug
        if (!reconnected) {
            connect()
            reconnected = true
        }
    }

    fun disconnect() {
        timer.cancel()
        if (ntInstance.isConnected) {
            println("NTClient.disconnect stopping networkTableInstance")
            ntInstance.stopDSClient()
            ntInstance.stopClient()
        }
    }
}