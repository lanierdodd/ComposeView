package com.example.composeview.music

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.io.File

/**
 * Author: 芒硝
 * Email : 1248389474@qq.com
 * Date  : 2023/3/31 14:13
 * Desc  :
 */
var innerPath = ""
val musicList = mutableStateListOf<Song>()

@Composable
fun MusicScreen() {
    LaunchedEffect(key1 = Unit) {
        val parentPath = File(innerPath)
        if (parentPath.isDirectory) {
            musicList.clear()
            parentPath.listFiles()?.let {
                it.forEach { f ->
                    val song = Song(f.absolutePath)
                    println(song.path)
                    musicList.add(song)
                }
            }
        }
    }
    val controller = LocalSongController.current
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Text(text = innerPath)
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(12.dp))
//            MediaPlayerI()
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(12.dp))
            LazyColumn {
                items(musicList.size) {
                    MusicItem(song = musicList[it]) { result ->
//                        println("click >> $result")
                        controller?.play(result)
                    }
                }
            }
        }
    }
}

@Composable
private fun MusicItem(
    song: Song,
    onClick: (song: Song) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick.invoke(song) }
    ) {
        Text(
            text = song.path,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(8.dp)
        )
    }
}

@Composable
private fun MediaPlayerI() {
    val context = LocalContext.current

    Column {
        Button(onClick = {
//            context.startService(Intent(context, MediaPlayerService::class.java))
        }) {
            Text(text = "start")
        }
        Button(onClick = {
            context.stopService(Intent(context, MediaPlayerService::class.java))
        }) {
            Text(text = "stop")
        }
    }
}
