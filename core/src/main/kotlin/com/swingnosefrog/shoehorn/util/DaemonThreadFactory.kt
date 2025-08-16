package com.swingnosefrog.shoehorn.util

import java.util.concurrent.ThreadFactory
import kotlin.apply


open class DaemonThreadFactory(private val onCreate: (Thread) -> Unit = {}) : ThreadFactory {

    override fun newThread(r: Runnable): Thread {
        return Thread(r).apply {
            onCreate(this)
            isDaemon = true
        }
    }
}