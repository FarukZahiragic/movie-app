package com.example.cineste

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Movie (
    @PrimaryKey @SerializedName("id") var id: Long,
    @ColumnInfo(name = "title") @SerializedName("original_title")  var title: String,
    @ColumnInfo(name = "overview") @SerializedName("overview")  var overview: String,
    @ColumnInfo(name = "release_date") @SerializedName("release_date")   var releaseDate: String,
    @ColumnInfo(name = "homepage") @SerializedName("homepage")   var homepage: String?,
    @ColumnInfo(name = "poster_path") @SerializedName("poster_path") var posterPath: String?,
    @ColumnInfo(name = "backdrop_path") @SerializedName("backdrop_path")  var backdropPath: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()!!,
        parcel.readString()!!) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(overview)
        parcel.writeString(releaseDate)
        parcel.writeString(homepage)
        parcel.writeString(posterPath)
        parcel.writeString(backdropPath)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<Movie> {
        override fun createFromParcel(parcel: Parcel): Movie {
            return Movie(parcel)
        }
        override fun newArray(size: Int): Array<Movie?> {
            return arrayOfNulls(size)
        }
    }
}