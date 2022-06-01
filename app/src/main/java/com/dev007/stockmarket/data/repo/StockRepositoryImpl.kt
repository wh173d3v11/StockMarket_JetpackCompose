package com.dev007.stockmarket.data.repo

import com.dev007.stockmarket.data.csv.CSVParser
import com.dev007.stockmarket.data.local.StockDatabase
import com.dev007.stockmarket.data.mappers.toCompanyListEntity
import com.dev007.stockmarket.data.mappers.toCompanyListing
import com.dev007.stockmarket.data.remote.StockApi
import com.dev007.stockmarket.domain.models.CompanyListing
import com.dev007.stockmarket.domain.repo.StockRepository
import com.dev007.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>
) : StockRepository {

    private val dao = db.dao


    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading<List<CompanyListing>>(true))
            //local db
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success<List<CompanyListing>>(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isEmpty()
            val justLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (justLoadFromCache) {
                emit(Resource.Loading<List<CompanyListing>>(false))
                return@flow
            }

            //API call.
            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {//parsing Error
                emit(Resource.Error<List<CompanyListing>>("Couldn't Load the data"))
                null
            } catch (e: HttpException) { //invalid response
                e.printStackTrace()
                emit(Resource.Error<List<CompanyListing>>("Invalid Response"))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map {
                        it.toCompanyListEntity()
                    }
                )
                emit(Resource.Success<List<CompanyListing>>(
                    data = dao.searchCompanyListing("").map {
                        it.toCompanyListing()
                    }
                ))
                emit(Resource.Loading<List<CompanyListing>>(false))
            }
        }
    }
}