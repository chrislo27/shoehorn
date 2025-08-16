package com.swingnosefrog.shoehorn.db

import java.sql.Connection


interface IDbConnectionProvider {
    
    fun getConnection(): Connection
}