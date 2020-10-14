package com.example.tp1kotlin

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ListeCocktails (
    @SerializedName("cocktails") val cocktails : List<Cocktail>
) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Cocktail)?: emptyList()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(cocktails)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListeCocktails> {
        override fun createFromParcel(parcel: Parcel): ListeCocktails {
            return ListeCocktails(parcel)
        }

        override fun newArray(size: Int): Array<ListeCocktails?> {
            return arrayOfNulls(size)
        }
    }
}