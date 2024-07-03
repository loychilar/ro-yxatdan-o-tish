package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import com.example.myapplication.MySharePrefaranse.edit

object MySharePrefaranse {
    private const val NAME="subjectProgram"
    private const val MODE= Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    fun init(context: Context){
        preferences = context.getSharedPreferences(NAME, MODE)

    }

  private inline fun SharedPreferences.edit(operation:(SharedPreferences.Editor) -> Unit){
      val editor = edit()
      operation(editor)
      editor.apply()
  }

    var darkMode:Boolean?
        get() = preferences.getBoolean("mode",false)
        set(value) = preferences.edit {
          if (value!=null){
              it.putBoolean("mode",value)
          }
        }
}