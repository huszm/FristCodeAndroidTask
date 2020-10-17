package com.fristcode.task.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "posts_table")
class PostModel:Parcelable{
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    var name: String? = null
    var image: String? = null
}
