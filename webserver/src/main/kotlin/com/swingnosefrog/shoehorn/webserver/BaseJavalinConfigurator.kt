package com.swingnosefrog.shoehorn.webserver

import com.swingnosefrog.shoehorn.webserver.util.getRealIPFromHeader
import io.javalin.config.JavalinConfig
import io.javalin.http.staticfiles.Location
import io.javalin.http.staticfiles.StaticFileConfig
import org.eclipse.jetty.server.handler.ContextHandler

open class BaseJavalinConfigurator {

    data class Config(
        val virtualHosts: List<String>,
        val staticFileConfigs: List<(StaticFileConfig) -> Unit>,
    ) {

        companion object {

            fun staticFilesAt(directory: String, location: Location): (StaticFileConfig) -> Unit = {
                it.directory = directory
                it.location = location
            }
        }
    }

    open fun configure(factoryConfig: Config, javalinConfig: JavalinConfig) {
        javalinConfig.showJavalinBanner = false
        javalinConfig.useVirtualThreads = false // VTs may cause random deadlocks
        javalinConfig.jetty.modifyServer { server ->
            server.handler = ContextHandler().also { ctxHandler ->
                if (factoryConfig.virtualHosts.isNotEmpty()) {
                    ctxHandler.addVirtualHosts(factoryConfig.virtualHosts.toTypedArray())
                }
            }
        }
        javalinConfig.contextResolver.ip = { ctx -> ctx.getRealIPFromHeader() }

        // Setup static files before route mapping
        val staticFileConfig = factoryConfig.staticFileConfigs
        if (staticFileConfig.isNotEmpty()) {
            staticFileConfig.forEach { configConsumer ->
                javalinConfig.staticFiles.add(configConsumer)
            }
        }
    }
}