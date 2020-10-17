package com.fristcode.task.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fristcode.task.model.PostModel

@Database(entities = [PostModel::class], version = 1 , exportSchema = false)
abstract class PostDatabase :RoomDatabase(){

    abstract fun postDao():PostDao

    companion object {
        @Volatile
        private var INSTANCE : PostDatabase?=null

        fun getDatabase(context: Context):PostDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostDatabase::class.java,
                    "posts_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}