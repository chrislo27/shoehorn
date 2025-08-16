package com.swingnosefrog.shoehorn.util

import java.util.*


val NULL_UUID: UUID = UUID(0, 0)

fun String.toUUID(): UUID = UUID.fromString(this)

fun String.toUUIDOrNull(): UUID? = try {
    UUID.fromString(this)
} catch (_: IllegalArgumentException) {
    null
}
