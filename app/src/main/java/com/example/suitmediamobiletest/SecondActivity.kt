package com.example.suitmediamobiletest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.suitmediamobiletest.FirstActivity.Companion.firstName
import com.example.suitmediamobiletest.FirstActivity.Companion.selectedName

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val welcomeText = findViewById<TextView>(R.id.secondWelcomeText)
        val showFirstName = findViewById<TextView>(R.id.secondFirstScreenName)
        val showSelectedName = findViewById<TextView>(R.id.secondSelectedUser)
        val chooseUserButton = findViewById<Button>(R.id.buttonChooseUser)
        val backButton = findViewById<ImageView>(R.id.secondBackButton)

        showFirstName.text = firstName
        showSelectedName.text = selectedName

        chooseUserButton.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }
        backButton.setOnClickListener {
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)
        }
    }
}

