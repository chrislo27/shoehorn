package com.swingnosefrog.shoehorn.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import java.io.File
import java.io.PrintWriter
import java.sql.Connection
import java.util.*
import kotlin.apply
import kotlin.io.nameWithoutExtension


interface IDbConnectionProviderSqlite : IDbConnectionProvider

open class DbConnectionProviderSqlite(val file: File) : IDbConnectionProviderSqlite {

    private val hikariProps = Properties().apply {
        put("dataSource.logWriter", PrintWriter(System.out))
    }
    private val dataSource: HikariDataSource = HikariDataSource(HikariConfig(hikariProps).apply {
        jdbcUrl = "jdbc:sqlite:${file.absolutePath}"
        this.poolName = "DbConnectionProviderSqlite-${file.nameWithoutExtension}"
        this.maximumPoolSize = 4
    })

    override fun getConnection(): Connection {
        @Suppress("UsePropertyAccessSyntax")
        return dataSource.getConnection()
    }
}