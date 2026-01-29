package uz.codingtech.messengerdashboard.presentation.common

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun VideoPlayer(url: String, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val httpFactory = remember {
        DefaultHttpDataSource.Factory()
            .setAllowCrossProtocolRedirects(true) // 302/301 redirectlar uchun
            .setUserAgent("Mozilla/5.0 (Android)") // ba'zi serverlar User-Agent talab qiladi
    }

    val dataSourceFactory = remember {
        DefaultDataSource.Factory(context, httpFactory)
    }

    val mediaSourceFactory = remember {
        DefaultMediaSourceFactory(dataSourceFactory)
    }

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(mediaSourceFactory)
            .build()
            .apply { playWhenReady = false }
    }

    // URL o'zgarsa qayta tayyorlaydi
    LaunchedEffect(url) {
        val uri = Uri.parse(url)
        val mediaItem = MediaItem.Builder()
            .setUri(uri)
            .build()

        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
    }

    // Diagnostika (LOGCAT juda muhim!)
    DisposableEffect(Unit) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                Log.d("VideoPlayer", "state=$state (1=IDLE 2=BUFFERING 3=READY 4=ENDED)")
            }

            override fun onPlayerError(error: PlaybackException) {
                Log.e("VideoPlayer", "ERROR: ${error.errorCodeName}", error)
            }
        }
        exoPlayer.addListener(listener)

        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = true
            }
        }
    )
}

