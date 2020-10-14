package com.example.tp1kotlin

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class Cocktail (
    @SerializedName("name") val name : String,
    @SerializedName("alcool") val alcool : Boolean,
    @SerializedName("ingredients") val ingredients : List<String>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: String(),
        parcel.readByte() != 0.toByte(),
        parcel.createStringArrayList() ?: emptyList()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeByte(if (alcool) 1 else 0)
        parcel.writeStringList(ingredients)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cocktail> {
        override fun createFromParcel(parcel: Parcel): Cocktail {
            return Cocktail(parcel)
        }

        override fun newArray(size: Int): Array<Cocktail?> {
            return arrayOfNulls(size)
        }
    }
}