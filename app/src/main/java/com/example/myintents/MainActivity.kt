package com.example.myintents

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    private val usersList = mutableListOf<Pair<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.editTextUsername)
        passwordEditText = findViewById(R.id.editTextPassword)
        loginButton = findViewById(R.id.buttonLogin)

        // Adicionando usuários iniciais à lista
        usersList.add(Pair("user", "1234"))

        loginButton.isEnabled = false

        // Adicionando listener para verificar se ambos os campos estão preenchidos
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val username = usernameEditText.text.toString().trim()
                val password = passwordEditText.text.toString().trim()
                loginButton.isEnabled = username.isNotEmpty() && password.isNotEmpty()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        usernameEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (isValidUser(username, password)) {
                openSecondActivity()
            } else {
                showErrorDialog()
            }
        }
    }

    private fun isValidUser(username: String, password: String): Boolean {
        // Verifica se o par de chave-valor (usuário, senha) existe na lista de usuários
        return usersList.any { it.first == username && it.second == password }
    }

    private fun openSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY)
    }

    private fun showErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Erro de Login")
        builder.setMessage("Nome de usuário ou senha incorretos.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SECOND_ACTIVITY && resultCode == RESULT_OK) {
            val newUser = data?.getStringExtra("newUser")
            val newPassword = data?.getStringExtra("newPassword")
            newUser?.let { username ->
                newPassword?.let { password ->
                    // Adiciona o novo usuário à lista
                    usersList.add(Pair(username, password))
                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_SECOND_ACTIVITY = 100
    }
}
