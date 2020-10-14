package com.example.tp1kotlin

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("liste_cocktails")
    fun getCocktails(): Call<ListeCocktails>

}