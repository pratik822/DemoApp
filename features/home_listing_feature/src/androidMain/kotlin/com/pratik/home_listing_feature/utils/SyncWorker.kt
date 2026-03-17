package com.pratik.home_listing_feature.utils

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pratik.home_listing_feature.domain.usecase.GetAllPostUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params), KoinComponent {

    private val getAllPostUseCase: GetAllPostUseCase by inject()

    override suspend fun doWork(): Result {
        return try {
            Log.d("SyncWorker", "Starting Sync...")

            val category = "general"
             getAllPostUseCase(category)
            Log.d("SyncWorker", "Sync news from API Success (Android)")
            Result.success()
        } catch (e: Exception) {
            Log.e("SyncWorker", "Sync Failed: ${e.message}")
            Result.retry()
        }
    }
}
