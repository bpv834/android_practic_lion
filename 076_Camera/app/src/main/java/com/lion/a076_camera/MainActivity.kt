package com.lion.a076_camera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a076_camera.databinding.ActivityMainBinding
import java.io.File

// 기본 사진 촬영을 사용하면 썸네일 이미지를 가져온다.
// 원본 사진 데이터를 사용하려면 촬영된 사진을 파일로 저장하게 하고
// 저장된 사진 파일 데이터를 읽어와야 한다.



class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 기본 사진찍기용 런처
    lateinit var basicCameraLauncher:ActivityResultLauncher<Intent>
    // 원본 사진 찍기용 런처
    lateinit var originalCameraLauncher: ActivityResultLauncher<Intent>

    // 촬영된 사진이 위치할 경로
    lateinit var filePath:String
    // 저장된 파일에 접근하기 위한 Uri
    lateinit var contentUri:Uri

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

        // 외부 저장소 경로를 가져온다.
        filePath = getExternalFilesDir(null).toString()

        // 일반 사진 찍기용 런처 생성 메서드 호출
        createBasicCameraLauncher()
        // 원본 사진 찍기용 런처 생성 메서드 호출
        createOriginalCameraLauncher()

        activityMainBinding.apply {
            button.setOnClickListener {
                val basicCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                basicCameraLauncher.launch(basicCameraIntent)
            }

            button2.setOnClickListener {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                // 촬영한 사진이 저장될 파일 이름
                val fileName = "/temp_${System.currentTimeMillis()}.jpg"
                // 경로 + 파일이름
                val picPath = "${filePath}${fileName}"
                val file = File(picPath)

                // 사진이 저장될 위치를 관리하는 Uri 객체를 생성ㅎ
                contentUri = FileProvider.getUriForFile(this@MainActivity, "com.lion.getpicture.file_provider", file)

                // Activity를 실행한다.

                //MediaStore.EXTRA_OUTPUT: 촬영된 사진이 저장될 위치를 지정하는 데 사용됩니다.
                //contentUri: FileProvider를 통해 생성된 URI로, 안전하고 명확한 저장 경로를 제공합니다.
                //이 코드를 사용하면 앱이 사진을 직접 관리할 수 있어, 더 안정적이고 효율적인 파일 처리가 가능합니다.
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
                originalCameraLauncher.launch(cameraIntent)
            }
        }
    }

    // 일반 사진 찍기용 런처 생성
    fun createBasicCameraLauncher(){
        basicCameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            // 사진을 선택하고 돌아왔다면
            if(it.resultCode == RESULT_OK){
                if(it.data != null) {
                    // 활용한 이미지 데이트를 가져온다.
                    val bitmap = it.data?.getParcelableExtra<Bitmap>("data")
                    activityMainBinding.imageView.setImageBitmap(bitmap)
                }
            }
        }
    }

    // 원본 사진찍기용 런처 생성
    fun createOriginalCameraLauncher(){
        originalCameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            // 사진을 선택하고 돌아왔다면
            if(it.resultCode == RESULT_OK){
                // uri를 통해 저장된 사진 데이터를 가져온다.
                val bitmap = BitmapFactory.decodeFile(contentUri.path)
                // 이미지의 회전 각도값을 가져온다.
                val degree = getDegree(contentUri)
                // 회전 값을 이용해 이미지를 회전시킨다.
                val rotateBitmap = rotateBitmap(bitmap, degree)
                // 크기를 조정한 이미지를 가져온다.
                val resizeBitmap = resizeBitmap(1024, rotateBitmap)

                // 이미지 뷰에 설정해준다.
                activityMainBinding.imageView.setImageBitmap(resizeBitmap)

                // 사진 파일은 삭제한다.
                val file = File(contentUri.path!!)
                file.delete()
            }
        }
    }

    // 이미지를 회전시키는 메서드
    fun rotateBitmap(bitmap: Bitmap, degree:Int):Bitmap{
        // 회전 이미지를 구하기 위한 변환 행렬
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        // 회전 행렬을 적용하여 회전된 이미지를 생성한다.
        val resultBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)
        return resultBitmap
    }

    // 회전 각도값을 구하는 메서드
    fun getDegree(uri:Uri):Int{

        // 이미지의 태그 정보에 접근할 수 있는 객체를 생성한다.
        // andorid 10 버전 이상이라면
        val exifInterface = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            // 이미지 데이터를 가져올 수 있는 Content Provider의 Uri를 추출한다.
            val photoUri = MediaStore.setRequireOriginal(uri)
            // 컨텐츠 프로바이더를 통해 파일에 접근할 수 있는 스트림을 추출한다.
            val inputStream = contentResolver.openInputStream(photoUri)
            // ExifInterface 객체를 생성한다.
            ExifInterface(inputStream!!)
        } else {
            ExifInterface(uri.path!!)
        }

        // ExifInterface 정보 중 회전 각도 값을 가져온다
        val ori = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)

        // 회전 각도값을 담는다.
        val degree = when(ori){
            ExifInterface.ORIENTATION_ROTATE_90 -> 90
            ExifInterface.ORIENTATION_ROTATE_180 -> 180
            ExifInterface.ORIENTATION_ROTATE_270 -> 270
            else -> 0
        }

        return degree
    }

    // 이미지의 사이즈를 줄이는 메서드
    fun resizeBitmap(targetWidth:Int, bitmap:Bitmap):Bitmap{
        // 이미지의 축소/확대 비율을 구한다.
        val ratio = targetWidth.toDouble() / bitmap.width.toDouble()
        // 세로 길이를 구한다.
        val targetHeight = (bitmap.height.toDouble() * ratio).toInt()
        // 크기를 조절한 Bitmap 객체를 생성한다.
        val result = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)
        return result
    }
}