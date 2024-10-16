package com.example.data.worker


/*
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.data_api.repository.NotificationRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val notificationRepository: NotificationRepository
) : Worker(context, params) {
    override fun doWork(): Result {
        val id = inputData.getString(ID)
        val title = inputData.getString(TITLE)
        val time = inputData.getString(TIME)

        if (id != null && title != null && time != null) {
            notificationRepository.showNotification(id = id.hashCode(), title = title, time = time)
            return Result.success()
        }
        return Result.failure()
    }

    companion object {
        private const val ID = "id"
        private const val TITLE = "title"
        private const val TIME = "time"
    }
}*/
