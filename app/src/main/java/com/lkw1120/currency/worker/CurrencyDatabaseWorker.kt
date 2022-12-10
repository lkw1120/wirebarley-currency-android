package com.lkw1120.currency.worker


import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.lkw1120.currency.datasource.local.AppDatabase
import com.lkw1120.currency.datasource.local.entity.CountryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CurrencyDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val filename = inputData.getString(KEY_FILENAME)
            if (filename != null) {
                applicationContext.assets.open(filename).use { inputStream ->
                    JsonReader(inputStream.reader()).use { jsonReader ->
                        val countryType = object : TypeToken<List<CountryEntity>>() {}.type
                        val countryList: List<CountryEntity> = Gson().fromJson(jsonReader, countryType)
                        val database = AppDatabase.getInstance(applicationContext)
                        database.countryDao().insertAll(countryList)

                        Result.success()
                    }
                }
            } else {
                Log.e(TAG, "Error currency database - no valid filename")
                Result.failure()
            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error currency database", ex)
            Result.failure()
        }
    }

    companion object {
        private const val TAG = "CurrencyDatabaseWorker"
        const val KEY_FILENAME = "COUNTRY_INFO_FILENAME"
    }
}