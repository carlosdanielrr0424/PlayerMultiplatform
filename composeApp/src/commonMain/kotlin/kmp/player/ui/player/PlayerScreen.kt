package kmp.player.ui.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.hsl_player.dataClass.HslModel
import kmp.player.ui.player.viewmodel.PlayerViewModel

class PlayerScreen(val hlsModel: HslModel) : Screen {
    @Composable
    override fun Content() {
        val viewModel: PlayerViewModel = viewModel { PlayerViewModel() }

        ViewContainer(viewModel, hlsModel)
    }
}

@Composable
fun ViewContainer(viewModel: PlayerViewModel, hlsModel: HslModel){

    viewModel.updateURL(hlsModel.url)

    Scaffold(topBar = { Toolbar(hlsModel.name) }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PlayerContainer(hlsModel.url, viewModel)
        }
    }
}

@Composable
fun Toolbar(title: String) {
    val navigator = LocalNavigator.currentOrThrow

    TopAppBar(
        title = {
            Text(title)
        },
        navigationIcon = {
            IconButton(onClick = { navigator.pop() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
    )
}

