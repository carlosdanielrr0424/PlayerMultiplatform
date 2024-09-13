package kmp.player.ui.player.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerViewModel: ViewModel(){
    private val _urlState = MutableStateFlow("")
    val urlState: StateFlow<String> = _urlState.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _isVisible = MutableStateFlow(true)
    val isVisible: StateFlow<Boolean> = _isVisible.asStateFlow()

    fun updateURL(newUrl: String){
        _urlState.value = newUrl
    }

    fun updateIsPlaying(newValue: Boolean){
        _isPlaying.value = newValue
    }

    fun updateIsVisible(newValue: Boolean){
        _isVisible.value = newValue
    }
}