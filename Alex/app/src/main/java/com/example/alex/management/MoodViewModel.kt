package com.example.alex.management

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MoodViewModel : ViewModel() {
    var currentMood = mutableStateOf(MoodEnum.NEUTRAL)
        private set
    fun updateCurrentMood(newMood: MoodEnum){
        currentMood.value = newMood
    }
    fun getCurrentMood(): MoodEnum {
        return currentMood.value
    }
}