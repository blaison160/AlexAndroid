package com.example.alex.management

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.alex.R

object HealthFunctions {

    @Composable
    fun getHealthRepresentation(health: Int) = when(health){
        6 -> painterResource(R.drawable.heart6)
        5 -> painterResource(R.drawable.heart5)
        4 -> painterResource(R.drawable.heart4)
        3 -> painterResource(R.drawable.heart3)
        2 -> painterResource(R.drawable.heart2)
        1 -> painterResource(R.drawable.heart1)
        else -> painterResource(R.drawable.heart0)
    }
}