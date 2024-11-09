package com.lion.a057_rawassets

import android.graphics.Typeface
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a057_rawassets.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader

// res 폴더
// 안드로이드 애플리케이션 개발시 사용하는 각종 리소스들을 담아 관리하는 폴더
// res 폴더에 있는 파일 데이터들은 R.xxx.xxx 형태의 리소스 아이디라는 것을 이용해
// 접근 가능

// raw 폴더
// res 폴더 내에서는 파일의 종류에 따라 폴더가 구분도ㅔ 있다. 안드로이드에서 구분하지 않은
// 기타 파일들을 넣어두는 곳이다.
// 비교적 스트림을 쉽게 얻을 수 있다
// 데이터 파일들, 사운드 파일들, 영상 파일들 등을 담아 써준다.

// assets 폴더
// 개발자가 자유롭게 파일을 넣어서 사용할 수 있는 폴더
// 하위 폴더를 만들어서 사용해도 된다.
// 비교적 스트림을 얻는게 쉽기 때문에 많이 사용하는 폴더 중에 하나이다


class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    // 사운드 재생 관련 객체
    var mediaPlayer: MediaPlayer? = null


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
            button.setOnClickListener {
                // 스트림을 가져온다.
                // resources : res 폴더에 있는 파일에 접근할 수 있는 객체
                // R.raw.data1 : res/raw/data1.txt
                val inputStream = resources.openRawResource(R.raw.data1)
                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)

                // 문자열들을 읽어온다.
                val strList = bufferedReader.readLines()

                bufferedReader.close()
                inputStreamReader.close()
                inputStream.close()

                // 출력한다.
                textView.text = ""

                strList.forEach {
                    textView.append("${it}\n")
                }
            }

            button2.setOnClickListener {
                mediaPlayer = MediaPlayer.create(this@MainActivity, R.raw.song)
                mediaPlayer?.start()
            }
            button3.setOnClickListener {
                if (mediaPlayer != null) {
                    mediaPlayer?.stop()
                    mediaPlayer = null
                }
            }
            button4.setOnClickListener {
                // videoView가 영상을 재생하지 않을 경우라면
                if (videoView.isPlaying == false) {
                    // 영상 파일에 접근할 있는 Uri 객체를 가져온다.
                    val uri = Uri.parse("android.resource://${packageName}/raw/video")
                    videoView.setVideoURI(uri)
                    // 재생한다
                    videoView.start()
                }
            }

            button5.setOnClickListener {
                if (videoView.isPlaying == true) {
                    videoView.stopPlayback()
                }
            }

            button6.setOnClickListener {
                // assets 폴더에 있는 파일과 연결된 스트림을 가져온다.
                val inputStream = assets.open("text/data1.txt")
                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)

                // 문자열들을 읽어온다.
                val strList = bufferedReader.readLines()

                bufferedReader.close()
                inputStreamReader.close()
                inputStream.close()

                // 출력한다.
                textView.text = ""

                strList.forEach{
                    textView.append("${it}\n")
                }
            }

            button7.setOnClickListener {
                // 폰트 객체를 생성한다.
                val typeFace = Typeface.createFromAsset(assets, "font/NanumBrush.ttf")
                // 폰트를 적용한다.
                textView.setTypeface(typeFace)
            }
        }
    }
}
