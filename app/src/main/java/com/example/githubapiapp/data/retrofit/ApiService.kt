package com.example.githubapiapp.data.retrofit

import com.example.githubapiapp.data.response.GithubResponse
import com.example.githubapiapp.data.response.GithubResponseDetail
import com.example.githubapiapp.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun getUser(@Query("q") query: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String,
    ): Call<GithubResponseDetail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String,
    ): Call<List<ItemsItem>>
}
