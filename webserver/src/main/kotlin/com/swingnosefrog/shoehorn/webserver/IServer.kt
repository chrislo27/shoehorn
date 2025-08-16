package com.swingnosefrog.shoehorn.webserver

import io.javalin.Javalin
import io.javalin.config.JavalinConfig
import org.slf4j.Logger


interface IServer {
    
    val logger: Logger

    /**
     * Called just before [Javalin.start] is called.
     */
    fun beforeStart(javalinConfig: JavalinConfig)

    /**
     * Called right after [Javalin.start] is called.
     */
    fun afterStart(javalin: Javalin)

    /**
     * Called as part of the shutdown hook.
     */
    fun onShutdown()
    
}
