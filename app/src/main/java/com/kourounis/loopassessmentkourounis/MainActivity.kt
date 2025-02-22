package com.kourounis.loopassessmentkourounis

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var nameField: EditText
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var repeatPasswordField: EditText
    private lateinit var submitButton: Button

    private var name: String = ""
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nameField = findViewById(R.id.nameField)
        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        repeatPasswordField = findViewById(R.id.repeatPasswordField)
        submitButton = findViewById(R.id.submitButton)

        val passwordToggle: ImageView = findViewById(R.id.passwordToggle)
        val repeatPasswordToggle: ImageView = findViewById(R.id.repeatPasswordToggle)

        togglePasswordVisibility(passwordField, passwordToggle)
        togglePasswordVisibility(repeatPasswordField, repeatPasswordToggle)


        nameField.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                name = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        emailField.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                email = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        submitButton.setOnClickListener {
            validatePasswords()
            nameField.text.clear()
            emailField.text.clear()
            passwordField.text.clear()
            repeatPasswordField.text.clear()
        }
    }

    private fun togglePasswordVisibility(editText: EditText, toggleButton: ImageView) {
        toggleButton.setOnClickListener {
            val isPasswordVisible = editText.transformationMethod !is PasswordTransformationMethod

            if (isPasswordVisible) {
                editText.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                toggleButton.setImageResource(R.drawable.ic_eye)
            } else {
                editText.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                toggleButton.setImageResource(R.drawable.ic_eye_closed)
            }

            editText.setSelection(editText.text.length)
        }
    }

    private fun validatePasswords() {
        val password = passwordField.text.toString()
        val confirmPassword = repeatPasswordField.text.toString()
        val passwordErrorText: TextView = findViewById(R.id.passwordErrorText)

        if (password != confirmPassword) {
            passwordErrorText.visibility = View.VISIBLE
        } else {
            passwordErrorText.visibility = View.GONE
            Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
        }
    }
}