package com.example.a043_actionbar

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a043_actionbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        activityMainBinding.apply {
            // Toolbar를 ActionBar로 설정한다.
            setSupportActionBar(materialToolbar)
            // ActionBar의 타이틀을 설정한다.
            supportActionBar?.title = "MainActivity"

            button.setOnClickListener {
                val secondIntent = Intent(this@MainActivity, SecondActivity::class.java)
                startActivity(secondIntent)
            }
        }
    }

    // ActionBar 구성시 메뉴를 구성하기 위해 호출하는 메서드
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // 메뉴를 구성한다.
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // ActionBar에 배치된 메뉴를 누를 때 호출되는 메서드
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // 사용자가 누른 메뉴의 객체가 매개변수로 들어온다.
        // 이 메뉴의 id를 통해 구분한다.
        when(item.itemId){
            R.id.item1 -> activityMainBinding.textView.text = "메뉴1을 눌렀습니다"
            R.id.item2 -> activityMainBinding.textView.text = "메뉴2를 눌렀습니다"
        }

        return true
    }
}