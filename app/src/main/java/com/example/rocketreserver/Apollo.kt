package com.example.rocketreserver

import com.apollographql.apollo.ApolloClient

class Apollo {
    val graphqlURL = "https://apollo-fullstack-tutorial.herokuapp.com"
    val client = ApolloClient.builder()
        .serverUrl(graphqlURL)
        .build()
}