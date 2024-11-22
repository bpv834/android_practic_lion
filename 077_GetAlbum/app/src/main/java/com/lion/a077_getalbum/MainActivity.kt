package com.lion.a077_getalbum

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a077_getalbum.databinding.ActivityMainBinding

// 1. 권한등록
// <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
// <uses-permission android:name="android.permission.ACCESS_MEDIA_LOCATION"/>

// 2. 권한 확인
//val permissionList = arrayOf(
//    android.Manifest.permission.READ_EXTERNAL_STORAGE,
//    android.Manifest.permission.ACCESS_MEDIA_LOCATION
//)

// requestPermissions(permissionList, 0)

// 3. 런처 선언
//     lateinit var albumLauncher:ActivityResultLauncher<Intent>

// 4. 런처 생성
// fun createAlbumLauncher() { }

// 5. 런처 생성 호출
// createAlbumLauncher()

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    lateinit var albumLauncher:ActivityResultLauncher<Intent>

    // 확인할 권한들
    val permissionList = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_MEDIA_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        requestPermissions(permissionList, 0)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // 런처 생성
        createAlbumLauncher()

        activityMainBinding.apply {
            button.setOnClickListener {
                val albumIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                // 이미지 타입을 설정합니다.
                albumIntent.setType("image/*")
                // 선택할 파일의 타입을 지정(안드로이드 OS가 사전 작업을 할 수 있도록)
                val mimeType = arrayOf("image/*")
                // Intent.EXTRA_MIME_TYPES: :이를 설정하면, 선택 가능한 파일 형식을 제한할 수 있습니다.
                // 사용자가 선택할 수 있는 파일 형식의 배열입니다.
                //예: arrayOf("image/jpeg", "image/png") → JPEG 또는 PNG 파일만 선택 가능.
                albumIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
                // 액티비티 실행
                albumLauncher.launch(albumIntent)
            }
        }
    }

    // 런처를 생성하는 메서드
    fun createAlbumLauncher(){
        albumLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            // 사진을 고르고 왔다면
            if(it.resultCode == RESULT_OK){
                // 가져온 데이터가 있다면
                if(it.data != null){
                    // 선택한 이미지에 접근할 수 있는 Uri 객체가 있다면
                    if(it.data?.data != null){
                        // android 10 버전 이상이라면
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                            // 이미지 객체를 생성할 수 있는 디코드를 생성한다.
                            val source = ImageDecoder.createSource(contentResolver, it.data?.data!!)
                            // Bitmap 객체를 생성한다.
                            val bitmap = ImageDecoder.decodeBitmap(source)
                            activityMainBinding.imageView.setImageBitmap(bitmap)
                        } else {
                            // ContentProvider를 통해 사진 데이터를 가져온다.
                            val cursor = contentResolver.query(it.data?.data!!, null, null, null, null)
                            if(cursor != null){
                                cursor.moveToNext()

                                // 이미지의 경로를 가져온다.
                                val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                                val source = cursor.getString(idx)

                                // 이미지를 생성한다.
                                val bitmap = BitmapFactory.decodeFile(source)
                                activityMainBinding.imageView.setImageBitmap(bitmap)
                            }
                        }
                    }
                }
            }
        }
    }

}