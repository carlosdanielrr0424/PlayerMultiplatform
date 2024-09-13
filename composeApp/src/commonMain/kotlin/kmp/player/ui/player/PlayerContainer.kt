package kmp.player.ui.player

import androidx.compose.runtime.Composable
import kmp.player.ui.player.viewmodel.PlayerViewModel

@Composable
expect fun PlayerContainer(url: String, viewModel: PlayerViewModel)