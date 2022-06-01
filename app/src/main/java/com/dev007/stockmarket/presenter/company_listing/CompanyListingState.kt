package com.dev007.stockmarket.presenter.company_listing

import com.dev007.stockmarket.domain.models.CompanyListing

data class CompanyListingState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)