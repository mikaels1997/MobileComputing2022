package com.example.mobilecomputing.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "accounts",
    indices = [
        Index("username", unique = true)
    ]
)

data class Account (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "username") val name: String,
    @ColumnInfo(name = "password") val password: String
)