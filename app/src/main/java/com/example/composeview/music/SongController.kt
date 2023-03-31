package com.example.composeview.music

import androidx.compose.runtime.compositionLocalOf

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2023/3/31 15:22
 * Desc  :
 */
interface SongController {

    fun play(song: Song)
    fun pause()
    fun resume()
    fun stop()
}

val LocalSongController = compositionLocalOf<SongController?> { null }