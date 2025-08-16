package com.swingnosefrog.shoehorn.util

import kotlin.concurrent.thread


inline fun addShutdownHook(threadName: String, crossinline action: () -> Unit) {
    Runtime.getRuntime().addShutdownHook(thread(start = false, name = threadName) {
        try {
            action()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    })
}
