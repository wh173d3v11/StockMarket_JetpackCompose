package com.dev007.stockmarket.presenter.company_listing

sealed class CompanyListingsEvent {
    object Refresh : CompanyListingsEvent()
    data class OnSearchQueryChange(val query: String) : CompanyListingsEvent()
}
