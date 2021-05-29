package d.com.restapi

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import kotlin.jvm.internal.Intrinsics


private val Base_Url = "https://rickandmortyapi.com/api/"
interface ApiService {
    @GET("character")
    fun getResults(@Query("page")results: String): Call<List<Results>>
}


/*val retrofit = Retrofit.Builder()
        .baseUrl(MainActivity.URl_Rick_API).addConverterFactory(MoshiConverterFactory.create()).build()


object Api {
   val apiService:ApiService =
        retrofit.create(ApiService::class.java)
}*/