package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    var hidding = true
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnSignIn.setOnClickListener {
            if (checkOldSignIn()){
                goNextAct()
            }else{
                checkLogins()
            }
        }

        binding.btntxtviv.setOnClickListener {
            if (hidding == true){
                binding.hide.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.unhide))
                binding.edtPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                hidding = false
            }else{
                binding.b.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.hide.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.hide))
                hidding = true
            }
        }

    }

    private fun goNextAct(){
        val intent = Intent(this,ActivityMainBinding::class.java)
        startActivity(intent)
        finish()
    }
    private fun checkOldSignIn():Boolean {
        val sharedPreferences = getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        val data = sharedPreferences.getString("user","empty")
        if (data != "empty"){
            return true
        }else{
            return false
        }
    }

    private fun checkLogins() {
        val login = binding.edtLogin.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()
        if (login.isNotEmpty() && password.isNotEmpty()){
            val gson = Gson()
            val sharedPreferences = getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
            val foydalanuvchi = Foydalanuvchi(login,password)
            val jsonKlass = gson.toJson(foydalanuvchi)
            val editor = sharedPreferences.edit()
            editor.putString("user",jsonKlass)
            editor.apply()
            goNextAct()
            Toast.makeText(this, "Saqlandi!", Toast.LENGTH_SHORT).show()

        }else if(login.isEmpty() && password.isNotEmpty()){
            binding.edtLogin.error = "To`ldirilmagan!"
            binding.edtLogin.isFocusable = true
        }else if (login.isNotEmpty() && password.isEmpty()){
            binding.edtPassword.error = "To`ldirilmagan"
            binding.edtPassword.isFocusable = true
        }else{
            Toast.makeText(this, "Ma`lumotlarni to`ldiring!", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onResume() {
        super.onResume()
        if (checkOldSignIn()){
            goNextAct()
        }
    }
}