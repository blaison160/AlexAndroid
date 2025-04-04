package com.example.alex.management

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

    fun getHappierMood(mood: MoodEnum) = when(mood){
        MoodEnum.NEUTRAL -> MoodEnum.NEUTRAL_HAPPY
        MoodEnum.NEUTRAL_HAPPY -> MoodEnum.HAPPY
        MoodEnum.BLINK -> MoodEnum.VERY_HAPPY
        MoodEnum.HAPPY -> MoodEnum.VERY_HAPPY
        MoodEnum.VERY_HAPPY -> MoodEnum.UWU_HEART
        MoodEnum.UWU -> MoodEnum.UWU_HEART
        MoodEnum.UWU_HEART -> MoodEnum.UWU_HEART
        MoodEnum.WORRIED -> MoodEnum.NEUTRAL
        MoodEnum.SAD -> MoodEnum.WORRIED
        MoodEnum.SOBBING -> MoodEnum.SAD
        MoodEnum.CRYING -> MoodEnum.SOBBING
        MoodEnum.DEAD -> MoodEnum.DEAD
        MoodEnum.SHOCKED -> MoodEnum.NEUTRAL
        MoodEnum.ASTONISHED -> MoodEnum.SHOCKED
        MoodEnum.ANGRY -> MoodEnum.SAD
        MoodEnum.GRUMBOT -> MoodEnum.GRUMBOT
    }

    fun getMoodFromHealth(health: Int) = when(health){
            6 -> MoodEnum.HAPPY
            5 -> MoodEnum.NEUTRAL_HAPPY
            4 -> MoodEnum.NEUTRAL
            3 -> MoodEnum.WORRIED
            2 -> MoodEnum.SAD
            1 -> MoodEnum.CRYING
            else -> MoodEnum.DEAD
    }
}
