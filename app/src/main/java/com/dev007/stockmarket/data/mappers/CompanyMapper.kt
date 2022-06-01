package com.dev007.stockmarket.data.mappers

import com.dev007.stockmarket.data.local.CompanyListEntity
import com.dev007.stockmarket.domain.models.CompanyListing

fun CompanyListEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListEntity(): CompanyListEntity {
    return CompanyListEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}