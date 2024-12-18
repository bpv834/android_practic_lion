package com.lion.cameraalbum

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.cameraalbum.databinding.ActivityMainBinding
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 사진 촬영용 런처
    lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    // 1. 앨범용 런처
    lateinit var albumLauncher:ActivityResultLauncher<PickVisualMediaRequest>

    // 사진이 저장될 경로
    lateinit var filePath:String
    // 저장된 파일에 접근하기 위한 Uri
    lateinit var contentUri: Uri

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

        // 카메라 런처 생성 메서드를 호출한다.
        createCameraLauncher()

        // 3. 앨범 런처 생성 메서드를 호출한다.
        createAlbumLauncher()

        activityMainBinding.apply {
            buttonCamera.setOnClickListener {
                // 카메라 런처를 실행한다.
                // 촬영한 사진이 저장될 파일 이름
                val fileName = "/temp_${System.currentTimeMillis()}.jpg"
                // 경로 + 파일이름
                val picPath = "${filePath}${fileName}"
                val file = File(picPath)

                // 사진이 저장될 위치를 관리하는 Uri 객체를 생성ㅎ
                contentUri = FileProvider.getUriForFile(this@MainActivity, "com.lion.cameraalbum.camera", file)

                cameraLauncher.launch(contentUri)
            }

            buttonAlbum.setOnClickListener {

                // 4. launcher 실행
                val pickVisualMediaRequest = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                albumLauncher.launch(pickVisualMediaRequest)
            }
        }
    }

    // 카메라 런처 생성 메서드
    fun createCameraLauncher(){
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()){
//            if(it){
//                Log.d("test100", "사진 촬영하고 왔음")
//            } else{
//                Log.d("test100", "사진 촬영 안함")
//            }
            if(it){
                // uri를 통해 저장된 사진 데이터를 가져온다.
                val bitmap = BitmapFactory.decodeFile(contentUri.path)
                // 회전 각도값을 가져온다.
                val degree = getDegree(contentUri)
                // 회전 시킨 이미지를 가져온다.
                val rotateBitmap = rotateBitmap(bitmap, degree)
                // 사이즈를 줄여준다.
                val resizingBitmap = resizeBitmap(380, rotateBitmap)
                activityMainBinding.imageViewResult.setImageBitmap(resizingBitmap)

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
    fun resizeBitmap(targetWidth:Int, bitmap: Bitmap):Bitmap{
        // 이미지의 축소/확대 비율을 구한다.
        val ratio = targetWidth.toDouble() / bitmap.width.toDouble()
        // 세로 길이를 구한다.
        val targetHeight = (bitmap.height.toDouble() * ratio).toInt()
        // 크기를 조절한 Bitmap 객체를 생성한다.
        val result = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)
        return result
    }

    // 2. album 런처를 생성하는 메서드
    fun createAlbumLauncher(){
        albumLauncher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
            // 가져온 사진이 있다면
            if(it != null){
                val bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                    val source = ImageDecoder.createSource(contentResolver, it)
                    ImageDecoder.decodeBitmap(source)
                } else {
                    val cursor = contentResolver.query(it, null, null, null, null)
                    cursor?.moveToNext()
                    val idx = cursor?.getColumnIndex(MediaStore.Images.Media.DATA)
                    val source = cursor?.getString(idx!!)

                    BitmapFactory.decodeFile(source)
                }

                val resizeBitmap = resizeBitmap(1024, bitmap)
                activityMainBinding.imageViewResult.setImageBitmap(resizeBitmap)
            }
        }
    }

}