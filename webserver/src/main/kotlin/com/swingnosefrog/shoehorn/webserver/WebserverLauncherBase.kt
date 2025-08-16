package com.swingnosefrog.shoehorn.webserver

import com.beust.jcommander.JCommander
import com.swingnosefrog.shoehorn.util.addShutdownHook
import io.javalin.Javalin
import io.javalin.config.JavalinConfig
import org.slf4j.simple.SimpleLogger


abstract class WebserverLauncherBase<Server : IServer, LaunchArguments : IServerLaunchArguments> {

    companion object {

        private fun configureSlf4j() {
            System.setProperty(SimpleLogger.LOG_FILE_KEY, "System.out")
            System.setProperty(SimpleLogger.SHOW_DATE_TIME_KEY, "true")
            System.setProperty(SimpleLogger.DATE_TIME_FORMAT_KEY, "yyyy-MM-dd HH:mm:ss.SSS")
        }
    }

    protected lateinit var javalin: Javalin
    protected lateinit var serverInstance: Server

    protected fun mainEntrypoint(rawArgs: Array<String>) {
        // Configure SLF4J with System.setProperty -- this MUST be first
        configureSlf4j()

        // Parse arguments
        val arguments = parseAndCheckLaunchArguments(rawArgs)

        // Create server instance
        val server = createServer(arguments)
        this.serverInstance = server

        // Create Javalin instance
        javalin = Javalin.create { javalinConfig ->
            onJavalinCreate(javalinConfig, arguments)

            server.beforeStart(javalinConfig)
        }

        // Add shutdown hooks
        addServerShutdownHook(serverInstance)
        addJavalinShutdownHook(javalin)

        // Start everything
        javalin.start(arguments.port)
        server.afterStart(javalin)
    }

    protected abstract fun createEmptyLaunchArguments(): LaunchArguments
    
    protected abstract fun createServer(arguments: LaunchArguments): Server
    
    protected abstract fun onJavalinCreate(javalinConfig: JavalinConfig, arguments: LaunchArguments)

    protected open fun checkLaunchArguments(arguments: LaunchArguments) {
        // This is where you may throw an error if some configuration is incorrect
    }


    private fun parseAndCheckLaunchArguments(rawArgs: Array<String>): LaunchArguments {
        val arguments: LaunchArguments = createEmptyLaunchArguments()
        val jcommander = JCommander.newBuilder()
            .acceptUnknownOptions(false)
            .addObject(arguments)
            .build()
        jcommander.parse(*rawArgs)

        checkLaunchArguments(arguments)

        return arguments
    }

    private fun addJavalinShutdownHook(javalin: Javalin) {
        addShutdownHook("Javalin Stop Hook") {
            javalin.stop()
        }
    }

    private fun addServerShutdownHook(server: IServer) {
        addShutdownHook("IServer Shutdown Hook") {
            server.onShutdown()
        }
    }
}