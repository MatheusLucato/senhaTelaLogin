package com.example.myintents

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText

class SecondActivity : AppCompatActivity() {

    private lateinit var newUsernameEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        newUsernameEditText = findViewById(R.id.editTextNewUsername)
        newPasswordEditText = findViewById(R.id.editTextNewPassword)
        saveButton = findViewById(R.id.buttonSave)

        // Define um listener para habilitar ou desabilitar o botão "Salvar"
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val newUsername = newUsernameEditText.text.toString().trim()
                val newPassword = newPasswordEditText.text.toString().trim()
                saveButton.isEnabled = newUsername.isNotEmpty() && newPassword.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        newUsernameEditText.addTextChangedListener(textWatcher)
        newPasswordEditText.addTextChangedListener(textWatcher)

        saveButton.setOnClickListener {
            val newUsername = newUsernameEditText.text.toString().trim()
            val newPassword = newPasswordEditText.text.toString().trim()

            // Retorna para a tela de login com o novo usuário e senha
            val resultIntent = Intent()
            resultIntent.putExtra("newUser", newUsername)
            resultIntent.putExtra("newPassword", newPassword)
            setResult(RESULT_OK, resultIntent)
            finish()
        }


    }
}

































































