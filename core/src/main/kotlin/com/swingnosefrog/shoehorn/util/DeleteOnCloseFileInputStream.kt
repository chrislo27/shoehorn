package com.swingnosefrog.shoehorn.util

import java.io.File
import java.io.FileInputStream


open class DeleteOnCloseFileInputStream(private val file: File) : FileInputStream(file) {

    override fun close() {
        try {
            super.close()
        } finally {
            file.delete()
        }
    }
}