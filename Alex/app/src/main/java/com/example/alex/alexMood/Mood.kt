package com.example.alex.alexMood

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.alex.R

object Mood {
    @Composable
    fun getMoodRepresentation(mood: MoodEnum) = when(mood){
            MoodEnum.NEUTRAL -> painterResource(R.drawable.neutral)
            MoodEnum.NEUTRAL_HAPPY -> painterResource(R.drawable.neutralhappy)
            MoodEnum.BLINK -> painterResource(R.drawable.blink)
            MoodEnum.HAPPY -> painterResource(R.drawable.happy)
            MoodEnum.VERY_HAPPY -> painterResource(R.drawable.veryhappy)
            MoodEnum.UWU -> painterResource(R.drawable.uwu)
            MoodEnum.UWU_HEART -> painterResource(R.drawable.uwuheart)
            MoodEnum.WORRIED -> painterResource(R.drawable.worried)
            MoodEnum.SAD -> painterResource(R.drawable.sad)
            MoodEnum.SOBBING -> painterResource(R.drawable.sobbing)
            MoodEnum.CRYING -> painterResource(R.drawable.crying)
            MoodEnum.DEAD -> painterResource(R.drawable.dead)
            MoodEnum.SHOCKED -> painterResource(R.drawable.shocked)
            MoodEnum.ASTONISHED -> painterResource(R.drawable.astonished)
            MoodEnum.ANGRY -> painterResource(R.drawable.angry)
            MoodEnum.GRUMBOT -> painterResource(R.drawable.grumbot)
    }

    @Composable
    fun getHappierMood(mood: MoodEnum) = when(mood){
        MoodEnum.NEUTRAL -> painterResource(R.drawable.neutralhappy)
        MoodEnum.NEUTRAL_HAPPY -> painterResource(R.drawable.happy)
        MoodEnum.BLINK -> painterResource(R.drawable.veryhappy)
        MoodEnum.HAPPY -> painterResource(R.drawable.veryhappy)
        MoodEnum.VERY_HAPPY -> painterResource(R.drawable.uwuheart)
        MoodEnum.UWU -> painterResource(R.drawable.uwuheart)
        MoodEnum.UWU_HEART -> painterResource(R.drawable.uwuheart)
        MoodEnum.WORRIED -> painterResource(R.drawable.neutral)
        MoodEnum.SAD -> painterResource(R.drawable.worried)
        MoodEnum.SOBBING -> painterResource(R.drawable.sad)
        MoodEnum.CRYING -> painterResource(R.drawable.sobbing)
        MoodEnum.DEAD -> painterResource(R.drawable.dead)
        MoodEnum.SHOCKED -> painterResource(R.drawable.neutral)
        MoodEnum.ASTONISHED -> painterResource(R.drawable.shocked)
        MoodEnum.ANGRY -> painterResource(R.drawable.angry)
        MoodEnum.GRUMBOT -> painterResource(R.drawable.grumbot)//make happygrumbot
    }
}