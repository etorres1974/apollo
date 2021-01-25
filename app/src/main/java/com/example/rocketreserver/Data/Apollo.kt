package com.example.rocketreserver.Data

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.example.rocketreserver.LaunchListQuery

class Apollo {
    val graphqlURL = "https://apollo-fullstack-tutorial.herokuapp.com"
    val client = ApolloClient.builder()
        .serverUrl(graphqlURL)
        .build()

    suspend fun queryLaunches() : List<LaunchListQuery.Launch>?{
        return try {
            client.query(LaunchListQuery()).await()?.data?.launches?.launches?.filterNotNull()
        }catch (e : Throwable){
            Log.d("LaunchList", "Failure", e)
            null
        }
    }
}