package com.example.suitmediamobiletest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso

class FirstActivity : AppCompatActivity() {
    private lateinit var nameInput: EditText
    private lateinit var sentenceInput: EditText
    companion object {
        var firstName: String? = null
        var selectedName: String? = null
        var avatar: String? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        nameInput = findViewById(R.id.firstNameText)
        sentenceInput = findViewById(R.id.firstSentenceText)
        val avatarImageView: ImageView = findViewById(R.id.topImageView)
        val checkButton: Button = findViewById(R.id.firstCheckButton)
        val nextButton: Button = findViewById(R.id.firstNextButton)
        avatar?.let { avatarUrl ->
            Picasso.get().load(avatarUrl).into(avatarImageView)
        }
        if (firstName != null && firstName != "Empty") {
            nameInput.setText(firstName)
        }
        checkButton.setOnClickListener {
            checkPalindrome()
        }

        nextButton.setOnClickListener {
            val name = nameInput.text.toString().takeIf { it.isNotBlank() } ?: "Empty"
            firstName = name
            val intent = Intent(this@FirstActivity, SecondActivity::class.java)
            startActivity(intent)
        }

    }

    private fun checkPalindrome() {
        val sentence = sentenceInput.text.toString()
        val isPalindrome = isPalindrome(sentence)
        val message = if (isPalindrome) {
            "isPalindrome"
        } else {
            "not palindrome"
        }

        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
            .setPositiveButton("OK") { _, _ -> }
            .create().show()
    }

    private fun isPalindrome(s: String): Boolean {
        val cleanString = s.replace("\\s+".toRegex(), "")
        val length = cleanString.length
        for (i in 0 until length / 2) {
            if (cleanString[i] != cleanString[length - i - 1]) {
                return false
            }
        }
        return true
    }
}

