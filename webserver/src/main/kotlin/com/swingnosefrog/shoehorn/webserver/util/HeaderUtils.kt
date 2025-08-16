package com.swingnosefrog.shoehorn.webserver.util

import io.javalin.http.Context


object RealIPFromHeader {
    
    const val HEADER_CLOUDFRONT_CONNECTING_IP: String = "CF-Connecting-IP"
    const val HEADER_X_REAL_IP: String = "X-Real-IP"
    const val HEADER_X_FORWARDED_FOR: String = "X-Forwarded-For"
    
    var headerCheckOrder: List<String> = listOf(
        HEADER_CLOUDFRONT_CONNECTING_IP,
        HEADER_X_REAL_IP,
        HEADER_X_FORWARDED_FOR,
    )
    
    private fun checkHeadersInOrder(ctx: Context): String? {
        val headersToCheck = headerCheckOrder

        for (header in headersToCheck) {
            val headerValue = ctx.header(header)?.takeUnless { it.isBlank() } ?: continue
            return headerValue
        }
        
        return null
    }
    
    fun getRealIPFromHeader(ctx: Context): String {
        return checkHeadersInOrder(ctx) ?: ctx.ip()
    }
}

fun Context.getRealIPFromHeader(): String = RealIPFromHeader.getRealIPFromHeader(this)
