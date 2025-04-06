package com.example.alex.management

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class MoodViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    companion object {
        private const val CURRENT_MOOD_KEY = "current_mood"
    }

    private val _currentMood = savedStateHandle.getLiveData<MoodEnum>(CURRENT_MOOD_KEY, MoodEnum.NEUTRAL)
    val currentMood: LiveData<MoodEnum> = _currentMood

    fun updateCurrentMood(newMood: MoodEnum) {
        _currentMood.value = newMood
        savedStateHandle[CURRENT_MOOD_KEY] = newMood
    }

}