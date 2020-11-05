package com.amary.amengsubang.core.utils

import com.amary.amengsubang.core.data.datasource.remote.network.NetworkResponse
import com.amary.amengsubang.domain.utils.Resource
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val networkResponse = createCall().first()) {
                is NetworkResponse.Success -> {
                    saveCallResult(networkResponse.data)
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is NetworkResponse.Empty -> {
                    emitAll(loadFromDB().map { Resource.Success(it) })
                }
                is NetworkResponse.Error -> {
                    onFetchFailed()
                    emit(Resource.Error(networkResponse.errorMessage))
                }
            }
        } else {
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<NetworkResponse<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    fun asFlow(): Flow<Resource<ResultType>> = result
}