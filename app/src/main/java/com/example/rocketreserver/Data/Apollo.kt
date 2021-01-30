package com.example.rocketreserver.Data

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import queries.LaunchListQuery


class Apollo {
    val graphqlURL = "https://apollo-fullstack-tutorial.herokuapp.com"
    val client = ApolloClient.builder()
        .serverUrl(graphqlURL)
        .build()

    suspend fun queryLaunches(cursor : String?) : LaunchListQuery.Launches?{
        return try {
            client.query(LaunchListQuery(cursor = Input.fromNullable(cursor))).await()?.data?.launches
        }catch (e : Throwable){
            Log.d("LaunchList", "Failure", e)
            null
        }
    }
}