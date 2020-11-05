package com.amary.amengsubang.core.data.datasource.remote.network

import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseNetwork : KoinComponent{

    private val fireStore by inject<FirebaseFirestore>()

    fun listPlace() = fireStore.collection("travel")

    fun detailPlace(id: String) = fireStore.collection("travel").document(id).collection("detail")
}