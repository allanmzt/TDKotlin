package com.example.tp1kotlin

import android.util.Log
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataController(val callback: MainActivity) : retrofit2.Callback<ListeCocktails> {
     val BASE_URL:String = "https://perso.univ-lyon1.fr/lionel.buathier/cocktails/"
    override fun onResponse(call: Call<ListeCocktails>, response: Response<ListeCocktails>) {
        if(response.isSuccessful){
            response.body()?.let { callback.onRecupDone(it) }
        }
        else
            Log.d("INFO",response.errorBody().toString())
    }

    override fun onFailure(call: Call<ListeCocktails>, t: Throwable) {
        TODO("Not yet implemented")
    }

    fun recupererData(){
        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val API=retrofit.create(ApiInterface::class.java)
        val cal=API.getCocktails()
        cal.enqueue(this)

    }


}
