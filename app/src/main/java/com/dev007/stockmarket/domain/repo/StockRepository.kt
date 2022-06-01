package com.dev007.stockmarket.domain.repo

import com.dev007.stockmarket.domain.models.CompanyListing
import com.dev007.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query:String
    ): Flow<Resource<List<CompanyListing>>>
}