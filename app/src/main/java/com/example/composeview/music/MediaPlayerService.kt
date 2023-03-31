package com.example.composeview.music

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2023/3/31 14:53
 * Desc  :
 */
class MediaPlayerService: Service() {

    var songController: SongController? = null
    private val binder: MediaPlayerBinder = MediaPlayerBinder()

    override fun onCreate() {
        super.onCreate()
        println("启动 service")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("重复执行")
        when (SongAction.values()[intent?.action?.toInt() ?: SongAction.NOTHING.ordinal]) {
            SongAction.PAUSE -> songController?.pause()
            SongAction.RESUME -> songController?.resume()
            SongAction.STOP -> songController?.stop()
            SongAction.NOTHING -> {}
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    inner class MediaPlayerBinder: Binder() {

        val service = this@MediaPlayerService
    }
}