package com.example.challenge2_foodapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.challenge2_foodapp.data.entity.CartEntity
import com.example.challenge2_foodapp.data.entity.FoodEntityConverter

@Database(entities = [CartEntity::class], version = 4, exportSchema = false)
@TypeConverters(FoodEntityConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun CartDao(): CartDao

    companion object {
        private const val DATABASE_NAME = "db_FoodEntity.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}