package kmp.player.ui.player

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kmp.player.R
import kmp.player.ui.player.viewmodel.PlayerViewModel

@Composable
actual fun PlayerContainer(url: String, viewModel: PlayerViewModel){
    val context = LocalContext.current

    val isPlaying: Boolean by viewModel.isPlaying.collectAsState()
    val isVisible: Boolean by viewModel.isVisible.collectAsState()

    val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()
    exoPlayer.setMediaItem(MediaItem.fromUri(url))
    exoPlayer.prepare()

    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = false
                setOnClickListener {
                    if (viewModel.isVisible.value){
                        viewModel.updateIsVisible(false)
                    }else{
                        viewModel.updateIsVisible(true)
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )

    PlayerControls(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        isPlaying = { isPlaying },
        isVisible = { isVisible },
        onPauseToggle = {
            if (isPlaying) {
                exoPlayer.pause()
            } else {
                exoPlayer.play()
            }
            viewModel.updateIsPlaying(isPlaying.not())
            Thread.sleep(500)
            viewModel.updateIsVisible(false)
        })
}

@Composable
fun PlayerControls(
    modifier: Modifier = Modifier,
    isPlaying: () -> Boolean,
    isVisible: () -> Boolean,
    onPauseToggle: () -> Unit
) {
    val isVideoPlaying = remember(isPlaying()) { isPlaying() }
    val visible = remember(isVisible()) { isVisible() }

    AnimatedVisibility(modifier = modifier, visible = visible, enter = fadeIn(), exit = fadeOut()) {
        Box(modifier = modifier.background(Color.Black.copy(alpha = 0.2f))) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                IconButton(modifier = Modifier.size(40.dp), onClick = onPauseToggle) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        colorFilter = ColorFilter.tint(colorResource(id = R.color.teal_200)),
                        contentScale = ContentScale.Crop,
                        painter =
                        if (isVideoPlaying) {
                            painterResource(id = R.drawable.baseline_pause_40)
                        } else {
                            painterResource(id = R.drawable.baseline_play_arrow_40)
                        },
                        contentDescription = "Play/Pause"
                    )
                }
            }
        }
    }
}