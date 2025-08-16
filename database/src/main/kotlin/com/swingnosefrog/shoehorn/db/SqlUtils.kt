package com.swingnosefrog.shoehorn.db

import java.sql.PreparedStatement
import java.sql.ResultSet
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.collections.plusAssign
import kotlin.let


fun ResultSet.getLocalDateFromText(columnName: String): LocalDate =
    LocalDate.parse(this.getString(columnName), DateTimeFormatter.ISO_DATE)

fun ResultSet.getNullableLocalDateFromText(columnName: String): LocalDate? =
    this.getString(columnName)?.let { s -> LocalDate.parse(s, DateTimeFormatter.ISO_DATE) }

fun PreparedStatement.setLocalDateAsText(parameterIndex: Int, value: LocalDate?) =
    this.setString(parameterIndex, value?.format(DateTimeFormatter.ISO_DATE))

fun ResultSet.getLocalDateTimeFromText(columnName: String): LocalDateTime =
    LocalDateTime.parse(this.getString(columnName), DateTimeFormatter.ISO_LOCAL_DATE_TIME)

fun ResultSet.getNullableLocalDateTimeFromText(columnName: String): LocalDateTime? =
    this.getString(columnName)?.let { s -> LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME) }

fun PreparedStatement.setLocalDateTimeAsText(parameterIndex: Int, value: LocalDateTime?) =
    this.setString(parameterIndex, value?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))

fun ResultSet.getUUID(columnName: String): UUID =
    UUID.fromString(this.getString(columnName))

fun ResultSet.getNullableUUID(columnName: String): UUID? =
    UUID.fromString(this.getString(columnName))

fun PreparedStatement.setUUID(parameterIndex: Int, value: UUID?) =
    this.setString(parameterIndex, value?.toString())

fun <R> PreparedStatement.executeQueryAndCollect(mapper: (ResultSet) -> R): List<R> {
    val res = this.executeQuery()
    val collection = mutableListOf<R>()
    while (res.next()) {
        collection += mapper(res)
    }
    return collection
}
