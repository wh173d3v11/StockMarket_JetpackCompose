package com.dev007.stockmarket.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListEntity( //got from csv from API
    val name: String,
    val symbol: String,
    val exchange: String,
    @PrimaryKey val id: Int? = null
)