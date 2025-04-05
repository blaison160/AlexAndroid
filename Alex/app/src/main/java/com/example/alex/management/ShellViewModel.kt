package com.example.alex.management

import androidx.lifecycle.ViewModel

class ShellViewModel : ViewModel() {
    var shellLines = mutableListOf<String>()
        private set
    fun updateShellLines(lines: List<String>){
        shellLines.clear()
        shellLines.addAll(lines)
        while(shellLines.size <6){
            shellLines.add("")
        }
    }
}