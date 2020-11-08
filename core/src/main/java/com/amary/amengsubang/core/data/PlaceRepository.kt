package com.amary.amengsubang.core.data

import com.amary.amengsubang.core.data.datasource.local.LocalDataSource
import com.amary.amengsubang.core.data.datasource.local.entity.mapToDomain
import com.amary.amengsubang.core.data.datasource.local.entity.mapToEntity
import com.amary.amengsubang.core.data.datasource.remote.RemoteDataSource
import com.amary.amengsubang.core.data.datasource.remote.network.NetworkResponse
import com.amary.amengsubang.core.data.datasource.remote.response.DetailPlaceResponse
import com.amary.amengsubang.core.data.datasource.remote.response.PlaceResponse
import com.amary.amengsubang.core.data.datasource.remote.response.mapToEntity
import com.amary.amengsubang.core.utils.NetworkBoundResource
import com.amary.amengsubang.domain.model.FavoriteDomain
import com.amary.amengsubang.domain.model.PlaceDetailDomain
import com.amary.amengsubang.domain.model.PlaceDomain
import com.amary.amengsubang.domain.model.PlaceFavoriteDomain
import com.amary.amengsubang.domain.repository.IPlaceRepository
import com.amary.amengsubang.domain.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class PlaceRepository(
        private val remoteDataSource: RemoteDataSource,
        private val localDataSource: LocalDataSource
) : IPlaceRepository {

    override fun getRemoteAllPlace(): Flow<Resource<List<PlaceDomain>>> =
            object : NetworkBoundResource<List<PlaceDomain>, List<PlaceResponse>>() {
                override fun loadFromDB(): Flow<List<PlaceDomain>> {
                    return localDataSource.getCacheAllPlace().map {
                        it.mapToDomain()
                    }
                }

                override fun shouldFetch(data: Flow<List<PlaceDomain>>?) =
                        flow {
                            emit(true)
                        }.flowOn(Dispatchers.IO)

                override suspend fun createCall(): Flow<NetworkResponse<List<PlaceResponse>>> =
                        remoteDataSource.getRemoteAllPlace()

                override suspend fun saveCallResult(data: List<PlaceResponse>) {
                    localDataSource.insertAllPlace(data.mapToEntity())
                }
            }.asFlow()

    override fun getPlaceSearch(search: String): Flow<Resource<List<PlaceDomain>>> {
        return flow {
            localDataSource.getPlaceSearch(search).collect {
                emit(Resource.Loading())
                if (it.isNotEmpty()) {
                    emit(Resource.Success(it.mapToDomain()))
                } else {
                    emit(Resource.Error())
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getFavoriteAllPlace(): Flow<Resource<List<PlaceFavoriteDomain>>> {
        return flow {
            localDataSource.getFavoriteAllPlace().collect {
                emit(Resource.Loading())
                if (it.isNotEmpty()) {
                    emit(Resource.Success(it.mapToDomain()))
                } else {
                    emit(Resource.Error())
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun isFavorite(placeId: String): Flow<Int> {
        return flow {
            localDataSource.isFavorite(placeId).collect { emit(it) }
        }.flowOn(Dispatchers.IO)
    }

    override fun getDetailPlace(placeId: String): Flow<Resource<PlaceDetailDomain>> =
            object : NetworkBoundResource<PlaceDetailDomain, DetailPlaceResponse>(){
                override fun loadFromDB(): Flow<PlaceDetailDomain> {
                    return localDataSource.getPlaceDetail(placeId).map {
                        it.mapToDomain()
                    }
                }

                override fun shouldFetch(data: Flow<PlaceDetailDomain>?) =
                        flow {
                            emit(true)
                        }.flowOn(Dispatchers.IO)

                override suspend fun createCall(): Flow<NetworkResponse<DetailPlaceResponse>> {
                    return remoteDataSource.getRemoteDetailPlace(placeId)
                }

                override suspend fun saveCallResult(data: DetailPlaceResponse) {
                    localDataSource.insertPlaceDetail(data.mapToEntity())
                }
            }.asFlow()

    override suspend fun insertFavoritePlace(favoriteDomain: FavoriteDomain) {
        return localDataSource.insertFavoritePlace(favoriteDomain.mapToEntity())
    }

    override suspend fun deleteFavorite(placeId: String) {
        return localDataSource.deleteFavorite(placeId)
    }
}