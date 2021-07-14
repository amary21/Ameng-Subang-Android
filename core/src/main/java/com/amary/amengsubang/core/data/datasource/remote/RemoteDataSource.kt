package com.amary.amengsubang.core.data.datasource.remote

import android.util.Log
import com.amary.amengsubang.core.data.datasource.remote.network.BaseNetwork
import com.amary.amengsubang.core.data.datasource.remote.network.NetworkResponse
import com.amary.amengsubang.core.data.datasource.remote.response.DetailPlaceResponse
import com.amary.amengsubang.core.data.datasource.remote.response.PlaceResponse
import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class RemoteDataSource : BaseNetwork() {

    companion object{
        private const val TAG = "RemoteDataSource"
    }

    suspend fun getRemoteAllPlace(): Flow<NetworkResponse<List<PlaceResponse>>> {
        return flow {
            try {
                val query = listPlace().get().await()
                val result = query.toObjects(PlaceResponse::class.java)
                if (result.isNotEmpty()){
                    emit(NetworkResponse.Success(result))
                } else {
                    emit(NetworkResponse.Empty)
                }

            } catch (e : Exception){
                emit(NetworkResponse.Error(e.message.toString()))
                Log.e(TAG, e.message.toString() )
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRemoteDetailPlace(id: String): Flow<NetworkResponse<DetailPlaceResponse>>{
        return flow {
            try {
                val query = detailPlace(id).get().await()
                val result = query.toObjects(DetailPlaceResponse::class.java)
                if (result.isNotEmpty()){
                    emit(NetworkResponse.Success(result.first()))
                } else {
                    emit(NetworkResponse.Empty)
                }
            } catch (e: Exception){
                emit(NetworkResponse.Error(e.message.toString()))
                Log.e(TAG, e.message.toString() )
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun sendMessage(name: String, message: String) : Flow<NetworkResponse<DocumentReference>> {
        return flow {
            try {
                val msg = hashMapOf(
                    "name" to name,
                    "message" to message
                )

                val result = addMessage(msg).await()
                emit(NetworkResponse.Success(result))
            } catch (e: Exception){
                emit(NetworkResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}