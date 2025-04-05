package com.example.alex.management

import android.content.Context
import com.example.alex.R
import kotlin.random.Random

object ShellFunctions {

    fun checkUserInput(input: String, mood: MoodEnum, context: Context): Pair<List<String>, MoodEnum>{
        return when(input) {
            "help" -> displayCommands(mood, context)
            "update" -> updateAlex()
            "debug" -> debugAlex()
            "upgrade" -> upgradeAlex()
            "hello" -> randomReply(mood)
            "mumbo" -> mumboJumbo(context)
            "jumbo" -> mumboJumbo(context)
            "mumbojumbo" -> mumboJumbo(context)
            "grumbot" -> mumboJumbo(context)
            "uwu" -> uwu()
            else -> checkForGoodWord(input,context)
        }
    }


    private fun displayCommands(mood: MoodEnum, context: Context): Pair<List<String>, MoodEnum> {
        val listOfCommands = listOf("debug","hello","update","upgrade", context.resources.getString(R.string.helpHint))
        return Pair(listOfCommands, mood)
    }

    private fun updateAlex(): Pair<List<String>, MoodEnum> {
        TODO()
    }

    private fun debugAlex(): Pair<List<String>, MoodEnum> {
        TODO()
    }

    private fun upgradeAlex(): Pair<List<String>, MoodEnum> {
        TODO("Not yet implemented")
    }

       private fun randomReply(mood: MoodEnum): Pair<List<String>, MoodEnum> {
        TODO("Not yet implemented")
    }

    private fun mumboJumbo(context: Context): Pair<List<String>, MoodEnum> {
        return Pair(listOf("Subscribe to Mumbo Jumbo !"), MoodEnum.GRUMBOT)
    }

    private fun uwu(): Pair<List<String>, MoodEnum> {
        return Pair(listOf("UwU"), MoodEnum.UWU)
    }

    private fun checkForGoodWord(input: String, context: Context): Pair<List<String>, MoodEnum>{
        return when(input){
            "iloveyou" -> youToo(context)
            "iloveu" -> youToo(context)
            "ilovu" -> youToo(context)
            "jevousaime" -> youToo(context)
            "jetaime" -> youToo(context)
            "jtm" -> youToo(context)
            "loveya" -> youToo(context)
            "youarenice" -> thanks(context)
            "youarecool" -> thanks(context)
            "yournice" -> thanks(context)
            "yourcool" -> thanks(context)
            "tuescool" -> thanks(context)
            "tescool" -> thanks(context)
            "tuesincroyable" -> thanks(context)
            "iloveyouralgorythm" -> thanks(context)
            "jadoretonalgorithme" -> thanks(context)
            "howdoirunformayor" -> mayor(context)
            "commentsepresenteralelectiondumaire" -> mayor(context)
            else -> checkForBadWords(input, context)
        }
    }

    private fun checkForBadWords(input: String, context: Context): Pair<List<String>, MoodEnum> {
        val listToReturn = listOf<String>()
        return when(input){
            "bitch" -> angryFace(context)
            "sonofabitch" -> angryFace(context)
            "idiot" -> angryFace(context)
            "connard" -> angryFace(context)
            "putain" -> angryFace(context)
            "salemerde" -> angryFace(context)
            "encule" -> angryFace(context)
            "asshole" -> angryFace(context)
            "bastard" -> angryFace(context)
            else -> doNotUnderstand()
        }
    }

    private fun youToo(context: Context): Pair<List<String>, MoodEnum> {
        return Pair(listOf(context.resources.getString(R.string.youtoo)), MoodEnum.UWU_HEART)

    }

    private fun thanks(context: Context): Pair<List<String>, MoodEnum> {
        return Pair(listOf(context.resources.getString(R.string.thanks)), MoodEnum.VERY_HAPPY)
    }

    private fun mayor(context: Context): Pair<List<String>, MoodEnum> {
        return  Pair(listOf(context.resources.getString(R.string.mayor)), MoodEnum.GRUMBOT)
    }

    private fun angryFace(context: Context): Pair<List<String>, MoodEnum> {
        val randomReaction = Random.nextInt(2)
        var reaction = MoodEnum.ANGRY
        when(randomReaction){
            1 -> reaction = MoodEnum.SHOCKED
            2 -> reaction = MoodEnum.ASTONISHED
        }
        return Pair(listOf(context.resources.getString(R.string.bePolite)), reaction)
    }

    private fun doNotUnderstand(): Pair<List<String>, MoodEnum>{
        return Pair(listOf("Hmmm..."), MoodEnum.WORRIED)
    }
}