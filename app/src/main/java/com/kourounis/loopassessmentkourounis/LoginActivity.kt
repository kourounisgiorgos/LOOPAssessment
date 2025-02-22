package com.kourounis.loopassessmentkourounis

import android.content.Intent
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
import com.kourounis.loopassessmentkourounis.compose.ComposeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var nameField: EditText
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var repeatPasswordField: EditText
    private lateinit var submitButton: Button
    private lateinit var passwordToggle: ImageView
    private lateinit var repeatPasswordToggle: ImageView

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

        passwordToggle = findViewById(R.id.passwordToggle)
        repeatPasswordToggle = findViewById(R.id.repeatPasswordToggle)

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

            val isValidated = validateForm()

            nameField.text.clear()
            emailField.text.clear()
            passwordField.text.clear()
            repeatPasswordField.text.clear()

            if(true){
                val intent = Intent(this, ComposeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun togglePasswordVisibility(editText: EditText, toggleButton: ImageView) {
        editText.transformationMethod = PasswordTransformationMethod.getInstance()
        toggleButton.setImageResource(R.drawable.ic_eye)

        toggleButton.setOnClickListener {
            val isPasswordHidden = editText.transformationMethod is PasswordTransformationMethod

            if (isPasswordHidden) {
                editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                toggleButton.setImageResource(R.drawable.ic_eye_closed)
            } else {
                editText.transformationMethod = PasswordTransformationMethod.getInstance()
                toggleButton.setImageResource(R.drawable.ic_eye)
            }

            editText.setSelection(editText.text.length)
        }
    }

    private fun validateForm(): Boolean {
        val email = emailField.text.toString()
        val password = passwordField.text.toString()
        val confirmPassword = repeatPasswordField.text.toString()
        val passwordErrorText: TextView = findViewById(R.id.passwordErrorText)

        if(email.isBlank() || password.isBlank() || confirmPassword.isBlank()){
            Toast.makeText(this, "Fields with * are required", Toast.LENGTH_LONG).show()
            return false
        }else if (password != confirmPassword) {
            passwordErrorText.visibility = View.VISIBLE
            return false
        } else {
            passwordErrorText.visibility = View.GONE
            Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_LONG).show()
            return true
        }
    }
}