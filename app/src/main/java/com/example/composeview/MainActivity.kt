package com.example.composeview

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Environment
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composeview.music.*
import com.example.composeview.touch.TestTouch4
import com.example.composeview.ui.theme.ComposeViewTheme

class MainActivity : ComponentActivity(), ServiceConnection {

//    private val vm by lazy {
//        ViewModelProvider(this)[MusicVM::class.java]
//    }
    private lateinit var vm: MusicVM
    private var mediaPlayerService: MediaPlayerService? = null
    private val songController = object : SongController {
        override fun play(song: Song) {
            println("play >>>> ${song.path}")
            vm.dispatch(
                MusicAction.Play(song)
            )
        }

        override fun pause() {
            vm.dispatch(
                MusicAction.Pause
            )
        }

        override fun resume() {
            vm.dispatch(
                MusicAction.Resume
            )
        }

        override fun stop() {
            vm.dispatch(
                MusicAction.Stop
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MusicVM(
                    MusicEnvironment(this@MainActivity)
                ) as T
            }
        }

        vm = ViewModelProvider(this, factory = factory)[MusicVM::class.java]

        val serviceIntent = Intent(this, MediaPlayerService::class.java)
        bindService(serviceIntent, this, BIND_AUTO_CREATE)

        setContent {
            innerPath = getExternalFilesDir(Environment.DIRECTORY_MUSIC)?.path?:""
            ComposeViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CompositionLocalProvider(
                        LocalSongController provides songController
                    ) {
                        MusicScreen()
                    }
                }
            }
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MediaPlayerService.MediaPlayerBinder
        mediaPlayerService = binder.service
        mediaPlayerService!!.songController = songController
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        mediaPlayerService = null
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeViewTheme {
        Greeting("Android")
    }
}