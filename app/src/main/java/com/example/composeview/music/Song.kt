package com.example.composeview.music

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2023/3/31 16:08
 * Desc  :
 */
data class Song (
    val path: String = ""
)

enum class SongAction {
    PAUSE,
    RESUME,
    STOP,

    NOTHING
}