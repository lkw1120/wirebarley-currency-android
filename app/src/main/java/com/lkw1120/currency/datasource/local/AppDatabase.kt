package com.lkw1120.currency.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.lkw1120.currency.common.COUNTRY_INFO_FILENAME
import com.lkw1120.currency.common.DATABASE_NAME
import com.lkw1120.currency.datasource.local.dao.CountryDao
import com.lkw1120.currency.datasource.local.entity.CountryEntity
import com.lkw1120.currency.worker.CurrencyDatabaseWorker
import com.lkw1120.currency.worker.CurrencyDatabaseWorker.Companion.KEY_FILENAME

@Database(
    version = 1,
    entities = [
        CountryEntity::class
    ])
abstract class AppDatabase : RoomDatabase() {

    abstract fun countryDao(): CountryDao

    companion object Database {

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(
                    object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            val request = OneTimeWorkRequestBuilder<CurrencyDatabaseWorker>()
                                .setInputData(workDataOf(KEY_FILENAME to COUNTRY_INFO_FILENAME))
                                .build()
                            WorkManager.getInstance(context).enqueue(request)
                        }
                    }
                ).build()
        }
    }
}