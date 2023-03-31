package com.example.composeview.music

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2023/3/31 16:26
 * Desc  :
 */
sealed interface MusicAction {

    data class Play(val song: Song) : MusicAction
    object Pause: MusicAction
    object Resume: MusicAction
    object Stop: MusicAction
}