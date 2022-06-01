package com.dev007.stockmarket.di

import com.dev007.stockmarket.data.csv.CSVParser
import com.dev007.stockmarket.data.csv.CompanyListingParser
import com.dev007.stockmarket.data.repo.StockRepositoryImpl
import com.dev007.stockmarket.domain.models.CompanyListing
import com.dev007.stockmarket.domain.repo.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(
        companyListingParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}