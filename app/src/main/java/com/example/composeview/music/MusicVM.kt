package com.example.composeview.music

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2023/3/31 16:25
 * Desc  :
 */
class MusicVM (private val environment: MusicEnvironment): ViewModel() {

    fun dispatch(action: MusicAction) {
        when (action) {
            MusicAction.Pause -> {
                viewModelScope.launch {
                    environment.pause()
                }
            }
            is MusicAction.Play -> {
                viewModelScope.launch {
                    environment.play(action.song)
                }
            }
            MusicAction.Resume -> {
                viewModelScope.launch {
                    environment.resume()
                }
            }
            MusicAction.Stop -> {
                viewModelScope.launch {
                    environment.stop()
                }
            }
        }
    }
}