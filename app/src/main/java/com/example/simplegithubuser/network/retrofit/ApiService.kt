package com.example.simplegithubuser.network.retrofit
import com.example.simplegithubuser.network.response.DetailResponse
import com.example.simplegithubuser.network.response.GithubResponse
import com.example.simplegithubuser.network.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
//    @Headers("Authorization: token <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>")
     fun getSearchResult(
         @Query("q") query: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDataUser(
        @Path("username") username : String
    ): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username : String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username : String
    ): Call<List<ItemsItem>>
}