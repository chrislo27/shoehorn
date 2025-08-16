package com.swingnosefrog.shoehorn.webserver.util

import io.javalin.http.Context


object RealIPFromHeader {
    
    fun getRealIPFromHeader(ctx: Context): String {
        return ctx.header("CF-Connecting-IP")?.takeUnless { it.isBlank() }
            ?: ctx.header("X-Real-IP")?.takeUnless { it.isBlank() }
            ?: ctx.header("X-Forwarded-For")?.takeUnless { it.isBlank() }
            ?: ctx.ip()
    }
}

fun Context.getRealIPFromHeader(): String = RealIPFromHeader.getRealIPFromHeader(this)

