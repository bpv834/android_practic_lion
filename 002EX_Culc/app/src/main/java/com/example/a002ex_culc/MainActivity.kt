package com.example.a002ex_culc

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var num1:EditText
    lateinit var num2:EditText
    lateinit var btnPlus : Button
    lateinit var btnMinus : Button
    lateinit var btnMultiple : Button
    lateinit var btnDivision : Button
    lateinit var txtResult : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        num1 = findViewById(R.id.txtNum1)
        num2 = findViewById(R.id.txtNum2)
        btnPlus = findViewById(R.id.btnPlus)
        btnMinus = findViewById(R.id.btnMinus)
        btnMultiple = findViewById(R.id.btnMultiple)
        btnDivision = findViewById(R.id.btnDivision)
        txtResult = findViewById(R.id.txtResult)


        btnPlus.setOnClickListener(BtnPlusListener())
        btnMinus.setOnClickListener(BtnMinusListener())
        btnMultiple.setOnClickListener(BtnMultipleListener())
        btnDivision.setOnClickListener(BtnDivisionListener())

    }

    inner class BtnPlusListener : OnClickListener{
        override fun onClick(v: View?) {
            val a1 = num1.text.toString().toInt()
            val a2 = num2.text.toString().toInt()

            val a3 =a1 +a2
            txtResult.text = "$a1 + $a2 = $a3"
        }

    }

    inner class BtnMinusListener : OnClickListener{
        override fun onClick(v: View?) {
            val a1 = num1.text.toString().toInt()
            val a2 = num2.text.toString().toInt()

            val a3 =a1 - a2
            txtResult.text = "$a1 - $a2 = $a3"
        }

    }

    inner class BtnMultipleListener : OnClickListener{
        override fun onClick(v: View?) {
            val a1 = num1.text.toString().toInt()
            val a2 = num2.text.toString().toInt()

            val a3 =a1 *a2
            txtResult.text = "$a1 * $a2 = $a3"
        }

    }

    inner class BtnDivisionListener : OnClickListener{
        override fun onClick(v: View?) {
            val a1 = num1.text.toString().toInt()
            val a2 = num2.text.toString().toInt()

            val a3 =a1 /a2
            txtResult.text = "$a1 / $a2 = $a3"
        }

    }
}