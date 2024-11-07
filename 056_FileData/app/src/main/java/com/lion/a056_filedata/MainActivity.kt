package com.lion.a056_filedata

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.lion.a056_filedata.databinding.ActivityMainBinding
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var activityMainBinding: ActivityMainBinding

    //파일 관리 앱의 Activity를 실행 시키기 위한 런처, Activity내부에서만 생성 가능
    lateinit var fileAppLauncher1: ActivityResultLauncher<Intent>

    // 읽기용
    lateinit var fileAppLauncher2: ActivityResultLauncher<Intent>

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

        // 파일 관리 앱의 Activity를 실행하기 위한 런처 객체 생성
        fileAppLauncher1 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                // ResultCode로 분기한다.
                // 선택하고 돌아왔을 때 : RESULT_OK
                // 그냥 돌아왔을 때 : RESULT_CANCEL
                if (it.resultCode == RESULT_OK) {
                    // 사용자가 선택한 파일에 접근할 수 경로데이터를 담은 객체를 추출한다.
                    // mode : w - 쓰기, a - 이어쓰기, r - 읽기
                    val des1 = contentResolver.openFileDescriptor(it.data?.data!!, "w")
                    // 스트림을 생성한다
                    val fileOutputStream = FileOutputStream(des1?.fileDescriptor)
                    val dataOutputStream = DataOutputStream(fileOutputStream)
                    dataOutputStream.writeInt(100)
                    dataOutputStream.writeDouble(11.11)
                    dataOutputStream.writeBoolean(true)
                    dataOutputStream.writeUTF("문자열1")

                    dataOutputStream.flush()
                    dataOutputStream.close()

                    activityMainBinding.textView.text = "파일 앱을 통해 선택한 파일에 쓰기 완료"
                }
            }

        fileAppLauncher2 =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    val des1 = contentResolver.openFileDescriptor(it.data?.data!!, "r")
                    val fileInputStream = FileInputStream(des1?.fileDescriptor)
                    val dataInputStream = DataInputStream(fileInputStream)

                    val data1 = dataInputStream.readInt()
                    val data2 = dataInputStream.readDouble()
                    val data3 = dataInputStream.readBoolean()
                    val data4 = dataInputStream.readUTF()

                    fileInputStream.close()

                    activityMainBinding.textView.text = ""
                    activityMainBinding.textView.append("data1 : ${data1}\n")
                    activityMainBinding.textView.append("data2 : ${data2}\n")
                    activityMainBinding.textView.append("data3 : ${data3}\n")
                    activityMainBinding.textView.append("data4 : ${data4}\n")
                }
            }

        activityMainBinding.apply {
            // 내부 저장소
            // 휴대폰을 컴퓨터에 연결해도 볼 수 없는 공간
            // data/data/애플리케이션 패키지명
            // 애플리케이션 삭제시 같이 삭제된다.
            // 내부 저장소는 File Stream을 사용한다.
            // 내부 저장소용 File Stream openFileOutput, openFileInput 메서드를 활용한다
            button.setOnClickListener {
                // MODE_PRIVATE : 덮어 씌우기
                // MODE_APPEND : 이어서 쓰기
                val fileOutputStream = openFileOutput("data1.dat", MODE_PRIVATE)
                val dataOutputStream = DataOutputStream(fileOutputStream)
                dataOutputStream.writeInt(100)
                dataOutputStream.writeDouble(11.11)
                dataOutputStream.writeBoolean(true)
                dataOutputStream.writeUTF("문자열1")

                dataOutputStream.flush()
                dataOutputStream.close()

                textView.text = "내부 저정소 쓰기 완료"
            }
            // 내부 저장소 읽기
            // 기본 스트림을 얻어올때 OpenInputStream 메서드를 사용한다.
            // 지정한 파일이 없으면 오류
            button2.setOnClickListener {
                textView.text = ""

                val fileInputStream = openFileInput("data1.dat")
                val dataInputStream = DataInputStream(fileInputStream)

                val data1 = dataInputStream.readInt()
                val data2 = dataInputStream.readDouble()
                val data3 = dataInputStream.readBoolean()
                val data4 = dataInputStream.readUTF()

                fileInputStream.close()

                textView.append("data1 : ${data1}\n")
                textView.append("data2 : ${data2}\n")
                textView.append("data3 : ${data3}\n")
                textView.append("data4 : ${data4}\n")
            }

            // 외부 저장소에 있는 애플리케이션 영역
            // Android 10에 생긴 Scoped Storage 정책을 따른다.
            // Android 9 까지는 개발자가 만든 애플리케이션이 외부 저장소에 접근하는 것이
            // 자유로웠다.
            // 하지만 안드로이드 10 버전 부터는 애플리케이션 영역에만 접근이 가능하고
            // 그 외의 폴더나 파일은 사용자의 선택에의해 접근 권한 취득할 수 잇다.
            button3.setOnClickListener {
                // 외부 저장소의 애플리케이션 영역까지의 경로를 가져온다.
                // null 을 넣어주면 애플리케이션 영역까지의 경로를 반환하고
                // Environment.DIRECTORY_xxx 를 넣어주면 애플리케이션 영역까지의 경로 뒤에 종류에 맞는
                // 폴더 이름이 추가된다
                val filePath = getExternalFilesDir(null).toString()
                // 외부 저정소의 애플리케이션 영역에 접근할 수 있는 스트림은 FileStream 객체를 직접 생성해준다.
                val fileOutputStream = FileOutputStream("${filePath}/data2.dat")
                val dataOutputStream = DataOutputStream(fileOutputStream)
                dataOutputStream.writeInt(100)
                dataOutputStream.writeDouble(11.11)
                dataOutputStream.writeBoolean(true)
                dataOutputStream.writeUTF("문자열1")

                dataOutputStream.flush()
                dataOutputStream.close()

                textView.text = "외부 저장소 애플리케이션 영역 쓰기 완료"
            }
            button4.setOnClickListener {
                val filePath = getExternalFilesDir(null).toString()
                val fileInputStream = FileInputStream("${filePath}/data2.dat")
                val dataInputStream = DataInputStream(fileInputStream)

                val data1 = dataInputStream.readInt()
                val data2 = dataInputStream.readDouble()
                val data3 = dataInputStream.readBoolean()
                val data4 = dataInputStream.readUTF()

                fileInputStream.close()

                textView.text = " "
                textView.append("data1 : ${data1}\n")
                textView.append("data2 : ${data2}\n")
                textView.append("data3 : ${data3}\n")
                textView.append("data4 : ${data4}\n")
            }
            button5.setOnClickListener {
                // Intent 생성
                // 쓰기인 경우 파일이 없으면 만들어야 하기 때문에 그에 맞는 것을 설정한다.
                val fileIntent = Intent(Intent.ACTION_CREATE_DOCUMENT)
                fileIntent.addCategory(Intent.CATEGORY_OPENABLE)
                // 파일의 MimeType(파일의 종류를 설정)
                // 모든 파일 : */*
                // 이미지 : image/*
                // 오디오 : audio/*
                // 영상 : video/*
                fileIntent.type = "*/*"
                // 실행한다.
                fileAppLauncher1.launch(fileIntent)
            }
            button6.setOnClickListener {
                // 읽기를 할 때는 ACTION_OPEN_DOCUMENT를 지정한다
                val fileIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                fileIntent.type = "*/*"
                fileAppLauncher2.launch(fileIntent)
            }
        }
    }
}
