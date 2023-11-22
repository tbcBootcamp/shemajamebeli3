package com.example.shemajamebeli3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shemajamebeli3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        with(binding){
            btn3x3.setOnClickListener {
                switchTo3x3Activity()
            }
            btn4x4.setOnClickListener {
                switchTo4x4Activity()
            }
            btn5x5.setOnClickListener {
                switchTo5x5Activity()
            }
        }
    }


    private fun switchTo3x3Activity() {
        val intent = Intent(this, ThreeXThreeActivity::class.java)
        startActivity(intent)
    }

    private fun switchTo4x4Activity() {
        val intent = Intent(this, FourXFourActivity::class.java)
        startActivity(intent)
    }

    private fun switchTo5x5Activity() {
        val intent = Intent(this, FiveXFiveActivity::class.java)
        startActivity(intent)
    }
}