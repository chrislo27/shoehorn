package com.swingnosefrog.shoehorn.webserver.util

import java.io.InputStream
import java.net.URL
import kotlin.io.bufferedReader
import kotlin.io.readText
import kotlin.jvm.java
import kotlin.let


private object ClasspathUtilsObject // Used for classloader only

fun classpathResource(path: String): URL = ClasspathUtilsObject::class.java.classLoader.getResource(path) ?: error("Could not find classpath resource $path")

fun classpathResourceStream(path: String): InputStream = ClasspathUtilsObject::class.java.classLoader.getResourceAsStream(path) ?: error(
    "Could not find classpath resource $path"
)

fun readClasspathResourceAsString(path: String): String = classpathResourceStream(path).bufferedReader().let {
    val s = it.readText()
    it.close()
    s
}
