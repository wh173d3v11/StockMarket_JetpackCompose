package com.dev007.stockmarket.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dev007.stockmarket.data.local.CompanyListEntity

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListEntities: List<CompanyListEntity>
    )

    @Query("DELETE FROM companylistentity")
    suspend fun clearCompanyListings()

    @Query(
        """
        SELECT *
        FROM companylistentity
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
        UPPER(:query) == symbol
    """
    )
    suspend fun searchCompanyListing(query: String): List<CompanyListEntity>
}